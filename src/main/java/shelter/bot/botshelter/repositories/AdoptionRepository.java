package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import shelter.bot.botshelter.model.Adoptions;
import shelter.bot.botshelter.model.Report;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdoptionRepository extends JpaRepository<Adoptions, Long> {
    @Query(value = "SELECT * from adoptions  where probation_period > CURRENT_TIMESTAMP", nativeQuery = true)
    Optional<List<Adoptions>> findByProbationPeriodLessNow();

    @Query(value = "Select * from A a  left join B b on a.id=?1", nativeQuery = true)
    Optional<List<Report>> findReportsByAdoptionId(Long adoptionId);
}
