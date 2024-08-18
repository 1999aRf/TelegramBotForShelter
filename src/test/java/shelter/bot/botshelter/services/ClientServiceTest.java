package shelter.bot.botshelter.services;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import shelter.bot.botshelter.model.Client;
import shelter.bot.botshelter.repositories.ClientRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    public void testFindByChatId() {
        Client client = new Client();
        client.setChatId(123456789L);
        client.setClientName("John Doe");

        when(clientRepository.findByChatId(123456789L)).thenReturn(Optional.of(client));

        Optional<Client> foundClient = clientService.findByChatId(123456789L);
        assertTrue(foundClient.isPresent());
        assertEquals("John Doe", foundClient.get().getClientName());
    }*/
}