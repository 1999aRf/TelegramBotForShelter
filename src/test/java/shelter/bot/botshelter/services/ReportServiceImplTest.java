package shelter.bot.botshelter.services;

import com.pengrad.telegrambot.TelegramBot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import shelter.bot.botshelter.model.Adoptions;
import shelter.bot.botshelter.model.Client;
import shelter.bot.botshelter.model.Report;
import shelter.bot.botshelter.repositories.AdoptionRepository;
import shelter.bot.botshelter.repositories.ReportRepository;

import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private TelegramBot telegramBot;

    @InjectMocks
    private ReportServiceImpl reportService;

    private Report report;
    private Client client;
    private Adoptions adoption;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        client = new Client();

        adoption = new Adoptions();
        adoption.setClient(client);

        report = new Report();
        report.setReviewed(false);
        report.setAdoption(adoption);
    }

    @Test
    void testGetUnreviewedReports() {
        when(reportRepository.findByIsReviewedFalse()).thenReturn(List.of(report));

        List<Report> unreviewedReports = reportService.getUnreviewedReports();

        assertNotNull(unreviewedReports);
        assertEquals(1, unreviewedReports.size());
        assertFalse(unreviewedReports.get(0).isReviewed());
        verify(reportRepository, times(1)).findByIsReviewedFalse();
    }

    @Test
    void testGetReportById() {
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        Optional<Report> retrievedReport = reportService.getReportById(1L);

        assertTrue(retrievedReport.isPresent());
        assertEquals(report.getId(), retrievedReport.get().getId());
        verify(reportRepository, times(1)).findById(1L);
    }

    @Test
    void testMarkReportAsReviewed() {
        when(reportRepository.findById(1L)).thenReturn(Optional.of(report));

        reportService.markReportAsReviewed(1L);

        assertTrue(report.isReviewed());
        verify(reportRepository, times(1)).findById(1L);
        verify(reportRepository, times(1)).save(report);
    }

    @Test
    void testSaveReport() {
        reportService.saveReport(report);

        verify(reportRepository, times(1)).save(report);
    }
}