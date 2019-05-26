import com.google.gson.Gson;

import javax.mail.MessagingException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListSet;

public class CommandParse {

    static Gson gson = MainServer.getGson(); //

    static void commandParse(ConcurrentSkipListSet<TimeTraveller> travellers, byte[] buf, int port, DatagramPacket packet, InetAddress add, DatagramSocket socket, Boolean flag, Connect connect) throws IOException, ParseException, SQLException, MessagingException {

        ClientMSG msg = gson.fromJson(new String(buf).trim(), ClientMSG.class); // Принимаем объект клиентского сообщения

        String cookie = msg.getCookie(); // Отделяем cookie oт пакета
        String command = msg.getCommand(); // команда, введенная пользователем
        ArrayList<String> args = msg.getArgs();

//        String arguments = new String();
        String login = cookie.split("\\|")[0];

        if(!(command.equals("login")||command.equals("hello")||command.equals("register")||DBCommands.checkCookie(connect, cookie))) {

            ServerMSG serverMSG = new ServerMSG("print", add, port, socket);
            String mes = "Необходимо зарегистрироваться/авторизироваться";
            serverMSG.getSargs().add(mes);
            ServerMSG.sendServerMSG(serverMSG);

        }
        // TODO: на все ошибки внутри исполнения команд кидать пользователю "Недастаточно аргументов"
        // Заспознаем введенную пользователем команду
//        if (command.equals("showPerson") || command.equals("remove") || command.equals("create") ) {
//            if (args.size() == 1) {
//                String mes = new String("Недостаточно данных, введите команду и имя объекта через пробел");
//                packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
//                socket.send(packet);
//            }
//        }
//        if (command.equals("load")||command.equals("save")||command.equals("import")) {
//            if (stringForCommand.length == 1) {
//                String mes = new String("Недостаточно данных, введите команду и имя файла через пробел");
//                packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
//                socket.send(packet);
//            }
//            arguments = stringForCommand[1].trim();
//        }
//        if (command.equals("setTime")) {
//            if (stringForCommand.length == 1) {
//                String mes = new String("Некорректный ввод. Дата должна быть представлена в формате yyyy-MM-dd");
//                packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
//                socket.send(packet);
//            }
//            arguments = args.get(0);
//        }
//        if (command.equals("register")) {
//            if (args.size() != 2) {
//                String mes = new String("Данные некорректны");
//                packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
//                socket.send(packet);
//            }
//            arguments = args.get(0);
//            login = arguments.split(" ")[0];
//            email = arguments.split(" ")[1];
//        }
//        if (command.equals("login")) {
//            if (args.size() != 2) {
//                String mes = new String("Данные некорректны");
//                packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
//                socket.send(packet);
//            }
//            arguments = stringForCommand[1];
//            login = arguments.split(" ")[0];
//            pass = arguments.split(" ")[1];
//        }
//        if (command.equals("setPlace")) {
//            if (stringForCommand.length == 1) {
//                String mes = new String("Некорректный ввод.");
//                packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
//                socket.send(packet);
//            }
//            arguments = stringForCommand[1].trim();
//        }
        switch (command) {
            case "hello":
                // При запуске клиентского приложения осуществляется "рукопожатие"
                AvailableCommands.hello(port, add, socket, connect, travellers);
                break;
            case "login":
                AvailableCommands.login(login, args, port, add, socket, connect);
                break;
            case "register":
                AvailableCommands.register(args, port, add, socket, connect);
                break;
            case "update":
                AvailableCommands.update(args, travellers, connect);
                break;
            case "remove":
                AvailableCommands.remove(args, travellers, connect);
                break;
            // TODO: Реализация
            case "add":
                AvailableCommands.create(travellers, args, connect);
                break;
            // TODO: Как я понимаю, такой команды больше не будет
//            case "showAllPersons":
//                AvailableCommands.showAllPersons(travellers, port, add, socket);
//                break;
            // TODO: Как я понимаю, такой команды больше не будет
//            case "showPerson":
//                AvailableCommands.showPerson(travellers, arguments, port, add, socket);
//                break;
//            case "clear":
//                //todo: удалить можно только свои объекты (все)
//                AvailableCommands.clear(travellers, port, add, socket);
//                break;
            // TODO: Реализация
//            case "remove":
//                AvailableCommands.remove(travellers, args, port, add, socket, login);
//                break;
////            case "load":
//                AvailableCommands.loadFile(travellers, arguments, port, add, socket);
//                break;
//            case "save":
//                AvailableCommands.saveFile(travellers, arguments, port, add, socket);
//                break;
//            case "import":
//                AvailableCommands.importString(travellers, arguments, port, add, socket);
//                break;
            // TODO: Реализации
//            case "setTime":
//                AvailableCommands.setTime(travellers, args, port, add, socket, connect, login);
//                break;
//            case "setPlace":
//                AvailableCommands.setPlace(travellers, args, port, add, socket, connect, login);
//                break;
//            case "info":
//                AvailableCommands.info(travellers, add, port, socket);
//                break;
            case "help":
                AvailableCommands.help(add, port, socket);
                break;
            case "exit":
                System.exit(-1);
                break;
            default:
                // TODO: Такого в этой лабе вообще быть не может
                ServerMSG serverMSG = new ServerMSG("print", add, port, socket);
                String mes = "Некорректный ввод";
                serverMSG.getSargs().add(mes);
                ServerMSG.sendServerMSG(serverMSG);
        }
    }
}
