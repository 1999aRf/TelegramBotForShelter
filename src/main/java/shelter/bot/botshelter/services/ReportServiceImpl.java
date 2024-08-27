package shelter.bot.botshelter.services;

import com.pengrad.telegrambot.TelegramBot;
import org.springframework.stereotype.Service;
import shelter.bot.botshelter.model.Report;
import shelter.bot.botshelter.model.User;
import shelter.bot.botshelter.repositories.ReportRepository;
import shelter.bot.botshelter.repositories.UserRepository;
import shelter.bot.botshelter.services.interfaces.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    private ReportRepository reportRepository;
    private UserRepository userRepository;
    private TelegramBot telegramBot;

    public ReportServiceImpl(ReportRepository reportRepository, UserRepository userRepository, TelegramBot telegramBot) {
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.telegramBot = telegramBot;
    }

    @Override
    public List<Report> getUnreviewedReports() {
        return reportRepository.findByIsReviewedFalse();
    }

    @Override
    public Optional<Report> getReportById(Long reportId) {
        return reportRepository.findById(reportId);
    }

    @Override
    public void markReportAsReviewed(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        report.setReviewed(true);
        reportRepository.save(report);
    }

    @Override
    public void saveReport(Report report) {
        reportRepository.save(report);
    }

    @Override
    public void sendWarning(Long reportId) {
        Report report = reportRepository.findById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        User user = report.getUser();

        String warningMessage = "Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. " +
                "Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного.";

        telegramBot.sendMessage(user.getChatId().toString(), warningMessage);
    }

    @Override
    public List<User> getUsersWithoutTodayReport() {
        LocalDate today = LocalDate.now();
        return userRepository.findUsersWithoutReportForDate(today);
    }
}
