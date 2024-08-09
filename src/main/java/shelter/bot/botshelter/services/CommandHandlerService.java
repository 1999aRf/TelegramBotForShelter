package shelter.bot.botshelter.services;

import org.springframework.stereotype.Service;
import shelter.bot.botshelter.configuration.TelegramBotConfiguration;
@Service
public class CommandHandlerService {
    private static TelegramBotConfiguration telegramBotConfiguration;

    public CommandHandlerService(TelegramBotConfiguration telegramBotConfiguration) {
        this.telegramBotConfiguration = telegramBotConfiguration;
    }
    private static final String START_COMMAND = "/start";

    public static String handleCommand(Long chatId, String command) {
        switch (command) {
            case START_COMMAND:
                return telegramBotConfiguration.getStartMsg();
            default:
                return "Я не понимаю эту команду. Попробуйте другую команду.";
        }
    }
}
