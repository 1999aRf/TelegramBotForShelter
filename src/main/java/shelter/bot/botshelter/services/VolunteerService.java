package shelter.bot.botshelter.services;

import org.springframework.stereotype.Service;
import shelter.bot.botshelter.model.Volunteer;
import shelter.bot.botshelter.repositories.VolunteerRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс предназначен для получения необходимых данных из БД посредством {@code VolunteerRepository}
 *
 * @author KhasanovTR
 */
@Service
public class VolunteerService {
    private final VolunteerRepository repository;


    public VolunteerService(VolunteerRepository repository) {
        this.repository = repository;
    }

    /**
     * Добавляет волонтера в БД
     *
     * @param volunteer - объект класса {@code Volunteer}
     * @return объект класса {@code Volunteer}
     */
    public Volunteer add(Volunteer volunteer) {
        return repository.save(volunteer);
    }

    /**
     * Ищет волонтера по id
     *
     * @param id - идентификатор волонтера в БД
     * @return объект класса {@code Volunteer}
     */
    public Optional<Volunteer> findById(long id) {
        return repository.findById(id);
    }

    /**
     * Редактирует данные о волонтере в БД
     *
     * @param volunteer - объект класса {@code Volunteer}
     * @return объект класса {@code Volunteer}
     */
    public Volunteer edit(Volunteer volunteer) {
        return repository.save(volunteer);
    }

    /**
     * Удаляет данные о волонтере из БД
     *
     * @param id - идентификатор волонтера в БД
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * Возвращает список всех волонтеров
     *
     * @return - список волонтеров(может быть null)
     */
    public Optional<List<Volunteer>> findAll() {
        return Optional.ofNullable(repository.findAll());
    }
}
