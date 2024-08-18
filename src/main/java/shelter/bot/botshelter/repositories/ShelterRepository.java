package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shelter.bot.botshelter.model.Shelter;

public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}
