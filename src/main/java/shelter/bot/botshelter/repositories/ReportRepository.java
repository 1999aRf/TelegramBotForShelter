package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shelter.bot.botshelter.model.Report;
import shelter.bot.botshelter.model.User;

import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByIsReviewedFalse();
    List<Report> findByUserAndDate(User user, LocalDate date);
}
