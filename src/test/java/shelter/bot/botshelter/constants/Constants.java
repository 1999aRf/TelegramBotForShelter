package shelter.bot.botshelter.constants;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import shelter.bot.botshelter.model.Volunteer;

@Component
public class Constants {
    // Параметры для тестовых объектов класса Volunteer
    public static Long chatId1 = 381673988L;
    public static Long chatId2 = 492673988L;
    public static Long chatId3 = 503673988L;

    public static String name1 = "Чарли Чаплин";
    public static String name2 = "Самюэль Кольт";
    public static String name3 = "Билл Боинг";

    public static String contact1 = "+71111111111";
    public static String contact2 = "+72222222222";
    public static String contact3 = "+73333333333";

    // Тестовые объекты класса Volunteer
    public static Volunteer VOLUNTEER1 = new Volunteer(1L,chatId1, name1, contact1);
    public static Volunteer VOLUNTEER2 = new Volunteer(2L,chatId2, name2, contact2);
    public static Volunteer VOLUNTEER3 = new Volunteer(3L,chatId3, name3, contact3);

    // Статические методы, возвращающие тестовые JSON-объекты класса Volunteer
    public static JSONObject getVolunteerJson1() throws JSONException {
        return new JSONObject()
                .put("id", 1L)
                .put("chatId", chatId1)
                .put("name", name1)
                .put("contact", contact1);
    }
    public static JSONObject getVolunteerJson2() throws JSONException {
        return new JSONObject()
                .put("id", 2L)
                .put("chatId", chatId2)
                .put("name", name2)
                .put("contact", contact2);
    }
    public static JSONObject getVolunteerJson3() throws JSONException {
        return new JSONObject()
                .put("id", 3L)
                .put("chatId", chatId3)
                .put("name", name3)
                .put("contact", contact3);
    }
}
