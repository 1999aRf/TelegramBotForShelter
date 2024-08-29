package shelter.bot.botshelter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shelter.bot.botshelter.model.Report;
import shelter.bot.botshelter.services.ReportServiceImpl;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private ReportServiceImpl reportService;

    @GetMapping("/unreviewed")
    public ResponseEntity<List<Report>> getUnreviewedReports() {
        List<Report> unreviewedReports = reportService.getUnreviewedReports();
        return ResponseEntity.ok(unreviewedReports);
    }

    @PostMapping(value = "/{reportId}/submit",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> submitReport(@PathVariable Long reportId,
                                               @RequestParam("photo") MultipartFile photo,
                                               @RequestParam("diet") String diet,
                                               @RequestParam("wellbeing") String wellbeing,
                                               @RequestParam("behaviorChanges") String behaviorChanges) {
        try {
            byte[] photoBytes = photo.getBytes();
            Report report = new Report();
//            report.setClient(new Client()); // reportId Ensure User object is set correctly
            report.setDate(LocalDate.now());
            report.setPhoto(photoBytes);
            report.setDiet(diet);
            report.setWellbeing(wellbeing);
            report.setBehaviorChanges(behaviorChanges);
            report.setReviewed(false);
            reportService.saveReport(report);
            return ResponseEntity.ok().body("Daily report submitted successfully.");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to submit report due to I/O error.");
        }
    }

    @PostMapping(value = "/{reportId}/setReviewed")
    public ResponseEntity<String> setReviewed(@PathVariable Long reportId) {
        Optional<Report> report = reportService.getReportById(reportId);
        if (report.isPresent()) {
            report.get().setReviewed(true);
            reportService.saveReport(report.orElse(null));
            return ResponseEntity.ok().body("Daily report submitted successfully.");
        } else return ResponseEntity.notFound().build();

    }

    @GetMapping("/{reportId}")
    public ResponseEntity<Report> getReportById(@PathVariable Long reportId) {
        Report report = reportService.getReportById(reportId)
                .orElseThrow(() -> new RuntimeException("Report not found"));
        return ResponseEntity.ok(report);
    }

    @PostMapping("/{reportId}/sendWarning")
    public ResponseEntity<Void> sendWarning(@PathVariable Long reportId) {
        reportService.sendWarning(reportId);
        return ResponseEntity.ok().build();
    }
}
