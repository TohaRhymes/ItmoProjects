import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import localization.LocalizationManager;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainClient {

    public static LocalizationManager localizationManager;

    @Getter
    private static Lock lock = new ReentrantLock();

    @Getter
    private static final Gson gson = new GsonBuilder().setLenient().create();

    public static ArrayList<String> datamass = new ArrayList<>();

    private static int port;

    @Getter
    @Setter
    private static String cookie;

    public static ServerConnect serverConnect;

    @Getter
    private static MainFrame frame;
    @Getter
    private static LoginOrRegisterWindow loginOrRegisterWindow;

    public static void main(String[] args) throws IOException {


        port = Integer.valueOf(args[0]); // TODO: Где-то должна быть проверка, вдруг порт не указали, и присваивание значения костыльное
        serverConnect = new ServerConnect(port);
        cookie = "unlogin|cat";

        SwingUtilities.invokeLater(MainClient::gui); // Запускаем поток диспетчеризации событий
    }

    private static void gui() {
        try {

            //Создаем начальную локализацию
            localizationManager = new LocalizationManager(LocalizationManager.COUNTRY_RU);

            // Слушатель сообщений от сервера
            ClientReceiver clientReceiver = new ClientReceiver(serverConnect.getDatagramChannel());
            clientReceiver.execute();

            //авторизация пользователя (теперь осуществляется в отдельном окне)
            loginOrRegisterWindow = new LoginOrRegisterWindow(true);

            // "Рукопожатие" с сервером, получение данных для первой отрисовки интерфейса
            serverConnect.sendMSG(new ClientMSG("hello"));

            frame = new MainFrame();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Не удалось установить соединение");
            System.exit(0);
        }
    }

}