package shelter.bot.botshelter.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import shelter.bot.botshelter.listener.Interface.TelegramLongPolling;
import shelter.bot.botshelter.services.CommandHandlerService;

import java.util.List;

@Service
public class BotListener implements TelegramLongPolling, UpdatesListener {
    private final CommandHandlerService commandHandlerService;

    private Logger logger = LoggerFactory.getLogger(BotListener.class);

    private final TelegramBot telegramBot;

    public BotListener(CommandHandlerService commandHandlerService, TelegramBot telegramBot) {
        this.commandHandlerService = commandHandlerService;
        this.telegramBot = telegramBot;
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

        String rs = CommandHandlerService.handleCommand(chatId, text);
        sendMessage(chatId, rs);
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage(chatId, message);
        SendResponse response = telegramBot.execute(sendMessage);
        if (!response.isOk()) {
            logger.error("Error during sending message: {}", response.description());
        }
    }
}
