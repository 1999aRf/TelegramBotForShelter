package shelter.bot.botshelter.listener.Interface;

import com.pengrad.telegrambot.model.Update;

import java.util.List;

public interface TelegramLongPolling {
    int process(List<Update> updates);
}
