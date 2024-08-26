package shelter.bot.botshelter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shelter.bot.botshelter.model.Client;
import shelter.bot.botshelter.services.ClientService;

import java.util.List;
import java.util.Optional;
/**
*Обработчик запросов к таблице БД сущности {@code Client}
*/
@RestController
@RequestMapping("api/clients")
public class ClientController {
    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public Client createClient(@RequestBody Client client) {
        return clientService.saveClient(client);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Client> getClientByChatId(@PathVariable Long chatId) {
        Optional<Client> client = clientService.findByChatId(chatId);
        return client.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/getAll")
    public List<Client> getAllClients() {
        return clientService.findAllClients();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.ok().build();
    }
}
