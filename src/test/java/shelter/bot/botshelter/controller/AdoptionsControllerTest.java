package shelter.bot.botshelter.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import shelter.bot.botshelter.model.Adoptions;
import shelter.bot.botshelter.model.Animal;
import shelter.bot.botshelter.model.Client;
import shelter.bot.botshelter.services.AdoptionService;
import shelter.bot.botshelter.services.AnimalService;
import shelter.bot.botshelter.services.ClientService;

import java.util.Optional;
import java.util.List;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdoptionsController.class)
class AdoptionsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdoptionService adoptionService;

    @MockBean
    private ClientService clientService;

    @MockBean
    private AnimalService animalService;

    @Test
    void testAddAdoptionsSuccess() throws Exception {
        Animal animal = new Animal();
        Client client = new Client();
        Adoptions adoption = new Adoptions(animal, client);

        Mockito.when(animalService.findById(any(Long.class))).thenReturn(Optional.of(animal));
        Mockito.when(clientService.findByChatId(any(Long.class))).thenReturn(Optional.of(client));
        Mockito.when(adoptionService.add(any(Adoptions.class))).thenReturn(adoption);

        mockMvc.perform(post("/api/adoptions/addAdoption/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());  // Проверьте наличие ID
    }

    @Test
    void testAddAdoptionsClientNotFound() throws Exception {
        Mockito.when(clientService.findByChatId(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/adoptions/addAdoption/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddAdoptionsAnimalNotFound() throws Exception {
        Client client = new Client();
        Mockito.when(clientService.findByChatId(any(Long.class))).thenReturn(Optional.of(client));
        Mockito.when(animalService.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/adoptions/addAdoption/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPutAdoptionsSuccess() throws Exception {
        Adoptions adoption = new Adoptions(); // Замените на правильный конструктор
//        adoption.setId(1L);

        Mockito.when(adoptionService.findById(eq(1L))).thenReturn(Optional.of(adoption));
        Mockito.when(adoptionService.edit(any(Adoptions.class))).thenReturn(adoption);

        mockMvc.perform(put("/api/adoptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"client\":{},\"animal\":{}}")) // Замените на правильные данные
                .andExpect(status().isOk());
    }

    @Test
    void testPutAdoptionsNotFound() throws Exception {
        Mockito.when(adoptionService.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/adoptions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1,\"client\":{},\"animal\":{}}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testPutAdoptionsSetReviewedSuccess() throws Exception {
        Adoptions adoption = new Adoptions(); // Замените на правильный конструктор
//        adoption.setId(1L);

        Mockito.when(adoptionService.findById(eq(1L))).thenReturn(Optional.of(adoption));
        Mockito.when(adoptionService.edit(any(Adoptions.class))).thenReturn(adoption);

        mockMvc.perform(put("/api/adoptions/setResult/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testPutAdoptionsSetReviewedNotFound() throws Exception {
        Mockito.when(adoptionService.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/adoptions/setResult/1/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindByIdSuccess() throws Exception {
        Adoptions adoption = new Adoptions(); // Замените на правильный конструктор

        Mockito.when(adoptionService.findById(eq(1L))).thenReturn(Optional.of(adoption));

        mockMvc.perform(get("/api/adoptions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        Mockito.when(adoptionService.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/adoptions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testFindAllSuccess() throws Exception {
        Adoptions adoption = new Adoptions(); // Замените на правильный конструктор
//        adoption.setId(1L);

        Mockito.when(adoptionService.findAll()).thenReturn(Optional.of(Collections.singletonList(adoption)));

        mockMvc.perform(get("/api/adoptions/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testFindAllNotFound() throws Exception {
        Mockito.when(adoptionService.findAll()).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/adoptions/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteSuccess() throws Exception {
        mockMvc.perform(delete("/api/adoptions/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}