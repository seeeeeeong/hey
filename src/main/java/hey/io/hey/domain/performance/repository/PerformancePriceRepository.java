package hey.io.hey.domain.performance.repository;

import hey.io.hey.domain.performance.domain.PerformancePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformancePriceRepository extends JpaRepository<PerformancePrice, Long> {
}
