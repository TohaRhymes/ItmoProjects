import java.io.*;
import java.net.PortUnreachableException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class Run {
    private static String readFileAsString(String path) {//чтение из файла
        File file = new File(path);
        if (!file.exists() || !file.isFile()) { /* Проверяем, существует ли файл или директория по такому пути,
        провяряем, действительно ли это файл */
            if (file.isDirectory()) {
                System.out.println("Введено имя не файла, а директории.");
            } else {
                System.out.println("Проверьте правильность пути к файлу");
            }
        }
        StringBuilder builder = new StringBuilder();
        try (InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file))) {
            int nowchar; // считываемый символ
            while ((nowchar = inputStreamReader.read()) != -1) { // "-1" - код символа конца файла
                builder.append((char) nowchar); // без приведения к типу char вернет числовой код символа
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла!");
        }
        //заполнение коллекции из файла
        String fileString = builder.toString().trim();
        if (fileString.isEmpty()) {
            System.out.println("Указанный файл пуст, вы можете продолжить работу с пустым файлом или ввести exit для завершения работы программы");
        }
        return fileString;
    }

    // считываем данные, вводимые пользователем, с клавиатуры
    public static void clientCommand(DatagramChannel datagramChannel) {
        String login = "frrrr";
        String pass = "";
        String email = "";
        String cookie = "B|myau";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] stringForCommand;
            String command = ""; // строка, вводимая пользователем
            Boolean flag = true; // признак оканчания цикла считывания комманд
            //Boolean isLogin = false;// проверяем, авторизирован ли пользователь

            String sendBuf = "";
            while (true) {
                try {
                    String str = reader.readLine();
                    if (Objects.isNull(str)) {
                        if (!reader.ready()) {
                            System.out.println("НЕ лапать эти кнопки. Запускай сначала");
                            return;
                        } else {
                            continue;
                        }
                    }
                    stringForCommand = str.trim().split("\\s+", 2 /* максимальное число элементов
                    массива 2 (команда, элемент) */);
                    command = stringForCommand[0]; // команда, введенная пользователем
                    // распознаем введенную пользователем команду
                    if (command.isEmpty()) {
                        continue;
                    }

                    if (command.equals("create")) {
                        //создаем объект на стороне клиента и предеем его в сериализованном виде с помощью Gson
                        try {
                            String name = "";
                            String x = "";
                            String y = "";
                            String jsonStr = "";

                            System.out.println("Введите имя объекта:");
                            name = reader.readLine().trim();
                            System.out.println("Введите x координату объекта:");
                            x = reader.readLine().trim();
                            System.out.println("Введите y координату объекта:");
                            y = reader.readLine().trim();

                            TimeTraveller traveller = new TimeTraveller(name, Double.parseDouble(x), Double.parseDouble(y), login);
                            Gson gson = new Gson();
                            jsonStr = gson.toJson(traveller);
                            sendBuf = stringForCommand[0] + " " + jsonStr;
                        } catch (Exception e) {
                            System.out.println("Все плохо! Некорректное заполнение полей(");
                            continue;
                        }

                    } else {
                        if ((command.equals("showPerson") || command.equals("remove") || command.equals("load") || command.equals("save") || command.equals("import")) && (stringForCommand.length > 1)) //вот здесь оно падает, потому что трбует второй элемент массива, сделать проверку и длина массива больше 1
                            sendBuf = stringForCommand[0].trim() + " " + stringForCommand[1].trim();
                        else if ((command.equals("showPerson") || command.equals("remove") || command.equals("load") || command.equals("save") || command.equals("import")) && (stringForCommand.length < 2)) {
                            System.out.println("Недостаточно аргументов");
                            continue;
                        } else if ((command.equals("showPerson") || command.equals("remove")) && (stringForCommand.length == 1))
                            System.out.println("Недостаточно данных, введите команду и аргумент (имя объекта) через пробел");
                        else if (command.equals("import") && stringForCommand.length == 2) {
                            String information = readFileAsString(stringForCommand[1].trim());
                            sendBuf = stringForCommand[0].trim() + " " + information;
                        } else if (command.equals("import") && stringForCommand.length != 2) {
                            System.out.println("Неправильный формат команды. Введите import + название файла/путь к нему, из которого надо считать коллекцию.");
                        } else if (command.equals("exit")) {
                            System.exit(-1);
                        } else if (command.equals("setTime")) {
                            String name = "";
                            OurDate toDate;

                            System.out.println("Введите имя путешественника во времени");
                            name = reader.readLine().trim();

                            System.out.println("Введите время перемещения в формате yyyy-MM-dd");
                            toDate = new OurDate(reader.readLine().trim());
                            sendBuf = stringForCommand[0] + " " + name + " " + toDate.DatetoString();
                        } else if (command.equals("setPlace")) {

                            String name = "";
                            Double x, y;

                            System.out.println("Введите имя путешественника во времени");
                            name = reader.readLine().trim();
                            System.out.println("Введите x координату перемещения");
                            x = new Double(reader.readLine().trim());
                            System.out.println("Введите y координату перемещения");
                            y = new Double(reader.readLine().trim());
                            sendBuf = stringForCommand[0] + " " + name + " " + x + " " + y;
                        } else if (command.equals("login")) {

                            login = "";
                            pass = "";

                            System.out.println("Введите логин");
                            login = reader.readLine().trim();
                            //Console console = System.console();
                            //String passString = new String(console.readPassword("Введите пароль\n"));
                            System.out.println("Введите пароль");
                            String passString = reader.readLine().trim();
                            pass = md5Hex(passString.trim());


                            sendBuf = stringForCommand[0] + " " + login + " " + pass;
                        } else if (command.equals("register")) {

                            login = "";
                            email = "";

                            System.out.println("Введите логин");
                            login = reader.readLine().trim();
                            System.out.println("Введите email");
                            email = reader.readLine().trim();

                            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
                                    "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
                            Matcher matcher = pattern.matcher(email);
                            if (!(matcher.find())) {
                                System.out.println("Некорректный email");
                                continue;
                            }
                            sendBuf = stringForCommand[0] + " " + login + " " + email;
                        } else {
                            sendBuf = stringForCommand[0];
                        }
                    }
                    //передача данных серверу
                    sendBuf += "#" + cookie;
                    datagramChannel.write(ByteBuffer.wrap(sendBuf.getBytes()));
                    for (int i = 0; i < stringForCommand.length; i++)
                        stringForCommand[i] = "";
                    ByteBuffer bb = ByteBuffer.allocate(2048);
                    while (bb.position() == 0)
                        datagramChannel.read(bb);
                    if (command.equals("login")) {
                        String is = new String(bb.array());
                        String[] isMass = is.split("#");
                        if (isMass.length == 2) {
                            System.out.println(isMass[0]);
                            cookie = isMass[1];
                        } else System.out.println(isMass[0]);
                    } else if (command.equals("register")) {
                        String is = new String(bb.array());
                        String[] isMass = is.split("#");
                        System.out.println(is);
                        System.out.println(isMass[1]);

                    } else System.out.println(new String(bb.array()));

                } catch (PortUnreachableException e) {
                    System.out.println("Сервер упал...( (Очень больно). Попробуйте повторить свой запрос позже, может быть, к этому врмени кто-нибудь его поднимет.");
                }catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }catch (IOException e) {
                    System.out.println("Ошибка пользовательского ввода");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}