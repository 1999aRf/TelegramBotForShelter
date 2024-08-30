package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shelter.bot.botshelter.model.Report;

import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByIsReviewedFalse();

    List<Report> findReportsByAdoptionId(Long adoptionsId);

}
