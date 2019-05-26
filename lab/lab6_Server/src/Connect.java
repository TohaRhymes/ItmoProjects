import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import java.sql.*;

public class Connect {

    private String url;
    private String name;
    private String pass;
    private Connection connection;
    private Statement statement;
    //private ResultSet resultSet;

    public Connect(String url, String name, String pass) throws SQLException, JSchException {
        this.url = url;
        this.name = name;
        this.pass = pass;
        heliosConnect();
        connection = DriverManager.getConnection(url, name,pass);
        statement = connection.createStatement();
        System.out.println("Соединение установлено");
    }

    void heliosConnect() throws JSchException {
        JSch jSch = new JSch();
        Session session = jSch.getSession(name, "se.ifmo.ru", 2222);
        session.setPassword(pass);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();
        session.setPortForwardingL(2222, "pg", 5432);
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement getStatement() {
        return statement;
    }
}
