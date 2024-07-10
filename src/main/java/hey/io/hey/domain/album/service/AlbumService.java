package hey.io.hey.domain.album.service;

import hey.io.hey.domain.album.domain.AlbumEntity;
import hey.io.hey.domain.album.repository.AlbumRepository;
import hey.io.hey.domain.artist.domain.ArtistEntity;
import hey.io.hey.domain.artist.repository.ArtistRepository;
import hey.io.hey.common.config.SpotifyConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsAlbumsRequest;

import java.io.IOException;
import java.util.ArrayList;
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
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final ArtistRepository artistRepository;

    SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setAccessToken(SpotifyConfig.accessToken())
            .build();

    @Transactional
    public int updateAlbumsBatch() {
        log.info("[Batch] Batch Updating Albums...");
        long startTime = System.currentTimeMillis();

        Pageable pageable = PageRequest.of(0, 100);
        Page<ArtistEntity> artistPage;

        List<String> allIdList = albumRepository.findAllIds();
        HashSet<String> allIdSet = new HashSet<>(allIdList);

        List<CompletableFuture<Void>> futures = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        do {
            artistPage = artistRepository.findAll(pageable);

            for (ArtistEntity artist : artistPage.getContent()) {
                CompletableFuture<Void> future = CompletableFuture.supplyAsync(() -> {
                    String artistId = artist.getId();

                    try {
                        GetArtistsAlbumsRequest getArtistsAlbumsRequest = spotifyApi.getArtistsAlbums(artistId).build();
                        Paging<AlbumSimplified> getArtistsAlbumsResponse = getArtistsAlbumsRequest.execute();
                        AlbumSimplified[] albums = getArtistsAlbumsResponse.getItems();

                        for (AlbumSimplified albumSimplified : albums) {
                            String albumId = albumSimplified.getId();
                            if (!allIdSet.contains(albumId)) {
                                AlbumEntity albumEntity = albumRepository.findById(albumId).orElse(null);

                                if (albumEntity == null) {
                                    albumEntity = AlbumEntity.of(
                                            albumId,
                                            albumSimplified.getName(),
                                            albumSimplified.getImages()[0].getUrl(),
                                            albumSimplified.getReleaseDate()
                                    );
                                    albumEntity.setArtist(artist);
                                    albumRepository.save(albumEntity);
                                    allIdSet.add(albumId);
                                }
                            }
                        }
                    } catch (IOException | SpotifyWebApiException | ParseException e) {
                        log.error("Error updating albums for artist {}: {}", artistId, e.getMessage());
                    }
                    return null;
                }, executorService);
                futures.add(future);
            }

            pageable = artistPage.nextPageable();
        } while (artistPage.hasNext());

        futures.stream().map(CompletableFuture::join).collect(Collectors.toList());
        executorService.shutdown();

        int updateCnt = allIdSet.size() - allIdList.size();

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info("[Batch] Albums have been updated. Total Size: {}, Duration: {} ms", updateCnt, duration);

        return updateCnt;
    }

}
