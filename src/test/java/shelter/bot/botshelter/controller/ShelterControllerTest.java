package shelter.bot.botshelter.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shelter.bot.botshelter.model.Shelter;
import shelter.bot.botshelter.services.ShelterService;


import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ShelterControllerTest {
    private MockMvc mockMvc;

    @InjectMocks
    private ShelterController shelterController;

    @Mock
    private ShelterService shelterService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(shelterController).build();
    }

    @Test
    void getShelterById() throws Exception {
        Shelter shelter = new Shelter();
        shelter.setId(1L);
        when(shelterService.getShelterById(1L)).thenReturn(Optional.of(shelter));

        mockMvc.perform(get("/api/shelters/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));

        verify(shelterService, times(1)).getShelterById(1L);
    }

    @Test
    void createShelter() {
        //не знаю как написать, надо подумать и обсудить.
    }
}