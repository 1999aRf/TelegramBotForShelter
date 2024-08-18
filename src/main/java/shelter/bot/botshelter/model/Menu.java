package shelter.bot.botshelter.model;


import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import shelter.bot.botshelter.listener.BotListener;


import java.util.*;

/**
* С помощью данного класса создаются меню из набора кнопок.<br>
 * Меню - это набор кнопок с командами, которые впоследствии необходимо будет обработать.
 * Текст кнопок меню формируется из статических массивов строк.<br>
 * Для того, чтобы отправить меню пользователю используется метод {@code sendMenu}, принимающий
 * в качестве одного из аргументов одномерный или двумерный массив(если передать одномерный массив, то
 * все команды будут умещены в одну строку, двумерный массив используется для размещения команд в
 * несколько строк).
 * @author KhasanovTR
* */
@Component
public class Menu {
    private Logger logger = LoggerFactory.getLogger(Menu.class);

    /**
     * Метод предназначен для отправки меню пользователю. Аргументами выступают необходимые параметры
     * для формирования объекта отправляемого сообщения {@code SendMessage}.Туда же помещается класс меню
     * {@code ReplyKeyboardMarkup}. Отправляется сообщение вызовом статического метода
     * {@code execute}
     * @param chatId - идентификатор чата
     * @param txt - сообщение( как сопроводительное письмо), которое отправляется вместе с меню
     * @param button - одномерный массив, размещающий наименование всех команд массива в одной строке
     * @param bot - экземпляр класса {@code TelegramBot}, для вызова статического метода {@code execute}
     */
    public void sendMenu(Long chatId, String txt, String[] button, TelegramBot bot) {
        // создаем объект отправляемого сообщения, в который помещаем объект клавиатуры с одной кнопкой
        SendMessage msg = new SendMessage(chatId, txt)
                .replyMarkup(new ReplyKeyboardMarkup(button));
        SendResponse response = bot.execute(msg);
        if (!response.isOk()) {
            logger.info("{" + this.getClass() + "}:Меню не отправлено");
        }
    }

    /**
     * Метод предназначен для отправки меню пользователю. Аргументами выступают необходимые параметры
     * для формирования объекта отправляемого сообщения {@code SendMessage}.Туда же помещается класс меню
     * {@code ReplyKeyboardMarkup}.Отправляется сообщение вызовом статического метода
     * {@code execute}
     * @param chatId - идентификатор чата
     * @param txt - сообщение( как сопроводительное письмо), которое отправляется вместе с меню
     * @param buttons - двумерный массив, размещающий массивы команд в несколько строк, количество которых
     *                зависит от количества одномерных массивов
     * @param bot - экземпляр класса {@code TelegramBot}, для вызова статического метода {@code execute}
     */
    public void sendMenu(Long chatId, String txt, String[][] buttons, TelegramBot bot) {
        SendMessage msg = new SendMessage(chatId, txt)
                .replyMarkup(new ReplyKeyboardMarkup(buttons, true, false, false));
        SendResponse response = bot.execute(msg);
        if (!response.isOk()) {
            logger.info("{" + this.getClass() + "}:Меню не отправлено");
        }
    }

}
