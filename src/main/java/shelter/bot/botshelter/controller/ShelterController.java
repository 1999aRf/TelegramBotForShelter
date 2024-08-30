package shelter.bot.botshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import shelter.bot.botshelter.model.Shelter;
import shelter.bot.botshelter.services.ShelterService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления приютами.
 * Обрабатывает запросы на создание, обновление, удаление и получение информации о приютах.
 */

@RestController
@RequestMapping("/api/shelters")
@Tag(name = "Shelter Controller", description = "API для работы с приютами")
public class ShelterController {
    private final ShelterService shelterService;

    public ShelterController(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    /**
     * Получить список всех приютов.
     *
     * @return Список всех приютов.
     */
    @Operation(summary = "Получить все приюты", description = "Возвращает список всех приютов")
    @GetMapping
    public List<Shelter> getAllShelters() {
        return shelterService.getAllShelters();
    }

    /**
     * Получить информацию о приюте по его ID.
     *
     * @param id Идентификатор приюта.
     * @return Информация о приюте.
     */
    @Operation(summary = "Получить приют по ID", description = "Возвращает информацию о приюте по его ID")
    @GetMapping("/{id}")
    public ResponseEntity<Shelter> getShelterById(@PathVariable Long id) {
        Optional<Shelter> shelter = shelterService.getShelterById(id);
        return shelter.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Создать новый приют.
     *
     * @param shelter Данные нового приюта.
     * @return Созданный приют.
     */
    @Operation(summary = "Создать новый приют", description = "Создает новый приют и возвращает его данные")
    @PostMapping
    public Shelter createShelter(@RequestBody Shelter shelter) {
        return shelterService.createShelter(shelter);
    }

    /**
     * Обновить существующий приют.
     *
     * @param id             Идентификатор приюта.
     * @param shelterDetails Обновленные данные приюта.
     * @return Обновленный приют.
     */
    @Operation(summary = "Обновить приют", description = "Обновляет существующий приют и возвращает его данные")
    @PutMapping("/{id}")
    public ResponseEntity<Shelter> updateShelter(@PathVariable Long id, @RequestBody Shelter shelterDetails) {
        Shelter updatedShelter = shelterService.updateShelter(id, shelterDetails);
        return ResponseEntity.ok(updatedShelter);
    }

    /**
     * Удалить приют по его ID.
     *
     * @param id Идентификатор приюта.
     * @return Статус ответа.
     */
    @Operation(summary = "Удалить приют", description = "Удаляет приют по его ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShelter(@PathVariable Long id) {
        shelterService.deleteShelter(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Загружает схему проезда для приюта.
     *
     * @param id   Идентификатор приюта.
     * @param file Файл с изображением схемы проезда.
     * @return Статус ответа с URL загруженного файла.
     */
    @Operation(summary = "Загрузить схему проезда", description = "Загружает изображение схемы проезда для приюта и возвращает URL файла.")
    @PostMapping(value = "/{id}/uploadMap", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadRouteMap(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            // Проверяем существование приюта по ID
            Shelter shelter = shelterService.getShelterById(id)
                    .orElseThrow(() -> new RuntimeException("Shelter not found"));

            // Сохраняем изображение схемы проезда в базу данных
            byte[] imageData = file.getBytes();

            // Обновить данные приюта, включая загруженную схему проезда
            shelterService.updateRouteMap(id, imageData); // Обновляем информацию о приюте, включая загруженную карту

            return ResponseEntity.ok("Route map uploaded successfully for shelter ID: " + id);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file due to I/O error");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload file");
        }
    }
}