package hey.io.hey.domain.artist.service;

import hey.io.hey.common.exception.BusinessException;
import hey.io.hey.common.exception.ErrorCode;
import hey.io.hey.common.response.SliceResponse;
import hey.io.hey.domain.album.dto.AlbumResponse;
import hey.io.hey.domain.album.repository.AlbumRepository;
import hey.io.hey.domain.artist.domain.ArtistEntity;
import hey.io.hey.domain.artist.dto.ArtistListResponse;
import hey.io.hey.domain.artist.dto.ArtistResponse;
import hey.io.hey.domain.artist.repository.ArtistRepository;
import hey.io.hey.domain.performance.domain.BoxOfficeRank;
import hey.io.hey.domain.performance.domain.Performance;
import hey.io.hey.domain.performance.domain.PerformanceArtist;
import hey.io.hey.domain.performance.dto.PerformanceResponse;
import hey.io.hey.domain.performance.repository.BoxOfficeRankRepository;
import hey.io.hey.domain.performance.repository.PerformanceArtistRepository;
import hey.io.hey.domain.performance.repository.PerformanceRepository;
import hey.io.hey.common.config.SpotifyConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceArtistRepository performanceArtistRepository;
    private final AlbumRepository albumRepository;
    private final BoxOfficeRankRepository boxOfficeRankRepository;

    SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(SpotifyConfig.accessToken())
            .build();

    public ArtistResponse getArtist(String artistId) {
        ArtistEntity artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ARTIST_NOT_FOUND));

        return new ArtistResponse(artist);
    }

    public SliceResponse<AlbumResponse> getAlbums(String artistId, int size, int page, Sort.Direction direction) {

        ArtistEntity artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ARTIST_NOT_FOUND));

        Slice<AlbumResponse> albums = albumRepository.getArtistAlbums(artist, Pageable.ofSize(size).withPage(page), direction);

        return new SliceResponse<>(albums);
    }

    public SliceResponse<PerformanceResponse> getArtistPerformances(String artistId, int size, int page, Sort.Direction direction) {
        Slice<PerformanceResponse> performances = performanceArtistRepository.getArtistPerformances(artistId, Pageable.ofSize(size).withPage(page), direction);
        return new SliceResponse<>(performances);
    }

    public List<ArtistListResponse> getArtistRank() {

        List<BoxOfficeRank> performances = boxOfficeRankRepository.findAll();
        String performanceIds = performances.get(0).getPerformanceIds();

        List<String> performanceIdList = Arrays.asList(performanceIds.split("\\|"));

        List<ArtistListResponse> artistListResponses = new ArrayList<>();

        for (String performanceId : performanceIdList) {
            List<ArtistListResponse> artists = performanceArtistRepository.getPerformanceArtists(performanceId);
            artistListResponses.addAll(artists);
        }
        return artistListResponses;
    }

    @Transactional
    public int updateArtistsBatch() throws IOException, ParseException, SpotifyWebApiException {
        long startTime = System.currentTimeMillis();
        log.info("[Batch] Batch Updating Artists...");

        List<Performance> performanceList = performanceRepository.findAll();
        List<String> allIdList = artistRepository.findAllIds();
        HashSet<String> allIdSet = new HashSet<>(allIdList);

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (Performance performance : performanceList) {
            CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                if (performance.getCast() != null) {
                    String artistNames = performance.getCast();
                    String[] artistNameArray = artistNames.split(", ");

                    if (artistNameArray[artistNameArray.length - 1].endsWith(" 등")) {
                        artistNameArray[artistNameArray.length - 1] = artistNameArray[artistNameArray.length - 1].replace(" 등", "");
                    }

                    List<String> artistNameList = new ArrayList<>(Arrays.asList(artistNameArray));

                    for (String artistName : artistNameList) {
                        try {
                            SearchArtistsRequest searchArtistsRequest = spotifyApi.searchArtists(artistName).limit(1).build();
                            Paging<Artist> searchArtistResponse = searchArtistsRequest.execute();
                            Artist[] searchArtistResult = searchArtistResponse.getItems();

                            if (searchArtistResult == null || searchArtistResult.length == 0) {
                                continue;
                            }

                            if (!allIdSet.contains(searchArtistResult[0].getId())) {
                                String artistImageUrl = null;
                                if (searchArtistResult[0].getImages() != null && searchArtistResult[0].getImages().length > 0) {
                                    artistImageUrl = searchArtistResult[0].getImages()[0].getUrl();
                                }
                                ArtistEntity artist = ArtistEntity.of(
                                        searchArtistResult[0].getId(),
                                        searchArtistResult[0].getName(),
                                        artistImageUrl,
                                        Arrays.asList(searchArtistResult[0].getGenres())
                                );

                                artistRepository.save(artist);
                                allIdSet.add(searchArtistResult[0].getId());

                                PerformanceArtist performanceArtist = PerformanceArtist.of(performance, artist);
                                performanceArtistRepository.save(performanceArtist);
                            }
                        } catch (IOException | SpotifyWebApiException | ParseException e) {
                            log.error("Error while searching artist: {}", artistName, e);
                        }
                    }
                }
                return null;
            }, executorService).thenAccept(aVoid -> {});
            futures.add(future);
        }

        futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        executorService.shutdown();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        int updateCnt = allIdSet.size() - allIdList.size();
        log.info("[Batch] Artists have been Updated... Total Size: {}, Duration: {} ms", updateCnt, duration);

        return updateCnt;
    }

}
