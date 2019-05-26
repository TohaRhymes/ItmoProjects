import com.google.gson.Gson;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.ConcurrentSkipListSet;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class AvailableCommands {

    // Массив, в котором содержиться информация о местоположении клиентских приложений (port, add)
    public static ArrayList<User> clientBase = new ArrayList<>();

    public static void hello(int port, InetAddress add, DatagramSocket socket, Connect connect, ConcurrentSkipListSet<TimeTraveller> travellers) throws IOException {
        // "Координаты" каждого нового клиента запоминаются в clientBase, чтобы потом можно было рассылать им изменения в БД и изменять содержимое таблиц и областей отображения
        clientBase.add(new User(add, port));

        StringBuilder data = new StringBuilder();
        Gson gson = MainServer.getGson();
        travellers.forEach(traveller -> {
            traveller.setOwner(traveller.getOwner().trim());
            data.append(gson.toJson(traveller)).append("&");
        });
        String mes = data.toString();
        //System.out.println(mes);
        DatagramPacket packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
        socket.send(packet);
    }

    public static void remove(ArrayList<String> args, ConcurrentSkipListSet<TimeTraveller> travellers, Connect connect) throws SQLException {

        TimeTraveller removeTraveller = MainServer.getGson().fromJson(args.get(0), TimeTraveller.class);
        for (TimeTraveller treveller : travellers) {
            if (treveller.equals(removeTraveller)) {
                travellers.remove(treveller);

                DBCommands.removeTraveller(connect, removeTraveller);

                try {
                    ServerMSG.sendServerMSGtoAll(new ServerMSG("remove", args));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }
        }
    }

    public static void login(String login, ArrayList<String> args, int port, InetAddress add, DatagramSocket socket, Connect connect) throws IOException, SQLException {
        String mes = "";
        login = args.get(0).trim();
        String pass = md5Hex(args.get(1).trim());
        if (DBCommands.checkLogin(connect, login)) {
            if (pass.trim().equals(DBCommands.getPass(connect, login).trim())) {
                String cookie = "" + Math.round(Math.random() * 10000);
                mes = "Авторизация прошла успешно. Для просмотра доступных команд введите help#" + login + "|" + cookie;
                DBCommands.setCookie(connect, login, cookie);
            } else mes = "Некорректный пароль";
        } else mes = "Некорректный логин";

        ServerMSG serverMSG = new ServerMSG("login", add, port, socket);
        serverMSG.getSargs().add(mes);
        ServerMSG.sendServerMSG(serverMSG);
    }

    public static void register(ArrayList<String> args, int port, InetAddress add, DatagramSocket socket, Connect connect) throws IOException, SQLException, MessagingException {
        String mes = "";
        String login = args.get(0);
        String email = args.get(1);
        if (!(DBCommands.checkLogin(connect, login))) {
            String pass = "" + Math.round(Math.random() * 100000000);
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.mailtrap.io" /*"smtp.yandex.ru"*/);
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", 25 /*"465"*/);
            //props.put("mail.smtp.socketFactory.port", 1233 /*"465"*/);
            //props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.trust", "smtp.mailtrap.io");

            //Authenticator auth = new EmailAuthenticator("jenni-2-0-0-0@ya.ru", "jenni2000");
            //Session session = Session.getInstance(props, auth);
            //session.setDebug(false);
            javax.mail.Authenticator auth = new javax.mail.Authenticator() {
                @Override
                protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                    return new javax.mail.PasswordAuthentication("2a3679e7579d35", "3bebea60f5d950");
                }
            };
            Session session = Session.getInstance(props, auth);
            InternetAddress email_from = new InternetAddress("jenni-2-0-0-0@yandex.ru");
            InternetAddress email_to = new InternetAddress(email);
            InternetAddress reply_to = null;

            Message message = new MimeMessage(session);
            message.setFrom(email_from);
            message.setRecipient(Message.RecipientType.TO, email_to);
            message.setSubject("Your password");
            message.setText("Ваш пароль: " + pass + "\n" + "--------------------------------------------------\n" + DBCommands.getBonus(connect) + "\n(c) Алексей Евгеньевич");

            Transport.send(message);

            DBCommands.addUser(connect, login, email, pass);
            mes = "myau#Регистрация прошла успешно, пароль отправлен на ваш email";
        } else mes = "Такой логин уже существует";

        ArrayList<String> sargs = new ArrayList<>();
        sargs.add(mes);

        ServerMSG serverMSG = new ServerMSG("register", add, port, socket);
        serverMSG.setSargs(sargs);
        ServerMSG.sendServerMSG(serverMSG);
    }


        public static void create(ConcurrentSkipListSet<TimeTraveller> travellers, ArrayList<String> arguments, Connect connect) throws SQLException {

        TimeTraveller newTtaveller = MainServer.getGson().fromJson(arguments.get(0), TimeTraveller.class);

        DBCommands.addTraveller(connect, newTtaveller);

        travellers.add(newTtaveller);

            try {
                ServerMSG.sendServerMSGtoAll(new ServerMSG("add", arguments));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    // TODO: Как я понимаю, его больше не будет
    //    /**
    //     * Method shows all elements in String representation.
    //     */
    //    public static void showAllPersons(ConcurrentSkipListSet<TimeTraveller> travellers, int port, InetAddress add, DatagramSocket socket) throws IOException {
    //        StringBuilder showstringbuilder = new StringBuilder();
    //        String mes = new String();
    //        //Это Stream API
    //        travellers.stream().forEach(traveller -> showstringbuilder.append(traveller));
    //        if (travellers.size() != 0) mes = showstringbuilder.toString();
    //        else mes = "Коллекция не содержит элементов";
    //        DatagramPacket packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
    //        socket.send(packet);
    //    }
    // TODO: Как я понимаю, его больше не будет
    //    /**
    //     * Method shows current element in String representation.
    //     */
    //    public static void showPerson(ConcurrentSkipListSet<TimeTraveller> travellers, String arguments, int port, InetAddress add, DatagramSocket socket) throws IOException {
    //
    //        //Это Stream API
    //        String mes = travellers.stream()
    //                .filter(traveller -> traveller.getName().equals(arguments))
    //                .map(TimeTraveller::toString)
    //                .reduce((s, s2) -> s += s2).get();
    //
    //
    //        DatagramPacket packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
    //        socket.send(packet);
    //    }

    //    /**
    //     * Method removes all collection's elements.
    //     */
    //    public static void clear(ConcurrentSkipListSet<TimeTraveller> travellers, int port, InetAddress add, DatagramSocket socket) throws IOException {
    //        travellers.clear();
    //        String mes = new String("The Collection has been cleaned.");
    //        DatagramPacket packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
    //        socket.send(packet);
    //    }

    // TODO:  Реализация
    //    /**
    //     * Method removes first collection's element, which are the same with argument travellerForDelete.
    //     */
    //    public static void remove(ConcurrentSkipListSet<TimeTraveller> travellers, String arguments, int port, InetAddress add, DatagramSocket socket, String login) throws IOException {
    //
    //        String mes = new String("Среди доступных вам объектов нет объекта с таким именем");
    //        if (travellers.removeIf(traveller -> ((traveller.getName().equals(arguments)) && (traveller.getOwner().trim().equals(login.trim())))))
    //            mes = "Объект успешно удален";
    //        DatagramPacket packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
    //        socket.send(packet);
    //    }
    // TODO: ?
    //    /**
    //     * Method shows information about collection (type, date of initialization, number of elements).
    //     */
    //    public static void info(ConcurrentSkipListSet<TimeTraveller> travellers, InetAddress add, int port, DatagramSocket socket) throws IOException {
    //        StringBuilder mes = new StringBuilder();
    //        mes.append("The Collection has type ConcurrentSkipListSet and contains TimeTraveller objects.");
    //        mes.append(System.getProperty("line.separator"));
    //        if (travellers.size() > 1) mes.append("It contains " + travellers.size() + " elements now.");
    //        else if (travellers.size() == 1) mes.append("It contains " + travellers.size() + " element now.");
    //        else mes.append("It does not contain elements now.");
    //        String mesStr = new String(mes);
    //        DatagramPacket packet = new DatagramPacket(mesStr.getBytes(), mesStr.getBytes().length, add, port);
    //        socket.send(packet);
    //    }
    // TODO: ?
    public static void help(InetAddress add, int port, DatagramSocket socket) throws IOException {
        StringBuilder mes = new StringBuilder();
        mes.append("Доступные методы:\ncreate - добавить новый объект в коллекцию\nshowAllPersons - вывыести содержимое коллекции\nshowPerson [имя объекта] - вывести информацию об объекте по имени\n" + "remove [имя объекта] - удалить объект из коллекции по имени\n" + "setTime - отправить путешественника в другое время\n" + "setPlace - отправить путешественника в другое место\ninfo - вывести информацию о коллекции\nhelp - мяу, ну вы серьезно?");

        String mesStr = new String(mes);
        DatagramPacket packet = new DatagramPacket(mesStr.getBytes(), mesStr.getBytes().length, add, port);
        socket.send(packet);
    }

    public static void update(ArrayList<String> args, ConcurrentSkipListSet<TimeTraveller> travellers, Connect connect) throws SQLException {

        TimeTraveller nw = MainServer.getGson().fromJson(args.get(0), TimeTraveller.class);

        for (TimeTraveller treveller : travellers) {
            if (treveller.equals(nw)) {
                travellers.remove(treveller);
                travellers.add(nw);

                DBCommands.updateTraveller(connect, nw);

                try {
                    ServerMSG.sendServerMSGtoAll(new ServerMSG("update", args));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            }
        }

    }
    //      clear - очистить коллекцию  load [файл] - записать в коллекцию содержимое файла по имени или пути к файлу(файл лежит на сервере))\ save [файл] - сохранить текущее состоянии коллекции в файл(файл лежит на сервере)
    //import [файл] - загрузить данные из пользовательского файла в коллекцию
    //    public static void loadFile(ConcurrentSkipListSet<TimeTraveller> travellers, String arguments, int port, InetAddress add, DatagramSocket socket) throws IOException {
    //        File file = new File(arguments);
    //        String mes = SaveCollection.readFile(file, travellers);
    //        DatagramPacket packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
    //        socket.send(packet);
    //    }
    //
    //    public static void saveFile(ConcurrentSkipListSet<TimeTraveller> travellers, String arguments, int port, InetAddress add, DatagramSocket socket) throws IOException {
    //        File file = new File(arguments);
    //        String mes = SaveCollection.writeFile(file, travellers);
    //        DatagramPacket packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
    //        socket.send(packet);
    //    }
    //
    //    public static void importString(ConcurrentSkipListSet<TimeTraveller> travellers, String arguments, int port, InetAddress add, DatagramSocket socket) throws IOException {
    //        String mes = SaveCollection.readString(arguments, travellers);
    //        DatagramPacket packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
    //        socket.send(packet);
    //    }
    // TODO: реализация
    //    public static void setTime(ConcurrentSkipListSet<TimeTraveller> travellers, String arguments, int port, InetAddress add, DatagramSocket socket, Connect connect, String login) throws IOException, ParseException, SQLException {
    //        String[] mass = arguments.split(" ");
    //        String traveller = mass[0].trim();
    ////        System.out.println(mass[1].trim());
    //        String mes = "";
    //        boolean checkTraveller=false;
    //        try {
    //            OurDate newDate = new OurDate(mass[1].trim());
    //            for (TimeTraveller tr : travellers) {
    //                System.out.println(tr.getName() + " " + traveller + " " + tr.getName().equals(traveller));
    //                if(!checkTraveller) checkTraveller=tr.getName().equals(traveller);
    //                if (tr.getName().equals(traveller)) {
    //                    tr.setLocDate(newDate);
    //                    tr.setAge(tr.getLocDate().getAge(tr.getBirthdayDate()));
    //                    System.out.println(tr.getLocDate().DatetoString() + " " + tr.getBirthdayDate().DatetoString() + " " + tr.getLocDate().getAge(tr.getBirthdayDate()));
    //                    mes = "Путешественник " + tr.getName() + " отправился в " + tr.getLocDate().DatetoString() + " , где его возраст составил " + tr.getAge();
    //                }
    //                System.out.println(mes);
    //            }
    //        }catch(IllegalArgumentException e){
    //            System.out.println(e.getMessage());
    //            mes+=e.getMessage();
    //        }finally {
    //            if(!checkTraveller) mes="Введено некорректное имя персонажа.";
    //            DatagramPacket packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
    //            socket.send(packet);
    //        }
    //
    //    }
    //
    //    public static void setPlace(ConcurrentSkipListSet<TimeTraveller> travellers, String arguments, int port, InetAddress add, DatagramSocket socket, Connect connect, String login) throws IOException, SQLException {
    //        String mes = new String("Среди доступных вам объектов нет объекта с таким именем");
    //        String[] mass = arguments.split(" ");
    //        String traveller = mass[0].trim();
    //        double x = new Double(mass[1]);
    //        double y = new Double(mass[2]);
    //        for (TimeTraveller tr : travellers) {
    //            if (tr.getName().equals(traveller)&&(tr.getOwner().trim().equals(login.trim()))) {
    //                tr.setX(x);
    //                tr.setY(y);
    //                DBCommands.updateTraveller(connect, tr);
    //                mes = "Путешественник " + tr.getName() + " отправился в место с координатами {" + tr.getX() + ", " + tr.getY() + "}";
    //            }
    //        }
    //        DatagramPacket packet = new DatagramPacket(mes.getBytes(), mes.getBytes().length, add, port);
    //        socket.send(packet);
    //    }
}
