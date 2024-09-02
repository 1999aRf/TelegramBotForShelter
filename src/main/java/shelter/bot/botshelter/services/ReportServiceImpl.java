package shelter.bot.botshelter.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;
import shelter.bot.botshelter.model.Client;
import shelter.bot.botshelter.model.Report;
import shelter.bot.botshelter.repositories.AdoptionRepository;
import shelter.bot.botshelter.repositories.ReportRepository;
import shelter.bot.botshelter.services.interfaces.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReportServiceImpl implements ReportService {
    private final ReportRepository reportRepository;
    private final AdoptionRepository adoptionRepository;
    private final TelegramBot telegramBot;

    public ReportServiceImpl(ReportRepository reportRepository, AdoptionRepository adoptionRepository, TelegramBot telegramBot) {
        this.reportRepository = reportRepository;
        this.adoptionRepository = adoptionRepository;
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
        Client client = report.getAdoption().getClient();

        String warningMessage = "Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо. " +
                "Пожалуйста, подойди ответственнее к этому занятию. В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного.";

        telegramBot.execute(new SendMessage(client.getChatId().toString(), warningMessage));
    }


}
