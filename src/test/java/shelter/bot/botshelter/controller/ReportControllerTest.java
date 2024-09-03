package shelter.bot.botshelter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import shelter.bot.botshelter.model.Adoptions;
import shelter.bot.botshelter.model.Report;
import shelter.bot.botshelter.services.AdoptionService;
import shelter.bot.botshelter.services.AnimalService;
import shelter.bot.botshelter.services.ReportServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReportController.class)
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportServiceImpl reportService;

    @MockBean
    private AnimalService animalService;

    @MockBean
    private AdoptionService adoptionService;

    private Report report;
    private Adoptions adoption;

    @BeforeEach
    void setUp() {
        report = new Report();
        report.setId(1L);
        report.setReviewed(false);

        adoption = new Adoptions();

        report.setAdoption(adoption);
    }

    @Test
    void testGetUnreviewedReports() throws Exception {
        when(reportService.getUnreviewedReports()).thenReturn(List.of(report));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reports/unreviewed"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(report.getId()));
    }

    @Test
    void testSubmitReport() throws Exception {
        MockMultipartFile photo = new MockMultipartFile("photo", "photo.png", "image/png", "fake-image".getBytes());

        when(adoptionService.findById(anyLong())).thenReturn(Optional.of(adoption));
        doNothing().when(reportService).saveReport(any(Report.class));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/reports/1/1/submit")
                        .file(photo)
                        .param("diet", "Diet details")
                        .param("wellbeing", "Wellbeing details")
                        .param("behaviorChanges", "Behavior changes")
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("Daily report submitted successfully."));
    }

    @Test
    void testSetReviewed() throws Exception {
        when(reportService.getReportById(anyLong())).thenReturn(Optional.of(report));
        doNothing().when(reportService).saveReport(any(Report.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reports/1/setReviewed"))
                .andExpect(status().isOk())
                .andExpect(content().string("Daily report submitted successfully."));
    }

    @Test
    void testSetReviewedReportNotFound() throws Exception {
        when(reportService.getReportById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reports/1/setReviewed"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetReportById() throws Exception {
        when(reportService.getReportById(anyLong())).thenReturn(Optional.of(report));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reports/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(report.getId()));
    }

    @Test
    void testGetPhoto() throws Exception {
        byte[] photoData = "fake-image".getBytes();
        report.setPhoto(photoData);

        when(reportService.getReportById(anyLong())).thenReturn(Optional.of(report));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/reports/getPhoto/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(photoData));
    }

    @Test
    void testSendWarning() throws Exception {
        doNothing().when(reportService).sendWarning(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/reports/1/sendWarning"))
                .andExpect(status().isOk());
    }
}