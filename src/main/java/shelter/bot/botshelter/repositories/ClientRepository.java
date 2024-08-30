package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shelter.bot.botshelter.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByChatId(Long chatId);
}
