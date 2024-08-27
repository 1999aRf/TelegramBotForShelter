package shelter.bot.botshelter.model;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shelter.bot.botshelter.services.ReportServiceImpl;

import java.util.List;

@Component
public class ReminderScheduler {
    private final ReportServiceImpl reportService;

    public ReminderScheduler(ReportServiceImpl reportService) {
        this.reportService = reportService;
    }

    @Scheduled(cron = "0 0 21 * * ?")
    public void sendReminders() {
        List<User> userWithoutReport = reportService.getUsersWithoutTodayReport();
        for (User user : userWithoutReport) {
            String message = "Дорогой усыновитель, не забудьте отправить отчет о состоянии животного сегодня!";
            reportService.sendWarning(user.getId());
        }
    }
}
