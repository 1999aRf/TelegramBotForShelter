package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shelter.bot.botshelter.model.Adoptions;
@Repository
public interface AdoptionRepository extends JpaRepository<Adoptions,Long> {
}
