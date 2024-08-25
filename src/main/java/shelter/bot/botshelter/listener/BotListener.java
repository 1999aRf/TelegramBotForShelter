package shelter.bot.botshelter.listener;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shelter.bot.botshelter.configuration.TelegramBotConfiguration;
import shelter.bot.botshelter.model.Menu;
import shelter.bot.botshelter.model.Shelter;
import shelter.bot.botshelter.services.ClientService;
import shelter.bot.botshelter.services.CommandHandlerService;


import static shelter.bot.botshelter.model.Menu.*;

import java.io.File;
import java.util.List;

@Component
public class BotListener implements UpdatesListener {
    private final CommandHandlerService commandHandlerService;

    private Logger logger = LoggerFactory.getLogger(BotListener.class);

    private final TelegramBot telegramBot;

    private final Menu menu;

    private ClientService clientService;

    @Autowired
    private TelegramBotConfiguration configuration;

    public BotListener(CommandHandlerService commandHandlerService, TelegramBot telegramBot, Menu menu, ClientService clientService) {
        this.commandHandlerService = commandHandlerService;
        this.telegramBot = telegramBot;
        this.menu = menu;
        this.clientService = clientService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        try {
            updates.stream()
                    .filter(update -> update.message() != null)
                    .forEach(this::processUpdate);
        } catch (Exception e) {
            logger.error("Error during processing telegram update", e);
        }

        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    private void processUpdate(Update update) {
        logger.info("Processing update: {}", update);

        Long chatId = update.message().chat().id();
        String text = update.message().text();
        commandHandlerService.handleCommand(chatId,text);
    }

    public void sendShelterInfo(Long chatId, Shelter shelter) {
        SendMessage message = new SendMessage(chatId, "Информация о приюте:\n" +
                "Название: " + shelter.getClientName() + "\n" +
                "Контактный номер: " + shelter.getContactNumber());

        // Отправляем текстовое сообщение с информацией
        telegramBot.execute(message);

        // Если есть схема проезда, отправляем её как фото
        if (shelter.getRouteMapUrl() != null) {
            SendPhoto sendPhoto = new SendPhoto(chatId, new File(shelter.getRouteMapUrl()));
            sendPhoto.caption("Схема проезда к приюту");
            telegramBot.execute(sendPhoto);
        }
    }
}
