package shelter.bot.botshelter.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import shelter.bot.botshelter.configuration.TelegramBotConfiguration;
import shelter.bot.botshelter.exceptions.DownloadPhotoException;
import shelter.bot.botshelter.listener.BotListener;
import shelter.bot.botshelter.model.*;
import shelter.bot.botshelter.services.interfaces.CommandHandler;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static shelter.bot.botshelter.constants.Texts.*;

@Singleton
@Service
public class CommandHandlerService implements CommandHandler {

    private Logger logger = LoggerFactory.getLogger(BotListener.class);

    private final TelegramBot bot;
    private final Menu menu;
    private final ClientService clientService;
    private final VolunteerService volunteerService;
    private final ShelterService shelterService;
    private final AdoptionService adoptionService;
    private final ReportServiceImpl reportService;

    private final TelegramBotConfiguration telegramBotConfiguration;

    public CommandHandlerService(TelegramBot bot, Menu menu, ClientService clientService, VolunteerService volunteerService, ShelterService shelterService, AdoptionService adoptionService, ReportServiceImpl reportService, TelegramBotConfiguration telegramBotConfiguration) {
        this.bot = bot;
        this.menu = menu;
        this.clientService = clientService;
        this.volunteerService = volunteerService;
        this.shelterService = shelterService;
        this.adoptionService = adoptionService;
        this.reportService = reportService;
        this.telegramBotConfiguration = telegramBotConfiguration;
    }

    private String prevCommand; // хранит название предыдущей команды
    private Report report;
    private Adoptions adoption;


    public void handleCommand(Message message)  {

        Long chatId = message.chat().id();
        String command = message.text();

        logger.info("Обработчик команд запущен для чата: {}",chatId);

        if (checkContains(MAIN_MENU_COMMANDS, command)) {
            handleMainCommands(chatId, command);
        } else if (checkContains(SUBMENU_NEW_USER, command)) {
            handleNewUserCommands(chatId, command);
        } else if (checkContains(SUBMENU_CONSULTATION, command)) {
            handleConsultationCommands(chatId, command);
        } else if (checkContains(SUBMENU_ADOPTION, command)) {
            handleAdoptionCommands(chatId, command);
        } else if (checkContains(SUBMENU_DOG_HANDLER, command)) {
            handleDogHandlerCommands(chatId, command);
        } else {
            logger.info("Обработчик базовых команд запущен для чата: {}",chatId);

            switch (command) {

                case START_COMMAND:
                    Optional<Client> optional = clientService.findByChatId(chatId);
                    if (optional.isEmpty()) {
                        clientService.saveClient(new Client(
                                chatId, //идентификатор чата
                                message.from().firstName() + message.from().lastName()) // полное имя клиента
                        );
                        menu.sendMenu(chatId,
                                telegramBotConfiguration.getStartMsg(),
                                MAIN_MENU_COMMANDS, bot);
                    } else {
                        menu.sendMenu(chatId, ASKING_TO_CHOOSE, SUBMENU_NEW_USER, bot);
                    }
                    ;
                    break;
                case CALL_VOLUNTEER:
                    logger.info(CALL_VOLUNTEER + " команда запущена");

                    Optional<List<Volunteer>> optional1 = volunteerService.findAll();

                    if (optional1.isPresent()) {
                        logger.info(CALL_VOLUNTEER + " в списке найдены волонтеры");
                        Volunteer volunteer = optional1.get().get(0);
                        sendMessage(chatId, volunteer.toString());

                    } else {
                        sendMessage(chatId, "Волонтеры" + NOT_FOUND);
                        logger.info("волонтеров на самом деле нет");
                    }
                    break;

                default:
                    handleUserText(message);
            }
        }

        prevCommand = command;
    }

    // Универсальный метод для отправки сообщения
    private void sendMessage(Long chatId, String messageText) {
        SendResponse response = bot.execute(new SendMessage(chatId, messageText));
        if (!response.isOk()) {
            logger.error("Error during sending message: {}", response.description());
        }
    }

    @Override
    public void handleMainCommands(Long chatId, String command) {
        logger.info("Обработчик команд главного меню запущен для чата: {}",chatId);
        switch (command) {
            case MAIN_COMMAND1:

                menu.sendMenu(chatId, SHElTER_DOG_INFO, SUBMENU_NEW_USER, bot);

                break;
            case MAIN_COMMAND2:
                menu.sendMenu(chatId, CONSULTATION_MENU, SUBMENU_CONSULTATION, bot);
                break;

            case MAIN_COMMAND3:
                menu.sendMenu(chatId,
                        CHOOSE_REPORT,
                        SUBMENU_REPORT,
                        bot);
                break;

            case MAIN_COMMAND4:

                break;
            case CHOSE_MENU:
                menu.sendMenu(chatId,
                        "Какое животное желаете приютить?",
                        CHOOSE_SHELTER_COMMANDS,
                        bot);
                break;
        }
    }


    @Override
    public void handleNewUserCommands(Long chatId, String command) {
        logger.info("Обработчик команд для пользователя запущен для чата: {}",chatId);
        switch (command) {
            case NEW_USER_COMMAND1:

                Shelter shelter = shelterService.getShelterById(1L).get();
                sendMessage(chatId, SHElTER_DOG_INFO);
                menu.sendMenu(chatId,
                        "Расписание: " + shelter.getBusinessTime(),
                        SUBMENU_NEW_USER,
                        bot);


                break;
            case NEW_USER_COMMAND2:


                sendShelterInfo(chatId, shelterService.getShelterById(1L).get());
                menu.sendMenu(chatId, "Можем вам предложить", SUBMENU_NEW_USER, bot);


                break;

            case NEW_USER_COMMAND3:
                menu.sendMenu(chatId, SAFETY_RULES, SUBMENU_NEW_USER, bot);
                break;
            case NEW_USER_COMMAND4:
                sendMessage(chatId, "Введите номер согласно маске +7-9**-***-**-");
                break;
            case MAIN_MENU:
                menu.sendMenu(chatId,
                        "Что выберете",
                        MAIN_MENU_COMMANDS, bot);
                break;
        }
    }

    @Override
    public void handleConsultationCommands(Long chatId, String command) {
        logger.info("Обработчик команд меню консультации запущен для чата: {}",chatId);
        switch (command) {
            case CONSULTATION_COMMAND1:

                Optional<Shelter> shelterById = shelterService.getShelterById(1L);
                if (shelterById.isPresent()) { // существует ли какой-нибудь приют в бд
                    Optional<List<Animal>> availableAnimals = Optional.ofNullable(
                            shelterById.get().getAvailableAnimals()
                    );
                    if (availableAnimals.isPresent()) { // есть ли в приюте какие-либо животные
                        menu.sendMenu(chatId,
                                availableAnimals.get().toString(),
                                SUBMENU_CONSULTATION,
                                bot);
                    } else {
                        sendMessage(chatId, ANIMALS_ARE_NOT_AVAILABLE);
                    }

                } else {
                    sendMessage(chatId, SHELTER_IS_NOT_AVAILABLE);

                }
                break;
            case CONSULTATION_COMMAND2:
                sendMessage(chatId, RULES_OF_ACQUAINTANCE_AND_ADOPTION);

                break;

            case CONSULTATION_COMMAND3:
                sendMessage(chatId, NEEDED_DOCS);
                break;
            case CONSULTATION_COMMAND4:
                menu.sendMenu(chatId,
                        "Доступный список рекомендаций",
                        SUBMENU_ADOPTION,
                        bot);
                break;
        }
    }

    @Override
    public void handleDogHandlerCommands(Long chatId, String command) {
        logger.info("Обработчик команд меню рекомендаций кинолога запущен для чата: {}",chatId);
        switch (command) {
            case DOG_HANDLER_COMMAND1:
                sendMessage(chatId, DOG_HANDLERS_RECOMMENDATIONS);
                break;
            case DOG_HANDLER_COMMAND2:
                sendMessage(chatId, DOG_HANDLER_LIST);
                break;

            case DOG_HANDLER_COMMAND3:
                sendMessage(chatId, REASONS_FOR_REFUSAL);
                break;
            case CONSULTATION_MENU:
                menu.sendMenu(chatId,
                        CONSULTATION_MENU,
                        SUBMENU_CONSULTATION,
                        bot);
                break;

        }
    }

    @Override
    public void handleAdoptionCommands(Long chatId, String command) {
        logger.info("Обработчик команд меню для усыновления запущен для чата: {}",chatId);
        switch (command) {
            case ADOPTION_COMMAND1:
                sendMessage(chatId, RULES_ANIMAL_TRANSPORTATION);

                break;
            case ADOPTION_COMMAND2:
                sendMessage(chatId, APARTMENT_PREPARATIONS_FOR_PUPPY);

                break;

            case ADOPTION_COMMAND3:
                sendMessage(chatId, APARTMENT_PREPARATIONS_FOR_GROWNUPS);
                break;

            case ADOPTION_COMMAND4:
                sendMessage(chatId, APARTMENT_PREPARATIONS_FOR_DISABLED);
                break;
            case DOG_HANDLER_MENU:
                menu.sendMenu(chatId,
                        DOG_HANDLER_MENU,
                        SUBMENU_DOG_HANDLER,
                        bot);
                break;
            case MAIN_MENU:
                menu.sendMenu(chatId,
                        MAIN_MENU,
                        MAIN_MENU_COMMANDS,
                        bot);
                break;

        }
    }

    @Override
    public void handleReportCommands(Message message) {
        Long chatId = message.chat().id();
        String command = message.text();
        switch (command) {
            case REPORT_COMMAND1:
                menu.sendMenu(chatId,
                        "Введите информацию о питании питомца",
                        SUBMENU_REPORT,
                        bot);
                break;
            case REPORT_COMMAND2:
                menu.sendMenu(chatId,
                        "Введите информацию о самочувствии питомца",
                        SUBMENU_REPORT,
                        bot);
                break;
            case REPORT_COMMAND3:
                menu.sendMenu(chatId,
                        "Введите информацию о поведении питомца питомца",
                        SUBMENU_REPORT,
                        bot);
                break;
            case REPORT_COMMAND4:
                menu.sendMenu(chatId,
                        "Отправьте фото-отчет",
                        SUBMENU_REPORT,
                        bot);
                break;
            case MAIN_MENU:
                menu.sendMenu(chatId,
                        MAIN_MENU,
                        MAIN_MENU_COMMANDS,
                        bot);
                break;
            default:
                if (prevCommand != null && !prevCommand.isEmpty() && prevCommand.equals(REPORT_COMMAND1)) {
                    handleReportCommand1(chatId, command);
                } else if (prevCommand != null && !prevCommand.isEmpty() && prevCommand.equals(REPORT_COMMAND2)) {
                    handleReportCommand2(chatId, command);
                } else if (prevCommand != null && !prevCommand.isEmpty() && prevCommand.equals(REPORT_COMMAND3)) {
                    handleReportCommand3(chatId, command);
                } else if (prevCommand != null && !prevCommand.isEmpty() && prevCommand.equals(REPORT_COMMAND4)) {
                    try {
                        handleReportCommand4(message);
                    } catch (DownloadPhotoException ex) {
                        logger.error("Ошибка при загрузке фото для чата {}: {}", chatId, ex.getMessage());
                    }

                }
                break;
        }
        if (prevCommand != null && !prevCommand.isEmpty() && prevCommand.equals(MAIN_COMMAND3)) {

        }
    }


    private void handleReportCommand1(Long chatId, String command) {
        if (!command.isEmpty() & !command.isBlank()) {
            report = new Report();
            report.setDiet(command);
        } else {
            menu.sendMenu(chatId,
                    "Нажмите еще раз на кнопку меню " + REPORT_COMMAND1 + " и введите отчет о питании питомца" +
                            "корректно",
                    SUBMENU_REPORT,
                    bot);
        }

    }

    private void handleReportCommand2(Long chatId, String command) {
        if (!command.isEmpty() & !command.isBlank()) {
            report.setWellbeing(command);
        } else {
            menu.sendMenu(chatId,
                    "Нажмите еще раз на кнопку меню " + REPORT_COMMAND2 + " и введите отчет о самочувствии питомца" +
                            "корректно",
                    SUBMENU_REPORT,
                    bot);
        }

    }

    private void handleReportCommand3(Long chatId, String command) {
        if (!command.isEmpty() & !command.isBlank()) {
            report.setBehaviorChanges(command);
        } else {
            menu.sendMenu(chatId,
                    "Нажмите еще раз на кнопку меню " + REPORT_COMMAND2 + " и введите отчет о поведении питомца" +
                            "корректно",
                    SUBMENU_REPORT,
                    bot);
        }

    }

    private void handleReportCommand4(Message message) throws DownloadPhotoException {
        if (message.photo() != null && message.photo().length > 0) {
            logger.info("Получено фото для фото-отчета для чата{}. На очереди скачивание файла для сохранения в БД", message.chat().id());
            GetFileResponse response = bot.execute(new GetFile(Arrays.stream(message.photo()).findFirst().get().fileId()));
            logger.info("Ответ на получение скачивание фото:{}", response);
            if (response.isOk()) {
                try {
                    byte[] image = bot.getFileContent(response.file());
                    report.setPhoto(image);
                    report.setAdoption(adoption);
                    report.setReviewed(false);
                    report.setAnimal(adoption.getAnimal());
                    report.setDate(LocalDate.now());
                    reportService.saveReport(report);
                } catch (IOException ex) {
                    logger.error("Ошибка при обработке фото для чата {}: {}", message.chat().id(), ex.getMessage());
                    sendMessage(message.chat().id(), "Простите, произошла ошибка при обработке фото. Пожалуйста, попробуйте еще раз.");
                }
            } else {
                logger.error("Не удалось получить файл фото: {}", response.errorCode());
                sendMessage(message.chat().id(), "Произошла ошибка при получении файла фото. Попробуйте еще раз.");
            }

        } else {
            logger.info("Сообщение не содержит фото для чата {}", message.chat().id());
            menu.sendMenu(message.chat().id(),
                    "Нажмите еще раз на кнопку меню " + REPORT_COMMAND4 + " и отправьте фото снова",
                    SUBMENU_REPORT,
                    bot);
        }
    }


    @Override
    public void handleUserText(Message message) {

        Long chatId = message.chat().id();
        String command = message.text();
        logger.info("Обработка текста для чата : {}", message.chat().id());

        // обработка номера. Если текущий текст предыдущая команда была "принять данные для связи",
        // то следует проверка на соответствие маске и сохранение номера для соответствующего клиента
        // иначе код будет расценивать команду недействительной и отправит пользователя на старт
        if (prevCommand != null && !prevCommand.isEmpty() && prevCommand.equals(NEW_USER_COMMAND4)) {
            if (validateNumber(command)) {
                Client client = clientService.findByChatId(chatId).get();
                client.setContactNumber(command);
                clientService.saveClient(client);
                menu.sendMenu(chatId,
                        NUMBER_SAVED_SUCCESS,
                        SUBMENU_NEW_USER,
                        bot);
            } else {
                sendMessage(chatId, "Введите корректный номер согласно маске +7-9**-***-**-");
            }
        } else {
            menu.sendMenu(chatId,
                    telegramBotConfiguration.getStartMsg(),
                    new String[]{START_COMMAND},
                    bot);
        }
    }


    public void sendShelterInfo(Long chatId, Shelter shelter) {
        SendMessage message = new SendMessage(chatId,
                "Контактный номер: " + shelter.getContactNumber());

        // Отправляем текстовое сообщение с информацией
        bot.execute(message);

        // Если есть схема проезда, отправляем её как фото
        if (shelter.getData() != null) {
            SendPhoto sendPhoto = new SendPhoto(chatId, shelter.getData());
            sendPhoto.caption("Схема проезда к приюту");
            bot.execute(sendPhoto);
        }
    }

    private Adoptions getThisClientAdoption(Long chatId) {
        // найдем усыновления с действующим подотчетным периодом
        Optional<List<Adoptions>> currentAdoptionsOptional = adoptionService.findByProbationPeriodLessNow();
        if (currentAdoptionsOptional.isPresent()) {
            // Проверяем, есть ли у данного клиента усыновления
            List<Adoptions> thisClientAdoptionsList = currentAdoptionsOptional.get().stream().
                    filter(e -> Objects.equals(e.getClient().getChatId(), chatId)).
                    toList();
            // далее можно обработать все итерации, но в данной версии программы остановимся только
            // на одном усыновлении, то есть у клиента может быть только одно усыновление
            Adoptions thisClientAdoption = thisClientAdoptionsList.stream().findFirst().get();
            return thisClientAdoption;
        }
        return null;
    }


}