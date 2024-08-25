package shelter.bot.botshelter.services;

import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import shelter.bot.botshelter.model.Shelter;
import shelter.bot.botshelter.repositories.ShelterRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;


@Service
public class ShelterService {

    private ShelterRepository repository;

    public ShelterService(ShelterRepository repository) {
        this.repository = repository;
    }

    public Shelter getShelterByAnimalSign(byte animalSign) {
        // возвращает приюты в зависимости от того, для кошек они или для собак

        return null;


    }

    /**
     * Получить все приюты.
     *
     * @return Список всех приютов.
     */

    public List<Shelter> getAllShelters() {

        return repository.findAll();

    }

    /**
     * Получить приют по его ID.
     *
     * @param id Идентификатор приюта.
     * @return Приют, если найден.
     */

    public Optional<Shelter> getShelterById(Long id) {

        return repository.findById(id);

    }

    /**
     * Создать новый приют.
     *
     * @param shelter Данные нового приюта.
     * @return Созданный приют.
     */

    public Shelter createShelter(Shelter shelter) {

        return repository.save(shelter);

    }

    /**
     * Обновить существующий приют.
     *
     * @param id      Идентификатор приюта.
     * @param shelterDetails Обновленные данные приюта.
     * @return Обновленный приют, если обновление успешно.
     */

    public Shelter updateShelter(Long id, Shelter shelterDetails) {

        Shelter shelter = repository.findById(id)

                .orElseThrow(() -> new RuntimeException("Shelter not found"));

        shelter.setChatId(shelterDetails.getChatId());
        shelter.setClientName(shelterDetails.getClientName());
        shelter.setContactNumber(shelterDetails.getContactNumber());
        shelter.setAdoptedAnimals(shelterDetails.getAdoptedAnimals());


        return repository.save(shelter);

    }

    /**
     * Удалить приют по его ID.
     *
     * @param id Идентификатор приюта.
     */

    public void deleteShelter(Long id) {

        repository.deleteById(id);
    }

    public void saveRouteMap(Long shelterId, MultipartFile file) throws IOException {
        Optional<Shelter> shelterOpt = repository.findById(shelterId);
        if (shelterOpt.isPresent()) {
            Shelter shelter = shelterOpt.get();

            shelter.setMediaType(file.getContentType());
            shelter.setData(file.getBytes());
            repository.save(shelter);

=======

        } else {
            throw new RuntimeException("Shelter not found");
        }
    }
}
