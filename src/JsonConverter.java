import com.google.gson.Gson;
import java.util.List;

public class JsonConverter {
    private static final Gson gson = new Gson();

    public static String convertObjectToJson(Object object) {
        return gson.toJson(object);
    }

    public static String convertListToJson(List<?> list) {
        return gson.toJson(list);
    }
}
