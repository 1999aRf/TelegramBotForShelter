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
import shelter.bot.botshelter.model.Adoptions;
import shelter.bot.botshelter.model.Animal;
import shelter.bot.botshelter.model.Client;
import shelter.bot.botshelter.model.Shelter;
import shelter.bot.botshelter.services.AdoptionService;
import shelter.bot.botshelter.services.AnimalService;
import shelter.bot.botshelter.services.ClientService;
import shelter.bot.botshelter.services.ShelterService;

import java.util.List;
import java.util.Optional;

/**
 * Обработчик запросов к таблице adoptions(усыновления) в бД
 */
@RestController
@RequestMapping("api/adoptions")
@Tag(name = "Adoptions",description = "API для работы с усыновлениями")
public class AdoptionsController {
    private final AdoptionService service;
    private final ClientService clientService;
    private final AnimalService animalService;

    public AdoptionsController(AdoptionService service, ClientService clientService, AnimalService animalService) {
        this.service = service;
        this.clientService = clientService;
        this.animalService = animalService;
    }

    /**

     *
             * @param adoptions объект сущности (@code Adoptions).Хранит необходимые данные о усыновлениях
     */
    /**
     * Эндпоинт добавляет усыновление в базу данных. Принимает объект класса (@code Adoptions) <br>
     * , куда входят все необходимые его данные для связи с ним
     * @param chatId - id чата клиента
     * @param animalId - id питомца
     */

    @Operation(summary = "Добавление данных об усыновлениях",
            description = "Метод для добавления данных об усыновлениях",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Усыновление успешно добавлено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Adoptions.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @PostMapping("addAdoption/{chatId}/{animalId}")
    public ResponseEntity<Adoptions> addAdoptions(@PathVariable Long chatId,
                                                  @PathVariable Long animalId) {

        Optional<Animal> animalOptional = animalService.findById(animalId);
        Optional<Client> clientOptional = clientService.findByChatId(chatId);
        Animal animal;
        Client client;
        if (clientOptional.isPresent()) {
            client = clientOptional.get();
        }else{
            return ResponseEntity.notFound().header("Клиент не найден").build();}
        if (animalOptional.isPresent()) {
            animal = animalOptional.get();
        }else{
            return ResponseEntity.notFound().header("Питомец не найден").build();
        }
        return ResponseEntity.ok(service.add(new Adoptions(animal, client)));
    }

    /**
     * Эндпоинт изменяет данные о усыновлениях в БД. Принимает объект класса (@code Adoptions).Выполняется
     * тот же метод, что и при выполнении эндпоинта {@code addAdoptions}
     *
     * @param adoptions объект сущности (@code Adoptions).Хранит необходимые данные о усыновлении
     */
    @Operation(summary = "Редактирование данных о усыновлениях",
            description = "Метод для редактирования данных об усыновлении",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Усыновление успешно добавлено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Adoptions.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Усыновление не найдено", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @PutMapping
    public ResponseEntity<Adoptions> putAdoptions(@RequestBody Adoptions adoptions) {
        if (service.findById(adoptions.getId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.edit(adoptions));
    }

    /**
     *  Эндпоит позволяет выставить результат подотчетного периода для усыновления
     * @param adoptionId - id усыновления
     * @param result - код результа(1 - прошел/ 2 - не прошел)
     */
    @Operation(summary = "Выставление результата подотчетного периода",
            description = "Метод для выставления результат подотчетного периода",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Усыновление успешно изменено"),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Усыновление не найдено", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @PutMapping("/setResult/{adoptionId}/{result}")
    public ResponseEntity<Adoptions> putAdoptionsSetReviewed(@PathVariable Long adoptionId,@PathVariable Integer result) {
        Optional<Adoptions> byId = service.findById(adoptionId);
        if (!byId.isPresent()) {
            return ResponseEntity.notFound().build();
        } else {
            Adoptions adoptions = byId.get();
            adoptions.setResult(1);
            return ResponseEntity.ok(service.edit(adoptions));
        }
    }

    /**
     * Находит усыновление по id в БД и возвращает объект класса (@code Adoptions)
     *
     * @param id идентификатор усыновления в БД (@code id)
     */
    @Operation(summary = "Поиск усыновления по id",
            description = "Метод для поиска усыновления по его id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Усыновление успешно найдено",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Adoptions.class))
                            )),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Усыновление не найдено", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @GetMapping("/{id}")
    public ResponseEntity<Adoptions> findById(@PathVariable Long id) {
        Optional<Adoptions> adoptions = service.findById(id);
        return adoptions.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Эндпоинт возвращает все усыновления в БД
     */
    @Operation(summary = "Получение всех зарегистрированных усыновлений",
            description = "Метод для получения списка всех усыновлений",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список усыновлениеий успешно получен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Adoptions.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Усыновления не найдены", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @GetMapping("/getAll")
    public ResponseEntity<List<Adoptions>> findAll() {
        Optional<List<Adoptions>> optional = service.findAll();
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Эндпоинт удаляет данные об усыновлении в БД.
     *
     * @param id идентификатор усыновления в БД (@code id)
     */
    @Operation(summary = "Удаление усыновления",
            description = "Метод для удаления усыновления из БД по id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Усыновление удалено из списка",content = @Content),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Усыновления не найдены", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }

}
