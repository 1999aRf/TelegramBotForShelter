package shelter.bot.botshelter.services.intefaces;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface CommandHandler {

    public static final String START_COMMAND = "/start";
    public static final String CALL_VOLUNTEER = "Позвать волонтера";

    public static final String MAIN_COMMAND1 = "Информация о приюте";
    public static final String MAIN_COMMAND2 = "Как взять животное из приюта";
    public static final String MAIN_COMMAND3 = "Прислать отчет о питомце";

    public static final String NEW_USER_COMMAND1 = "Расписание и адрес приюта";
    public static final String NEW_USER_COMMAND2 = "Оформление пропуска и схема проезда";
    public static final String NEW_USER_COMMAND3 = "Техника безопасности";

    public static final String CONSULTATION_COMMAND1 = "Список животных";
    public static final String CONSULTATION_COMMAND2 = "Правила знакомства и усыновления";
    public static final String CONSULTATION_COMMAND3 = "Список необходимых документов";
    public static final String CONSULTATION_COMMAND4 = "Рекомендации";

    public static final String DOG_HANDLER_COMMAND1 = "Советы кинолога";
    public static final String DOG_HANDLER_COMMAND2 = "Проверенные кинологи";
    public static final String DOG_HANDLER_COMMAND3 = "Причины отказа";

    public static final String ADOPTION_COMMAND1 = "Транспортировка животного";
    public static final String ADOPTION_COMMAND2 = "Обустройство дома";
    public static final String ADOPTION_COMMAND3 = "Обустройство дома для взрослого питомца";
    public static final String ADOPTION_COMMAND4 = "Обустройство дома для питомца с ограниченными возможностями";

    public final List<String> mainCommandsList = new ArrayList<>(List.of(
            MAIN_COMMAND1,
            MAIN_COMMAND2,
            MAIN_COMMAND3,
            CALL_VOLUNTEER
    ));

    public static final String[][] MAIN_MENU_COMMANDS = new String[][]{
            new String [] {MAIN_COMMAND1},
            new String [] {MAIN_COMMAND2},
            new String [] {MAIN_COMMAND3},
            new String [] {CALL_VOLUNTEER}
    };
    public static final String[][] SUBMENU_NEW_USER = new String[][]{
            new String [] {NEW_USER_COMMAND1},
            new String [] {NEW_USER_COMMAND2},
            new String [] {NEW_USER_COMMAND3},
            new String [] {CALL_VOLUNTEER}
    };
    public static final String[][] SUBMENU_CONSULTATION = new String[][]{
            new String [] {CONSULTATION_COMMAND1},
            new String [] {CONSULTATION_COMMAND2},
            new String [] {CONSULTATION_COMMAND3},
            new String [] {CONSULTATION_COMMAND4}
    };
    public static final String[][] SUBMENU_DOG_HANDLER = new String[][]{
            new String [] {DOG_HANDLER_COMMAND1},
            new String [] {DOG_HANDLER_COMMAND2},
            new String [] {DOG_HANDLER_COMMAND3},
            new String [] {CALL_VOLUNTEER}
    };
    public static final String[][] SUBMENU_ADOPTION = new String[][]{
            new String [] {ADOPTION_COMMAND1},
            new String [] {ADOPTION_COMMAND2},
            new String [] {ADOPTION_COMMAND3},
            new String [] {ADOPTION_COMMAND4}
    };

    void handleMainCommands(Long chatId,String command);

    void handleNewUserCommands(Long chatId,String command);
    void handleConsultationCommands(Long chatId,String command);
    void handleDogHandlerCommands(Long chatId,String command);
    void handleAdoptionCommands(Long chatId,String command);

    default boolean checkContains(String[][] buttonsArray,String command){
        for (int i = 0; i < buttonsArray.length; i++) {
            if (buttonsArray[i][0].equals(command)) {
                return true;
            }
        }
        return false;
    }





}
