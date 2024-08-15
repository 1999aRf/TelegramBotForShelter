package shelter.bot.botshelter.services;

import org.springframework.stereotype.Service;
import shelter.bot.botshelter.model.Volunteer;
import shelter.bot.botshelter.repositories.VolunteerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class VolunteerService {
    private final VolunteerRepository repository;

    public VolunteerService(VolunteerRepository repository) {
        this.repository = repository;
    }

    public Volunteer add(Volunteer volunteer) {
        return repository.save(volunteer);
    }

    public Optional<Volunteer> findById(long id) {
        return repository.findById(id);
    }

    public Volunteer edit(Volunteer volunteer) {
        return repository.save(volunteer);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Optional<List<Volunteer>> findAll() {
        return Optional.ofNullable(repository.findAll());
    }
}
