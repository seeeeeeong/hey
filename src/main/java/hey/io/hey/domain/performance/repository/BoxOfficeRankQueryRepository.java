package hey.io.hey.domain.performance.repository;

import hey.io.hey.domain.performance.domain.BoxOfficeRank;
import hey.io.hey.domain.performance.dto.BoxOfficeRankRequest;

import java.util.Optional;

public interface BoxOfficeRankQueryRepository {

    Optional<BoxOfficeRank> findBoxOfficeRank(BoxOfficeRankRequest request);

}
