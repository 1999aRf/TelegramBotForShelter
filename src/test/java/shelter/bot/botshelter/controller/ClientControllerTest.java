package shelter.bot.botshelter.controller;

import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import shelter.bot.botshelter.model.Client;
import shelter.bot.botshelter.services.ClientService;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientControllerTest.class)
class ClientControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

    @Test
    public void testGetClientByChatId() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();

        Client client = new Client();
        client.setChatId(123456789L);
        client.setClientName("John Doe");

        when(clientService.findByChatId(123456789L)).thenReturn(Optional.of(client));

        mockMvc.perform(get("/api/clients/123456789")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}