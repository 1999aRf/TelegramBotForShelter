package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import shelter.bot.botshelter.model.Shelter;

@Repository
public interface ShelterRepository extends JpaRepository<Shelter, Long> {
}

