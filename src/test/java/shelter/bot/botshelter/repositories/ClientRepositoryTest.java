package shelter.bot.botshelter.repositories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import shelter.bot.botshelter.model.Client;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    /*@Test
    public void testSaveAndFindByChatId() {
        Client client = new Client();
        client.setChatId(123456789L);
        client.setClientName("John Doe");
        client.setContactNumber("1234567890");

        clientRepository.save(client);

        Optional<Client> foundClient = clientRepository.findByChatId(123456789L);
        assertTrue(foundClient.isPresent());
        assertEquals("John Doe", foundClient.get().getClientName());
    }*/
}