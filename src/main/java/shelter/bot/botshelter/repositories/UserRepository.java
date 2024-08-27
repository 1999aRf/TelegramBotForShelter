package shelter.bot.botshelter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shelter.bot.botshelter.model.User;

import java.time.LocalDate;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE NOT EXISTS (SELECT r FROM Report r WHERE r.user = u AND r.date = :date)")
    List<User> findUsersWithoutReportForDate(@Param("date") LocalDate date);
}
