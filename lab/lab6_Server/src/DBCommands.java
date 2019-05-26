import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.ConcurrentSkipListSet;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class DBCommands {
    public static boolean checkLogin(Connect connect, String login) throws SQLException {
        PreparedStatement preparedStatement = null;
        // ? - место вставки нашего значеня
        preparedStatement = connect.getConnection().prepareStatement(
                "select count(login) from users where ? = login");
        //Устанавливаем в нужную позицию значения определённого типа
        preparedStatement.setString(1, login);
        //выполняем запрос
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        return res.getInt("count") == 1;
    }

    public static String getPass(Connect connect, String login) throws SQLException {
        PreparedStatement preparedStatement = null;
        // ? - место вставки нашего значеня
        preparedStatement = connect.getConnection().prepareStatement(
                "select pass from users where ? = login");
        //Устанавливаем в нужную позицию значения определённого типа
        preparedStatement.setString(1, login);
        //выполняем запрос
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        return res.getString("pass");
    }

    public static String getCookie(Connect connect, String login) throws SQLException {
        PreparedStatement preparedStatement = null;
        // ? - место вставки нашего значеня
        preparedStatement = connect.getConnection().prepareStatement(
                "select cookie from users where ? = login");
        //Устанавливаем в нужную позицию значения определённого типа
        preparedStatement.setString(1, login);
        //выполняем запрос
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        return res.getString("cookie");
    }

    public static void setCookie(Connect connect, String login, String cookie) throws SQLException {
        PreparedStatement preparedStatement = null;
        // ? - место вставки нашего значеня
        preparedStatement = connect.getConnection().prepareStatement(
                "update users set cookie = ? where login = ?");
        //Устанавливаем в нужную позицию значения определённого типа
        preparedStatement.setString(1, cookie);
        preparedStatement.setString(2, login);
        //выполняем запрос
        preparedStatement.executeUpdate();
    }

    public static boolean checkCookie(Connect connect, String cookie) throws SQLException {
        PreparedStatement preparedStatement = null;
        String login = cookie.trim().split("\\|",2)[0];
        String cook = cookie.trim().split("\\|",2)[1];
        if (!checkLogin(connect, login))
            return false;
        // ? - место вставки нашего значеня
        preparedStatement = connect.getConnection().prepareStatement(
                "select cookie from users where ? = login");
        //Устанавливаем в нужную позицию значения определённого типа
        preparedStatement.setString(1, login);
        //выполняем запрос
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        return res.getString("cookie").trim().equals(cook);
    }

    public static void addUser(Connect connect, String login, String email, String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        // ? - место вставки нашего значеня
        preparedStatement = connect.getConnection().prepareStatement(
                "INSERT INTO users (login, email, pass, cookie) VALUES (?,?,?,?)");
        //Устанавливаем в нужную позицию значения определённого типа
        preparedStatement.setString(1, login);
        preparedStatement.setString(2, email);
        preparedStatement.setString(3, md5Hex(pass));
        preparedStatement.setString(4, "kurlyk");
        //выполняем запрос
        preparedStatement.executeUpdate();
    }


    public static void addTraveller(Connect connect, TimeTraveller traveller) throws SQLException{
        PreparedStatement preparedStatement = null;
        //CallableStatement callableStatement = connect.getConnection().prepareCall("{CALL addTraveller(?,?,?,?,?,?,?)}");
        // ? - место вставки нашего значения
//        callableStatement.setString(1, traveller.getName());
//        callableStatement.setString(2, traveller.getBirthdayDate().DatetoString());
//        callableStatement.setString(3, traveller.getLocDate().DatetoString());
//        callableStatement.setDouble(4, traveller.getX());
//        callableStatement.setDouble(5, traveller.getY());
//        callableStatement.setLong(6, traveller.getAge());
//        callableStatement.setString(7, traveller.getOwner());


        preparedStatement = connect.getConnection().prepareStatement(
                "INSERT INTO travellers (name, birthdaydate, locdate, x, y, age, owner) VALUES (?,?,?,?,?,?,?)");
       // Устанавливаем в нужную позицию значения определённого типа
        preparedStatement.setString(1, traveller.getName());
        preparedStatement.setString(2, traveller.getBirthdayDate().DatetoString());
        preparedStatement.setString(3, traveller.getLocDate().DatetoString());
        preparedStatement.setDouble(4, traveller.getX());
        preparedStatement.setDouble(5, traveller.getY());
        preparedStatement.setLong(6, traveller.getAge());
        preparedStatement.setString(7, traveller.getOwner());
       // выполняем запрос
        preparedStatement.executeUpdate();


        //callableStatement.execute();
    }

    public static void getTravellers (Connect connect, ConcurrentSkipListSet<TimeTraveller> travellers) throws SQLException, ParseException {
        PreparedStatement preparedStatement = null;
        // ? - место вставки нашего значения
        preparedStatement = connect.getConnection().prepareStatement(
                "select * from travellers");
        //выполняем запрос
        ResultSet res = preparedStatement.executeQuery();

        while(res.next()){
            String name = res.getString("name").trim();
            OurDate birthdayDate = new OurDate(res.getString("birthdaydate").trim());
            OurDate locDate = new OurDate(res.getString("locdate").trim());
            double x = res.getDouble("x");
            double y = res.getDouble("y");
            long age = res.getLong("age");
            String owner = res.getString("owner");
            travellers.add(new TimeTraveller(name, birthdayDate, locDate, x, y, age, owner));
        }
    }

    public static void updateTraveller(Connect connect, TimeTraveller traveller) throws SQLException{
        PreparedStatement preparedStatement = null;
        // ? - место вставки нашего значения
        preparedStatement = connect.getConnection().prepareStatement(
                "update travellers set locdate = ?, x = ?, y = ?, age = ? where name = ?");
        //Устанавливаем в нужную позицию значения определённого типа
        preparedStatement.setString(1, traveller.getLocDate().DatetoString());
        preparedStatement.setDouble(2, traveller.getX());
        preparedStatement.setDouble(3, traveller.getY());
        preparedStatement.setLong(4, traveller.getAge());
        preparedStatement.setString(5, traveller.getName());
        //выполняем запрос
        preparedStatement.executeUpdate();
    }

    public static void removeTraveller(Connect connect, TimeTraveller traveller) throws SQLException{
        PreparedStatement preparedStatement = null;
        // ? - место вставки нашего значения
        preparedStatement = connect.getConnection().prepareStatement(
                "delete from travellers where name = ?");
        //Устанавливаем в нужную позицию значения определённого типа
        preparedStatement.setString(1, traveller.getName());
        //выполняем запрос
        preparedStatement.executeUpdate();
    }

    public static String getBonus(Connect connect) throws SQLException {
        PreparedStatement preparedStatement = null;
        preparedStatement = connect.getConnection().prepareStatement(
                "select * from quotes where id = ?");
        preparedStatement.setLong(1, 1 + Math.round(Math.random()*9));
        ResultSet res = preparedStatement.executeQuery();
        res.next();
        return res.getString("quote");
    }
}