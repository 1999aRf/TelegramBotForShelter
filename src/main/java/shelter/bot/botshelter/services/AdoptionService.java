package shelter.bot.botshelter.services;

import org.springframework.stereotype.Service;
import shelter.bot.botshelter.model.Adoptions;
import shelter.bot.botshelter.repositories.AdoptionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdoptionService {

    private final AdoptionRepository repository;

    public AdoptionService(AdoptionRepository repository) {
        this.repository = repository;
    }


    /**
     * Добавляет объект класса {@code Adoptions} в БД
     * @param adoptions - объект класса {@code Adoption}
     * @return объект класса {@code Adoption}
     */
    public Adoptions add(Adoptions adoptions) {
        return repository.save(adoptions);
    }

    /**
     * Ищет усыновление по id
     * @param id - идентификатор усыновления в БД
     * @return объект класса {@code Adoptions}
     */
    public Optional<Adoptions> findById(long id) {
        return repository.findById(id);
    }

    /**
     * Редактирует данные об усыновлении в БД
     * @param adoptions - объект класса {@code Adoptions}
     * @return объект класса {@code Adoptions}
     */
    public Adoptions edit(Adoptions adoptions) {
        return repository.save(adoptions);
    }

    /**
     * Удаляет данные об усыновлении из БД
     * @param id - идентификатор усыновления в БД
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * Возвращает список всех усыновлений
     * @return - список усыновлений(может быть null)
     */
    public Optional<List<Adoptions>> findAll() {
        return Optional.ofNullable(repository.findAll());
    }
}