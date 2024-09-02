package shelter.bot.botshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shelter.bot.botshelter.model.Adoptions;
import shelter.bot.botshelter.model.Animal;
import shelter.bot.botshelter.model.Report;
import shelter.bot.botshelter.services.AdoptionService;
import shelter.bot.botshelter.services.AnimalService;
import shelter.bot.botshelter.services.ReportServiceImpl;

import javax.print.attribute.standard.Media;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
@Tag(name = "Reports")
public class ReportController {
    private final ReportServiceImpl reportService;
    private final AnimalService animalService;
    private final AdoptionService adoptionService;

    public ReportController(ReportServiceImpl reportService, AnimalService animalService, AdoptionService adoptionService) {
        this.reportService = reportService;
        this.animalService = animalService;
        this.adoptionService = adoptionService;
    }
@Operation(summary = "Список непросмотренных отчетов",
description = "Метод для выдачи списка непросмотренных отчетов",
responses = {
        @ApiResponse(responseCode = "200", description = "Выдан список непросмотренных отчетов",
                content = @Content(
                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = Report.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
        @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
})
@GetMapping("/unreviewed")
    public ResponseEntity<List<Report>> getUnreviewedReports() {
        List<Report> unreviewedReports = reportService.getUnreviewedReports();
        return ResponseEntity.ok(unreviewedReports);
    }


    @Operation(summary = "Добавить отчет",
            description = "Метод добавления отчета",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Отчет успешно добавлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })

    @PostMapping(value = "/{reportId}/{adoptionId}/submit",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> submitReport(@PathVariable Long reportId,
                                               @PathVariable Long adoptionId,
                                               @RequestParam("photo") MultipartFile photo,
                                               @RequestParam("diet") String diet,
                                               @RequestParam("wellbeing") String wellbeing,
                                               @RequestParam("behaviorChanges") String behaviorChanges) {
        byte[] photoBytes;
        Optional<Adoptions> adoptionsOptional = adoptionService.findById(adoptionId);
         if (!adoptionsOptional.isPresent()) {
            return ResponseEntity.notFound().header("Усыновление не найдено").build();
        }
        try {
            photoBytes = photo.getBytes();

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to submit report due to I/O error.");
        }
        Report report = new Report();
        report.setDate(LocalDateTime.now());
        report.setPhoto(photoBytes);
        report.setDiet(diet);
        report.setWellbeing(wellbeing);
        report.setBehaviorChanges(behaviorChanges);
        report.setReviewed(false);
        report.setAdoption(adoptionsOptional.get());
        reportService.saveReport(report);
        return ResponseEntity.ok().body("Daily report submitted successfully.");
    }

    @Operation(summary = "Отметить отчет просмотренным ",
            description = "Метод редактирования отчета и пометки его как прочитанного",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Отчет успешно просмотрен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })

    @PostMapping(value = "/{reportId}/setReviewed")
    public ResponseEntity<String> setReviewed(@PathVariable Long reportId) {
        Optional<Report> report = reportService.getReportById(reportId);
        if (report.isPresent()) {
            report.get().setReviewed(true);
            reportService.saveReport(report.orElse(null));
            return ResponseEntity.ok().body("Daily report submitted successfully.");
        } else return ResponseEntity.notFound().build();

    }

    @Operation(summary = "Получить отчет с БД ",
            description = "Метод получения отчета по его id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Отчет успешно получен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Report.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })


    @GetMapping(value = "/{reportId}")
    public ResponseEntity<Report> getReportById(@PathVariable Long reportId) {
        Report report = reportService.getReportById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));

        return ResponseEntity.ok(report);
    }

    @Operation(summary = "Получить фото с отчета БД ",
            description = "Метод получения фото отчета по его id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Фото успешно получено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Byte.class)))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @GetMapping(value = "/getPhoto/{reportId}")
    private ResponseEntity<byte[]> getPhoto(@PathVariable Long reportId) {
        Report report = reportService.getReportById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.IMAGE_PNG.toString()));
        headers.setContentLength(report.getPhoto().length);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(report.getPhoto());
    }

    @Operation(summary = "Отправить напоминание ",
            description = "Метод отправки напоминания отправителю отчета по его id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Напоминае успешно отправлено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE)),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @PostMapping("/{reportId}/sendWarning")
    public ResponseEntity<Void> sendWarning(@PathVariable Long reportId) {
        reportService.sendWarning(reportId);
        return ResponseEntity.ok().build();
    }
}
