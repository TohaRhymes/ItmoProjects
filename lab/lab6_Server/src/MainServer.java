import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jcraft.jsch.JSchException;
import lombok.Getter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.concurrent.ConcurrentSkipListSet;

public class MainServer {

    @Getter
    private static final Gson gson = new GsonBuilder().setLenient().create();

       public static void main(String[] args) throws SQLException, JSchException, ParseException {
        System.out.println("Привет, я сервер и я проснулся (выспался, в отличие от тебя <3)");

        // компоратор для сортировки объетов в коллекции (сортировка по имени, затем по удаленности объектов от начала координат)
        Comparator<TimeTraveller> comparator = Comparator.comparing(TimeTraveller::getName).thenComparing(t -> (new Double(Math.sqrt(Math.pow(t.getX(), 2) + Math.pow(t.getY(), 2)))));

        ConcurrentSkipListSet<TimeTraveller> travellers = new ConcurrentSkipListSet<>(comparator);

        Connect connect = new Connect("jdbc:postgresql://localhost:2222/studs", "s265099", "nfn012");

        if (args.length < 1) {
            System.out.println("Вы забыли указать порт");
            return;
        }

            // TODO: Как сделать выход по ^C?
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//      SaveCollection.writeFile(file, travellers);
//            System.out.println("Cервер устал");
//        }));

            //на сервере лежит коллекция, чтобы поддерживать сессию, будем заполнять ее из БД
            DBCommands.getTravellers(connect, travellers);

            try {
                DatagramSocket socket = new DatagramSocket(new Integer(args[0]));
                byte[] buf = new byte[8192];
                boolean flag = true;
                while (flag) {
                    Arrays.fill(buf, (byte) 0);
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    ServerThread serverThread = new ServerThread(packet, travellers, Arrays.copyOf(buf, 1024), socket, connect);
                    serverThread.start();
                }
            } catch (SocketException e) {
                System.out.println("Косяковый порт");
            } catch (IOException e) {
                System.out.println("IOException");
            } catch (Exception e) {
                System.out.println("Ошибка сервера");
            }
            System.out.println("Сервер выполнил свою миссию и ушел отдыхать");
        }
    }