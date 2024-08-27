package shelter.bot.botshelter.services.interfaces;

import shelter.bot.botshelter.model.Report;
import shelter.bot.botshelter.model.User;

import java.util.List;
import java.util.Optional;

public interface ReportService {
    List<Report> getUnreviewedReports();
    Optional<Report> getReportById(Long reportId);
    void markReportAsReviewed(Long reportId);
    void saveReport(Report report);
    void sendWarning(Long reportId);
    List<User> getUsersWithoutTodayReport();
}
