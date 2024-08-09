package shelter.bot.botshelter.model;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

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

    public static List<String> MAIN_MENU_BUTTONS_TEXTS = new ArrayList<>(List.of(
            "Информация о приюте",
            "Как взять животное из приюта",
            "Прислать отчет о питомце",
            "Позвать волонтера"
    ));
    public static List<String> SUBMENU_NEW_USER= new ArrayList<>(List.of(
            "Расписание и адрес приюта",
            "Оформление пропуска и схема проезда",
            "Техника безопасности",
            "Запросить связь"
    ));
    public static List<String> SUBMENU_CONSULTATION = new ArrayList<>(List.of(
            "Список животных",
            "Правила знакомства и усыновления",
            "Список необходимых документов",
            "Рекомендации"
    ));
    public static List<String> SUBMENU_DOG_HANDLER = new ArrayList<>(List.of(
            "Советы кинолога",
            "Проверенные кинологи",
            "Причины отказа",
            "Запросить связь"
    ));
    public static List<String> SUBMENU_ADOPTION = new ArrayList<>(List.of(
            "Транспортировка животного",
            "Обустройство дома",
            "Обустройство дома для взрослого питомца",
            "Обустройство дома для питомца с ограниченными возможностями"
    ));

    // метод создания кнопок меню в оболочке отправляемого сообщения
    public SendMessage makeMenuMessage(Long chatId, String txt, List<String> buttons) {
        SendMessage sm = SendMessage.builder()
                .chatId(chatId.toString())
                .text(txt)
                .replyMarkup(new ReplyKeyboardMarkup(makeKeyboard(buttons))).build();
        return sm;
    }

    // приватный метод создания листа строк для
    private List<KeyboardRow> makeKeyboard(List<String> buttonTexts) {
        KeyboardRow buttons = new KeyboardRow();
        buttons.addAll(buttonTexts);
        return List.of(buttons);
    }

}
