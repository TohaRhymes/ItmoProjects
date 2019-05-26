import com.google.gson.Gson;

public class ForJson {
    public static TimeTraveller readJson(String string) {
        TimeTraveller traveller = null;
        Gson gson = new Gson();
            traveller = gson.fromJson(string, TimeTraveller.class);
        return traveller;
    }
}
