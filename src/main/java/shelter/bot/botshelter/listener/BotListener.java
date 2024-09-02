package shelter.bot.botshelter.listener;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetFileResponse;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shelter.bot.botshelter.configuration.TelegramBotConfiguration;
import shelter.bot.botshelter.model.Menu;
import shelter.bot.botshelter.services.ClientService;
import shelter.bot.botshelter.services.CommandHandlerService;

import java.io.IOException;
import java.util.Arrays;
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
        Message message = update.message();
            commandHandlerService.handleCommand(message);
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            logger.error("Error during sending message: {}", response.description());
        }
    }

}