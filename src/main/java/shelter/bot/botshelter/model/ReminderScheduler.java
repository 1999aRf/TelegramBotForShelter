package shelter.bot.botshelter.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shelter.bot.botshelter.services.AdoptionService;
import shelter.bot.botshelter.services.ReportServiceImpl;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

/**
 * Класс содержит метод-шедулер для проверки даты отправки последнего отчета для усыновления и отправки напоминая
 * в случае необходимости
 */

@Component
public class ReminderScheduler {

    private Logger logger = LoggerFactory.getLogger(ReminderScheduler.class);

    private final AdoptionService adoptionService;
    private final ReportServiceImpl reportService;

    public ReminderScheduler(AdoptionService adoptionService, ReportServiceImpl reportService) {
        this.adoptionService = adoptionService;
        this.reportService = reportService;
    }

    /**
     * Шедулер. Отправляет напоминание о просрочке отправки отчета. Запускается после 21:00
     */
    @Scheduled(cron = "0 0 21 * * ?")
    public void sendReminders() {
        List<Report> reportsToRemind;
        Optional<List<Adoptions>> optional = adoptionService.findByProbationPeriodLessNow();
        if (optional.isPresent()) {
            Iterator<Adoptions> iterator = optional.get().iterator();
            while (iterator.hasNext()) {
                Adoptions currentAdoption = iterator.next();
                logger.info("Напоминание.Обработка усыновления с текущим контрольным периодом. ID : {}", currentAdoption.getId());
                Optional<List<Report>> reportsOptional = adoptionService.findReportsByAdoptionId(currentAdoption.getId());
                if (reportsOptional.isPresent()) {
                    reportsToRemind = reportsOptional.get()
                            .stream()
                            .filter(e -> e.getDate().isBefore(LocalDate.now().minusDays(1)))
                            .toList();
                    while (reportsToRemind.iterator().hasNext()) {
                        logger.info("Напоминание.Отправка напоминания клиенту {}, чей последний отчет был отправлен более суток назад", currentAdoption.getClient().getId());
                        reportService.sendWarning(reportsToRemind.iterator().next().getId());
                    }
                }
            }
        }
    }
}
