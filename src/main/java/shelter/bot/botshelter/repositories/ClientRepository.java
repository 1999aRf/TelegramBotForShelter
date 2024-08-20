package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shelter.bot.botshelter.model.Client;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByChatId(Long chatId);
}
