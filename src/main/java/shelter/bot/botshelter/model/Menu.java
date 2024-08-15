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

/*
* С помощью данного класса создаются меню из набора кнопок.<br> Текст кнопок меню
* формируется из статических коллекций строк. Они преобразуются в объекты класса (@code KeyboardRow)
* с помощью метода (@code makeKeyboard).<br>
* Для того, чтобы это меню отправить пользователю, используется метод (@code makeMenuMessage).
* В нем формируется объект списка клавиш(меню), помещается в оболочку класса отправляемого пользователю
* сообщения (@code SendMessage) и возвращается методом. Далее в классе-слушателе с помощью метода (@code execute())
* будет отправлено пользователю.
* */
@Component
public class Menu {
    private Logger logger = LoggerFactory.getLogger(Menu.class);

    // Метод для создания сообщения с кнопкой "Start"
    public SendMessage createStartMenu(Long chatId) {
        // Создаем клавиатуру с одной строкой и одной кнопкой "Start"
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(
                new String[]{"Start"}) // одна строка с одной кнопкой
                .resizeKeyboard(true)   // Клавиатура подстраивается под размер экрана
                .oneTimeKeyboard(true); // Клавиатура исчезает после использования

        // Возвращаем сообщение с клавиатурой
        return new SendMessage(chatId, "Нажмите кнопку 'Start' для начала работы.")
                .replyMarkup(keyboardMarkup);
    }




    /*// метод создания кнопок меню в оболочке отправляемого сообщения
    public SendMessage makeMenuMessage(Long chatId, String txt, List<String> buttons) {
        SendMessage sm = new SendMessage(chatId,txt)
                .replyMarkup(new ReplyKeyboardMarkup(makeKeyboard(MAIN_MENU_BUTTONS_TEXTS)));
        return sm;
    }

    // приватный метод создания листа строк для
    private List<KeyboardRow> makeKeyboard(List<String> buttonTexts) {
        KeyboardRow buttons = new KeyboardRow();
        buttons.addAll(buttonTexts);
        return List.of(buttons);
    }*/

    public void sendMenu(Long chatId, String txt, String[][] buttons, TelegramBot bot) {
        SendMessage msg = new SendMessage(chatId, txt)
                .replyMarkup(new ReplyKeyboardMarkup(buttons, true, false, false));
        SendResponse response = bot.execute(msg);
        if (!response.isOk()) {
            logger.info("{" + this.getClass() + "}:Меню не отправлено");
        }
    }

}
