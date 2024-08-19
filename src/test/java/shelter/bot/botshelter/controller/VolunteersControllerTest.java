package shelter.bot.botshelter.controller;


import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shelter.bot.botshelter.model.Volunteer;
import shelter.bot.botshelter.repositories.VolunteerRepository;
import shelter.bot.botshelter.services.VolunteerService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static shelter.bot.botshelter.constants.Constants.*;

@WebMvcTest(VolunteersController.class)
public class VolunteersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private VolunteersController controller;

    @SpyBean
    private VolunteerService service;

    @MockBean
    private VolunteerRepository repository;


    @Test
    void addVolunteerTest() throws Exception {

        when(service.add(any(Volunteer.class))).thenReturn(VOLUNTEER1);
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/volunteers")
                        .content(getVolunteerJson1().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",VOLUNTEER1.getId()).exists())
                .andExpect(jsonPath("$.chat_id",VOLUNTEER1.getChat_id()).exists())
                .andExpect(jsonPath("$.name",VOLUNTEER1.getName()).exists())
                .andExpect(jsonPath("$.contacts",VOLUNTEER1.getContacts()).exists());

    }
    @Test
    void findByIdTest() throws Exception {
        when(service.findById(any(Long.class))).thenReturn(Optional.of(VOLUNTEER1));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/volunteers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",VOLUNTEER1.getId()).exists())
                .andExpect(jsonPath("$.chat_id",VOLUNTEER1.getChat_id()).exists())
                .andExpect(jsonPath("$.name",VOLUNTEER1.getName()).exists())
                .andExpect(jsonPath("$.contacts",VOLUNTEER1.getContacts()).exists());

    }

    @Test
    void deleteVolunteer() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .delete("/api/volunteers/" + VOLUNTEER1.getId())
        ).andExpect(status().isOk());
    }


}
