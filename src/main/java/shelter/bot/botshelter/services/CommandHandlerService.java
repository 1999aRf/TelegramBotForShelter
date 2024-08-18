package shelter.bot.botshelter.services;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import shelter.bot.botshelter.configuration.TelegramBotConfiguration;
import shelter.bot.botshelter.listener.BotListener;
import shelter.bot.botshelter.model.Client;
import shelter.bot.botshelter.model.Menu;
import shelter.bot.botshelter.model.Volunteer;

import java.util.List;
import java.util.Optional;

import static shelter.bot.botshelter.constants.Commands.*;
import static shelter.bot.botshelter.constants.Texts.ASKING_TO_CHOOSE;
import static shelter.bot.botshelter.constants.Texts.NOT_FOUND;

@Service
public class CommandHandlerService {

    private Logger logger = LoggerFactory.getLogger(BotListener.class);

    private final TelegramBot bot;
    private final Menu menu;
    private ClientService clientService;
    private VolunteerService volunteerService;

    private TelegramBotConfiguration telegramBotConfiguration;

    public CommandHandlerService(TelegramBot bot,
                                 Menu menu,
                                 ClientService clientService,
                                 VolunteerService volunteerService,
                                 TelegramBotConfiguration telegramBotConfiguration) {
        this.bot = bot;
        this.menu = menu;
        this.clientService = clientService;
        this.volunteerService = volunteerService;
        this.telegramBotConfiguration = telegramBotConfiguration;
    }

    public void handleCommand(Long chatId, String command) {
        switch (command) {
            case START_COMMAND:
                Optional<Client> optional = clientService.findByChatId(chatId);
                if (optional.isEmpty()) {
                    clientService.saveClient(new Client(chatId));
                    menu.sendMenu(chatId,
                            telegramBotConfiguration.getStartMsg(),
                            MAIN_MENU_COMMANDS, bot);
                } else {
                    menu.sendMenu(chatId, ASKING_TO_CHOOSE, SUBMENU_NEW_USER, bot);
                }
                ;
                break;
            case CALL_VOLUNTEER:
                Optional<List<Volunteer>> optional1 = volunteerService.findAll();

                if (optional1.isPresent()) {

                    Optional<Volunteer> optionalVolunteer = getVolunteer();
                    if (optionalVolunteer.isPresent()) {
                        sendMessage(chatId, optionalVolunteer.get().toString());
                    } else {
                        sendMessage(chatId, "Волонтеры" + NOT_FOUND);
                    }

                } else {
                    sendMessage(chatId, "Волонтеры" + NOT_FOUND);
                }
                break;
            default:
                menu.sendMenu(chatId,
                        telegramBotConfiguration.getStartMsg(),
                        new String[]{START_COMMAND},
                        bot);
        }
    }

    private Optional<Volunteer> getVolunteer() {
        return volunteerService.findById(1);
    }

    // Универсальный метод для отправки сообщения
    private void sendMessage(Long chatId, String messageText) {
        SendMessage sendMessage = new SendMessage(chatId, messageText);
        sendMessage(sendMessage);
    }

    private void sendMessage(SendMessage sendMessage) {
        SendResponse response = bot.execute(sendMessage);
        if (!response.isOk()) {
            logger.error("Error during sending message: {}", response.description());
        }
    }
}
