package shelter.bot.botshelter.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import shelter.bot.botshelter.model.Animal;
import shelter.bot.botshelter.services.AnimalService;

import java.util.List;
import java.util.Optional;

/**
 * Обработчик запросов к таблице БД сущности {@code Animal}<br>
 * Предназначен для разработчиков бота без доступа пользователю.
 *
 * @author KhasanovTR
 */
@RestController
@RequestMapping("api/animals")
@Tag(name = "Animal", description = "API для работы с питомцами")
public class AnimalsController {

    private final AnimalService service;

    public AnimalsController(AnimalService service) {
        this.service = service;
    }

    /**
     * Эндпоинт добавляет питомца в базу данных. Принимает объект класса (@code Animal) <br>
     * , куда входят все необходимые его данные для связи с ним
     *
     * @param animal объект сущности (@code Animal).Хранит необходимые данные о питомце
     */

    @Operation(summary = "Добавление данных о питомце",
            description = "Метод для добавления данных о питомце",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Питомец успешно добавлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Animal.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @PostMapping
    public ResponseEntity<Animal> addAnimal(@RequestBody Animal animal) {

        if (animal == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.add(animal));
    }

    /**
     * Эндпоинт изменяет данные о питомце в БД. Принимает объект класса (@code Animal).Выполняется
     * тот же метод, что и при выполнении эндпоинта {@code addAnimal}
     *
     * @param animal объект сущности (@code Animal).Хранит необходимые данные о питомце
     */
    @Operation(summary = "Редактирование данных о питомце",
            description = "Метод для редактирования данных о питомце",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Питомец успешно добавлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Animal.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Питомец не найден", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @PutMapping
    public ResponseEntity<Animal> putAnimal(@RequestBody Animal animal) {
        if (service.findById(animal.getId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.edit(animal));
    }

    /**
     * Находит питомца по id в БД и возвращает объект класса (@code Animal)
     *
     * @param id идентификатор питомца в БД (@code id)
     */
    @Operation(summary = "Поиск питомца по id",
            description = "Метод для поиска питомца по его id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Питомец успешно найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Animal.class))
                            )),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Питомец не найден", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @GetMapping("/{id}")
    public ResponseEntity<Animal> findById(@PathVariable Long id) {
        Optional<Animal> animal = service.findById(id);
        return animal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Эндпоинт возвращает всех питомцов в БД
     */
    @Operation(summary = "Получение всех зарегистрированных питомцов",
            description = "Метод для получения списка всех питомцов",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список питомцов успешно получен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Animal.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Питомцы не найдены", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @GetMapping("/getAll")
    public ResponseEntity<List<Animal>> findAll() {
        Optional<List<Animal>> optional = service.findAll();
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Эндпоинт удаляет данные о питомце в БД.
     *
     * @param id идентификатор питомца в БД (@code id)
     */
    @Operation(summary = "Удаление питомца",
            description = "Метод для удаления питомца из БД по id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Питомец удален из списка",content = @Content),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Питомец не найдены", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
