package hey.io.hey.domain.boxoffice.repository;

import hey.io.hey.domain.boxoffice.domain.BoxOfficeRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoxOfficeRankRepository extends JpaRepository<BoxOfficeRank, Long> {
}
