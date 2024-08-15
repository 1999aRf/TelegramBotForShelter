package shelter.bot.botshelter.services;

import org.springframework.stereotype.Service;
import shelter.bot.botshelter.model.Client;
import shelter.bot.botshelter.repositories.ClientRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {
    private final ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    public Optional<Client> findByChatId(Long chatId) {
        return Optional.ofNullable(clientRepository.findByChatId(chatId));
    }

    public List<Client> findAllClients() {
        return clientRepository.findAll();
    }

    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }
}
