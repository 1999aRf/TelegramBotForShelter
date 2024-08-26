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
import shelter.bot.botshelter.model.Volunteer;
import shelter.bot.botshelter.services.VolunteerService;

import java.util.List;
import java.util.Optional;

/**
 * Обработчик запросов к таблице БД сущности {@code Volunteer}<br>
 * Предназначен для разработчиков бота без доступа пользователю.
 *
 * @author KhasanovTR
 */
@RestController
@RequestMapping("api/volunteers")
@Tag(name = "Volunteer")
public class VolunteersController {

    private final VolunteerService service;

    public VolunteersController(VolunteerService service) {
        this.service = service;
    }

    /**
     * Эндпоинт добавляет волонтера в базу данных. Принимает объект класса (@code Volunteer) <br>
     * , куда входят все необходимые его данные для связи с ним
     *
     * @param volunteer объект сущности (@code Volunteer).Хранит необходимые данные о волонтере
     */

    @Operation(summary = "Добавление данных о волонтере",
            description = "Метод для добавления данных о волонтере:id,id чата,имя,контактный номер.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Волонтер успешно добавлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @PostMapping
    public ResponseEntity<Volunteer> addVolunteer(@RequestBody Volunteer volunteer) {

        if (volunteer == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.add(volunteer));
    }

    /**
     * Эндпоинт изменяет данные о волонтере в БД. Принимает объект класса (@code Volunteer).Выполняется
     * тот же метод, что и при выполнении эндпоинта {@code addVolunteer}
     *
     * @param volunteer объект сущности (@code Volunteer).Хранит необходимые данные о волонтере
     */
    @Operation(summary = "Редактирование данных о волонтере",
            description = "Метод для редактирования данных о волонтере:id,id чата,имя,контактный номер.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Волонтер успешно добавлен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Волонтер не найден", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @PutMapping
    public ResponseEntity<Volunteer> putVolunteer(@RequestBody Volunteer volunteer) {
        if (service.findById(volunteer.getId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(service.edit(volunteer));
    }

    /**
     * Находит волонтера по id в БД и возвращает объект класса (@code Volunteer)
     *
     * @param id идентификатор волонтера в БД (@code id)
     */
    @Operation(summary = "Поиск волонтера по id",
            description = "Метод для поиска волонтера по его id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Волонтер успешно найден",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = Volunteer.class))
                            )),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Волонтер не найден", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @GetMapping("/{id}")
    public ResponseEntity<Volunteer> findById(@PathVariable Long id) {
        Optional<Volunteer> volunteer = service.findById(id);
        return volunteer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Эндпоинт возвращает всех волонтеров в БД
     */
    @Operation(summary = "Получение всех зарегистрированных волонтеров",
            description = "Метод для получения списка всех волонтеров",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Список волонтеров успешно получен",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Volunteer.class))),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Волонтеры не найдены", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @GetMapping("/getAll")
    public ResponseEntity<List<Volunteer>> findAll() {
        Optional<List<Volunteer>> optional = service.findAll();
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Эндпоинт удаляет данные о волонтере в БД.
     *
     * @param id идентификатор волонтера в БД (@code id)
     */
    @Operation(summary = "Удаление волонтера",
            description = "Метод для удаления волонтера из БД по id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Волонтер удален из списка",content = @Content),
                    @ApiResponse(responseCode = "400", description = "Некорректные входные данные", content = @Content),
                    @ApiResponse(responseCode = "404", description = "Волонтеры не найдены", content = @Content),
                    @ApiResponse(responseCode = "500", description = "Ошибка на сервере", content = @Content)
            })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
