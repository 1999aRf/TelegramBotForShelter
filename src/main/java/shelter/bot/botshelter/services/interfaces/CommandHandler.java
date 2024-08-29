package shelter.bot.botshelter.services.interfaces;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.PhotoSize;
import com.pengrad.telegrambot.request.GetFile;
import com.pengrad.telegrambot.response.GetFileResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

public interface CommandHandler {

    // паттерн для валидации номера формата +7-9**-***-**-

    Pattern validNumber = Pattern.compile("^\\+7-\\d{3}-\\d{3}-\\d{2}-\\d{2}$");
    //--------------------------------------------------------------------------
    //    Маркеры выбора интересующего набора приютов с кошками или собаками
    //--------------------------------------------------------------------------
    public static final byte CHOSEN_DOGS = 1; // добавлен выбор питомцев
    public static final byte CHOSEN_CATS = 2;

    //--------------------------------------------------------------------------
    //    Дополнительные кнопки в меню для возврата к предыдущему меню
    //--------------------------------------------------------------------------
    public static final String CHOSE_MENU = "Выбрать приют"; // добавлены кнопки навигации по меню
    public static final String MAIN_MENU = "Главное меню";
    public static final String NEW_USER_MENU = "О приюте";
    public static final String CONSULTATION_MENU = "Консультация";
    public static final String RECOMMENDATION_MENU = "Рекомендации";
    public static final String DOG_HANDLER_MENU = "Советы кинолога";

    //--------------------------------------------------------------------------
    //                          Базовые команды
    //--------------------------------------------------------------------------
    public static final String START_COMMAND = "/start";
    public static final String CALL_VOLUNTEER = "Позвать волонтера";

    //--------------------------------------------------------------------------
    //                      Команды соответствующего меню
    //--------------------------------------------------------------------------
    public static final String CHOOSE_SHELTER_COMMAND1 = "Приют для собак";
    public static final String CHOOSE_SHELTER_COMMAND2 = "Приют для кошек";

    public static final String MAIN_COMMAND1 = "Информация о приюте";
    public static final String MAIN_COMMAND2 = "Как взять животное из приюта";
    public static final String MAIN_COMMAND3 = "Прислать отчет о питомце";
    public static final String MAIN_COMMAND4 = "Консультация";

    public static final String NEW_USER_COMMAND1 = "Расписание и адрес приюта";
    public static final String NEW_USER_COMMAND2 = "Оформление пропуска и схема проезда";
    public static final String NEW_USER_COMMAND3 = "Техника безопасности";
    public static final String NEW_USER_COMMAND4 = "Принять данные для связи";

    public static final String CONSULTATION_COMMAND1 = "Список животных";
    public static final String CONSULTATION_COMMAND2 = "Правила знакомства и усыновления";
    public static final String CONSULTATION_COMMAND3 = "Список необходимых документов";
    public static final String CONSULTATION_COMMAND4 = "Рекомендации";

    public static final String DOG_HANDLER_COMMAND1 = "Советы кинолога";
    public static final String DOG_HANDLER_COMMAND2 = "Проверенные кинологи";
    public static final String DOG_HANDLER_COMMAND3 = "Причины отказа";

    public static final String ADOPTION_COMMAND1 = "Транспортировка животного";
    public static final String ADOPTION_COMMAND2 = "Обустройство дома для щенка";
    public static final String ADOPTION_COMMAND3 = "Обустройство дома для взрослого питомца";
    public static final String ADOPTION_COMMAND4 = "Обустройство дома для питомца с ограниченными возможностями";

    public static final String REPORT_COMMAND1 = "Отчет о питании питомца";
    public static final String REPORT_COMMAND2 = "Отчет о самочувствии питомца";
    public static final String REPORT_COMMAND3 = "Отчет о поведении питомца";
    public static final String REPORT_COMMAND4 = "Фото-отчет";

    //--------------------------------------------------------------------------
    //              Наборы кнопок для соответствующего в названии меню
    //--------------------------------------------------------------------------
    public static final String[][] CHOOSE_SHELTER_COMMANDS = new String [][] {
            new String[]{CHOOSE_SHELTER_COMMAND1},
            new String[]{CHOOSE_SHELTER_COMMAND2}
    };

    public static final String[][] MAIN_MENU_COMMANDS = new String[][]{
            new String [] {MAIN_COMMAND1},
            new String [] {MAIN_COMMAND2},
            new String [] {MAIN_COMMAND3},
//            new String [] {CHOSE_MENU},
            new String [] {CALL_VOLUNTEER}
    };
    public static final String[][] SUBMENU_NEW_USER = new String[][]{
            new String [] {NEW_USER_COMMAND1},
            new String [] {NEW_USER_COMMAND2},
            new String [] {NEW_USER_COMMAND3},
            new String [] {NEW_USER_COMMAND4},
            new String [] {MAIN_MENU},
            new String [] {CALL_VOLUNTEER}
    };
    public static final String[][] SUBMENU_CONSULTATION = new String[][]{
            new String [] {CONSULTATION_COMMAND1},
            new String [] {CONSULTATION_COMMAND2},
            new String [] {CONSULTATION_COMMAND3},
            new String [] {CONSULTATION_COMMAND4},
            new String [] {NEW_USER_MENU},
    };
    public static final String[][] SUBMENU_DOG_HANDLER = new String[][]{
            new String [] {DOG_HANDLER_COMMAND1},
            new String [] {DOG_HANDLER_COMMAND2},
            new String [] {DOG_HANDLER_COMMAND3},
            new String [] {CONSULTATION_MENU},
            new String [] {CALL_VOLUNTEER}
    };

    public static final String[][] SUBMENU_ADOPTION = new String[][]{
            new String [] {ADOPTION_COMMAND1},
            new String [] {ADOPTION_COMMAND2},
            new String [] {ADOPTION_COMMAND3},
            new String [] {ADOPTION_COMMAND4},
            new String [] {DOG_HANDLER_MENU},
            new String [] {MAIN_MENU}
    };
    public static final String[][] SUBMENU_REPORT = new String[][]{
            new String [] {REPORT_COMMAND1},
            new String [] {REPORT_COMMAND2},
            new String [] {REPORT_COMMAND3},
            new String [] {REPORT_COMMAND4},
            new String [] {MAIN_MENU}
    };

    //--------------------------------------------------------------------------
    //      Методы для обработки команд соответствующего в названии меню
    //--------------------------------------------------------------------------

    /**
     * Обработчик команд главного меню. Переносит в другие меню, где пользователь
     * получить информацию о: <br>
     * - информации о приюте(в зависимости от того, это приют для собак или кошек)
     * {@code MAIN_COMMAND1},<br>
     * - том, как взять животное из приюта{@code MAIN_COMMAND2},<br>
     * - как отправить отчет о питомце(для усыновивших животное){@code MAIN_COMMAND3}<br>
     * - возврат к меню выбора интересующего животного {@code CHOSE_MENU}<br>
     * - позвать волонтера {@code CALL_VOLUNTEER}.
     *
     * @param chatId - идентификатор чата(при взаимодействии с пользователем он
     *               сохраняется вместе с его выбором интересующего приюта для выдачи
     *               актуальной для него информации в следующих меню)
     * @param command - текущая команда, которую отправил пользователь
     */
    void handleMainCommands(Long chatId,String command);


    /**
     * Обработчик команд меню, в котором пользователь может узнать различную информацию о
     * приюте:<br>
     * - расписание и адрес приюта {@code NEW_USER_COMMAND1},<br>
     * - оформление разрешения на проезд на территорию приюта и схема проезда(картинка)
     * {@code NEW_USER_COMMAND2},<br>
     * - общие сведения о технике безопасности{@code NEW_USER_COMMAND3},<br>
     * - принять данные для связи {@code NEW_USER_COMMAND4},<br>
     * - возврат в главное меню {@code MAIN_MENU}
     * @param chatId - идентификатор чата(при взаимодействии с пользователем он
     *               сохраняется вместе с его выбором интересующего приюта для выдачи
     *               актуальной для него информации в следующих меню)
     * @param command - текущая команда, которую отправил пользователь
     */
    void handleNewUserCommands(Long chatId,String command);

    /**
     * Обработчик команд меню, в котором пользователь получает информацию для будущего усыновителя
     * животного :<br>
     * - транспортировка животного{@code CONSULTATION_COMMAND1},<br>
     * - правила знакомства и усыновления {@code CONSULTATION_COMMAND2},<br>
     * - список необходимых документов {@code CONSULTATION_COMMAND3},<br>
     * - рекомендации(для тех, кто хочет усыновить собаку) {@code CONSULTATION_COMMAND4},<br>
     * - возврат в меню для нового пользователя {@code NEW_USER_MENU}
     *
     * @param chatId - идентификатор чата(при взаимодействии с пользователем он
     *               сохраняется вместе с его выбором интересующего приюта для выдачи
     *               актуальной для него информации в следующих меню)
     * @param command - текущая команда, которую отправил пользователь
     */
    void handleConsultationCommands(Long chatId,String command);

    /**
     * Обработчик команд меню, в котором пользователь получает рекомендации уже как усыновившему :<br>
     * - советы кинолога{@code DOG_HANDLER_COMMAND1},<br>
     * - проверенные кинологи {@code DOG_HANDLER_COMMAND2},<br>
     * - причины отказа {@code DOG_HANDLER_COMMAND3},<br>
     * - рекомендации(для тех, кто хочет усыновить собаку) {@code CONSULTATION_COMMAND4},<br>
     * - возврат в меню консультации {@code CONSULTATION_MENU},<br>
     * - вызов волонтера{@code CALL_VOLUNTEER}
     *
     * @param chatId - идентификатор чата(при взаимодействии с пользователем он
     *               сохраняется вместе с его выбором интересующего приюта для выдачи
     *               актуальной для него информации в следующих меню)
     * @param command - текущая команда, которую отправил пользователь
     */
    void handleDogHandlerCommands(Long chatId,String command);

    /**
     * Обработчик команд меню, в котором усыновивший животное пользователь получает рекомендации:<br>
     * - транспортировка животного{@code ADOPTION_COMMAND1},<br>
     * - обустройство дома{@code ADOPTION_COMMAND2},<br>
     * - обустройство дома для взрослого питомца{@code ADOPTION_COMMAND3},<br>
     * - обустройство дома для питомца с ограниченными возможностями{@code ADOPTION_COMMAND4},<br>
     * - возврат в главное меню{@code MAIN_MENU}.
     *
     * @param chatId - идентификатор чата(при взаимодействии с пользователем он
     *               сохраняется вместе с его выбором интересующего приюта для выдачи
     *               актуальной для него информации в следующих меню)
     * @param command - текущая команда, которую отправил пользователь
     */
    void handleAdoptionCommands(Long chatId,String command);
    /**
     * Обработчик команд меню отчета, в котором пользователь, усыновивший питомца обязан отправлять достоверную
     * информацию о: <br>
     * - отчет о питании питомца{@code REPORT_COMMAND1},<br>
     * - отчет о самочувствии питомца {@code REPORT_COMMAND2},<br>
     * - отчет о поведении питомца{@code REPORT_COMMAND3},<br>
     * - фото-отчет{@code REPORT_COMMAND4},<br>
     * - возврат в главное меню{@code MAIN_MENU}.
     *
     *
     * @param message - все содержимое сообщения
     */
    void handleReportCommands(Message message);

    /**
     * Метод проверяет, есть ли команда в соответствуюем меню.
     * @param buttonsArray - набор кнопок меню
     * @param command - команда, к которой ищет соответствие в разных наборах кнопок.
     * @return - {@code true}- если есть, {@code false} - если нет такой команды
     */
    default boolean checkContains(String[][] buttonsArray,String command){
        if (command.equals(CALL_VOLUNTEER)) {
            return false;
        }
        for (int i = 0; i < buttonsArray.length; i++) {
            if (buttonsArray[i][0].equals(command)) {
                return true;
            }
        }
        return false;
    }

    //--------------------------------------------------------------------------
    //              Методы для обработки сообщений пользователя
    //--------------------------------------------------------------------------

    /**
     * Метод для обработки команды {@code NEW_USER_COMMAND4} - принять данные для связи.
     * @param message - все содержимое сообщения
     */
    void handleUserText(Message message);
    /**
     * Метод валидации номера согласно маске {@code validNumber}
     * @param command - введенный текст пользователя
     * @return - {@code true}, если текст пользователя соответствует маске
     */
    default boolean validateNumber(String command) {
        return validNumber.matcher(command).matches();
    }

    default byte[] getPhotoFromMsg(Optional<PhotoSize> hasPhoto,TelegramBot bot) throws IOException {

        if (hasPhoto.isPresent()) {
            GetFile getFile = new GetFile(hasPhoto.get().fileId());
            File file = bot.execute(getFile).file();
            return bot.getFileContent(file);
        } else {
            return null;
        }

    }



}