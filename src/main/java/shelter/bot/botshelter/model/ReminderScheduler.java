package shelter.bot.botshelter.model;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shelter.bot.botshelter.services.AdoptionService;
import shelter.bot.botshelter.services.ReportServiceImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class ReminderScheduler {
    private final AdoptionService adoptionService;
    private final ReportServiceImpl reportService;

    public ReminderScheduler(AdoptionService adoptionService, ReportServiceImpl reportService) {
        this.adoptionService = adoptionService;
        this.reportService = reportService;
    }

    @Scheduled(cron = "0 0 21 * * ?")
    public void sendReminders() {
        List<Report> reportsToRemind;
        Optional<List<Adoptions>> optional = adoptionService.findByProbationPeriodLessNow();
        if (optional.isPresent()) {
            Adoptions adoption = optional.get().stream().findFirst().get();// пока приложение расчитано на одно усыновление
            Optional<List<Report>> reportsOptional = adoptionService.findReportsByAdoptionId(adoption.getId());
            if (reportsOptional.isPresent()) {
                reportsToRemind = reportsOptional.get()
                        .stream()
                        .filter(e -> e.getDate().isBefore(LocalDateTime.now().minusDays(1)) )
                        .toList();
                while (reportsToRemind.iterator().hasNext()) {

                    reportService.sendWarning(reportsToRemind.iterator().next().getId());
                }
            }

        }
    }
}
