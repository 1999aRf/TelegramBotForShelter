package shelter.bot.botshelter.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import shelter.bot.botshelter.configuration.TelegramBotConfiguration;
import shelter.bot.botshelter.listener.BotListener;
import shelter.bot.botshelter.model.Client;
import shelter.bot.botshelter.model.Menu;
import shelter.bot.botshelter.model.Shelter;
import shelter.bot.botshelter.model.Volunteer;
import shelter.bot.botshelter.services.interfaces.CommandHandler;

import java.io.File;
import java.util.List;
import java.util.Optional;

import static shelter.bot.botshelter.constants.Texts.*;

@Service
public class CommandHandlerService implements CommandHandler {

    private Logger logger = LoggerFactory.getLogger(BotListener.class);

    private final TelegramBot bot;
    private final Menu menu;
    private final ClientService clientService;
    private final VolunteerService volunteerService;
    private final ShelterService shelterService;

    private final TelegramBotConfiguration telegramBotConfiguration;

    public CommandHandlerService(TelegramBot bot, Menu menu, ClientService clientService, VolunteerService volunteerService, ShelterService shelterService, TelegramBotConfiguration telegramBotConfiguration) {
        this.bot = bot;
        this.menu = menu;
        this.clientService = clientService;
        this.volunteerService = volunteerService;
        this.shelterService = shelterService;
        this.telegramBotConfiguration = telegramBotConfiguration;
    }

    /*private byte chosenShelter = 0; // хранит выбранный приют:с собаками или кошками(1\2)
    private long chatIdChosen; // хранит id чата, кто и выбрал приют с кошками или собаками*/
    private String prevCommand; // хранит название предыдущей команды

    public void handleCommand(Message message) {

        Long chatId = message.chat().id();
        String command = message.text();

        logger.info("HandlerCommand метод запущен");


        if (checkContains(MAIN_MENU_COMMANDS, command)) {
            handleMainCommands(chatId, command);
        } else if (checkContains(SUBMENU_NEW_USER, command)) {
            handleNewUserCommands(chatId, command);
        } else if (checkContains(SUBMENU_CONSULTATION, command)) {
            handleConsultationCommands(chatId, command);
        } else if (checkContains(SUBMENU_DOG_HANDLER, command)) {
            handleDogHandlerCommands(chatId, command);
        } else if (checkContains(SUBMENU_ADOPTION, command)) {
            handleAdoptionCommands(chatId, command);
        } else {
            logger.info("обработка базовых команд");

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
                    handleUserText(chatId, command);
            }
        }

        prevCommand = command;
    }

    private Optional<Volunteer> getVolunteer() {
        return volunteerService.findById(1);
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

        switch (command) {
            case MAIN_COMMAND1:

                menu.sendMenu(chatId, SHElTER_DOG_INFO, SUBMENU_NEW_USER, bot);

                break;
            case MAIN_COMMAND2:

                break;

            case MAIN_COMMAND3:

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
    public void handleChooseShelter(Long chatId, String command) {
        /*if (command.equals(CHOOSE_SHELTER_COMMAND1)) {
            chatIdChosen = chatId;
            chosenShelter = CHOSEN_DOGS;


        } else if (command.equals(CHOOSE_SHELTER_COMMAND2)) {
            chatIdChosen = chatId;
            chosenShelter = CHOSEN_CATS;
        } else {
            menu.sendMenu(chatId,
                    "Вы не выбрали приют!!!",
                    CHOOSE_SHELTER_COMMANDS,
                    bot);
        }
        menu.sendMenu(chatId,
                "Что выберете",
                MAIN_MENU_COMMANDS,
                bot);*/
    }

    //______________________________________________________________________________________
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    // нет реализации принятия данных о пользователе для связи
    // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    //______________________________________________________________________________________
    @Override
    public void handleNewUserCommands(Long chatId, String command) {

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
        switch (command) {
            case CONSULTATION_COMMAND1:

                break;
            case CONSULTATION_COMMAND2:

                break;

            case CONSULTATION_COMMAND3:

                break;
            case CONSULTATION_COMMAND4:

                break;
        }
    }

    @Override
    public void handleDogHandlerCommands(Long chatId, String command) {
        switch (command) {
            case DOG_HANDLER_COMMAND1:

                break;
            case DOG_HANDLER_COMMAND2:

                break;

            case DOG_HANDLER_COMMAND3:

                break;

        }
    }

    @Override
    public void handleAdoptionCommands(Long chatId, String command) {
        switch (command) {
            case ADOPTION_COMMAND1:

                break;
            case ADOPTION_COMMAND2:

                break;

            case ADOPTION_COMMAND3:

                break;

            case ADOPTION_COMMAND4:

                break;

        }
    }

    @Override
    public void handleUserText(Long chatId, String command) {
        logger.info("обработка номера");

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
                return;

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
}