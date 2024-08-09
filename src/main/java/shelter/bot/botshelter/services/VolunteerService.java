package shelter.bot.botshelter.services;

import org.springframework.stereotype.Service;
import shelter.bot.botshelter.model.Volunteer;
import shelter.bot.botshelter.repository.VolunteerRepository;

@Service
public class VolunteerService {
    private final VolunteerRepository repository;

    public VolunteerService(VolunteerRepository repository) {
        this.repository = repository;
    }

    public Volunteer add(Volunteer volunteer) {
        return repository.save(volunteer);
    }

    public Volunteer findById(long id) {
        return repository.findById(id).get();
    }

    public Volunteer edit(Volunteer volunteer) {
        return repository.save(volunteer);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
