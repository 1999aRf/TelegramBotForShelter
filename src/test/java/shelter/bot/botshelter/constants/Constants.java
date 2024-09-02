package shelter.bot.botshelter.constants;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import shelter.bot.botshelter.model.Volunteer;

@Component
public class Constants {
    // Параметры для тестовых объектов класса Volunteer
    public static Long chatId1 = 381673988L;
    public static String name1 = "Чарли Чаплин";

    public static String contact1 = "+71111111111";
    // Тестовые объекты класса Volunteer
    public static Volunteer VOLUNTEER1 = new Volunteer(1L,chatId1, name1, contact1);

    // Статические методы, возвращающие тестовые JSON-объекты класса Volunteer
    public static JSONObject getVolunteerJson1() throws JSONException {
        return new JSONObject()
                .put("id", 1L)
                .put("chatId", chatId1)
                .put("name", name1)
                .put("contact", contact1);
    }

}
