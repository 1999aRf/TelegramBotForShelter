package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shelter.bot.botshelter.model.Animal;

@Repository
public interface AnimalRepository extends JpaRepository<Animal,Long> {
}
