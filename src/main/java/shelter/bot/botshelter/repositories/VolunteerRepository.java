package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shelter.bot.botshelter.model.Volunteer;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer,Long> {
}
