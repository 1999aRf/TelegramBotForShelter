package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shelter.bot.botshelter.model.Adoptions;
import shelter.bot.botshelter.model.Report;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByIsReviewedFalse();

    List<Report> findReportsByAdoptionId(Long adoptionsId);

}
