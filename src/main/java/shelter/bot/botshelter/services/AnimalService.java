package shelter.bot.botshelter.services;

import org.springframework.stereotype.Service;
import shelter.bot.botshelter.model.Animal;
import shelter.bot.botshelter.repositories.AnimalRepository;

import java.util.List;
import java.util.Optional;

/**
 * Класс предназначен для получения необходимых данных из БД посредством {@code AnimalRepository}
 *
 * @author KhasanovTR
 *
 */
@Service
public class AnimalService {
    private final AnimalRepository repository;


    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    /**
     * Добавляет животное в БД
     * @param animal - объект класса {@code Animal}
     * @return объект класса {@code Animal}
     */
    public Animal add(Animal animal) {
        return repository.save(animal);
    }

    /**
     * Ищет животное по id
     * @param id - идентификатор животного в БД
     * @return объект класса {@code Animal}
     */
    public Optional<Animal> findById(long id) {
        return repository.findById(id);
    }

    /**
     * Редактирует данные о животном в БД
     * @param animal - объект класса {@code Animal}
     * @return объект класса {@code Animal}
     */
    public Animal edit(Animal animal) {
        return repository.save(animal);
    }

    /**
     * Удаляет данные о животном из БД
     * @param id - идентификатор жмвотного в БД
     */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * Возвращает список всех животных
     * @return - список животных(может быть null)
     */
    public Optional<List<Animal>> findAll() {
        return Optional.ofNullable(repository.findAll());
    }
}
