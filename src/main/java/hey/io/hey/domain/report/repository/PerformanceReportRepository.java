package hey.io.hey.domain.report.repository;

import hey.io.hey.domain.report.domain.PerformanceReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceReportRepository extends JpaRepository<PerformanceReport, String> {
}
