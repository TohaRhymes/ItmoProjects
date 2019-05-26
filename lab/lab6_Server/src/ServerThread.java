import javax.mail.MessagingException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.concurrent.ConcurrentSkipListSet;

public class ServerThread extends Thread {

    DatagramPacket packet;
    ConcurrentSkipListSet<TimeTraveller> travellers;
    byte[] buf;
    boolean flag = true;
    DatagramSocket socket;
    Connect connect;


    ServerThread(DatagramPacket packet, ConcurrentSkipListSet<TimeTraveller> travellers, byte[] buf, DatagramSocket socket, Connect connect) {
        // Создаём новый поток
        super("Очередной поток");
        this.packet = packet;
        this.travellers = travellers;
        this.buf = buf;
        this.socket = socket;
        this.connect = connect;
    }

    @Override
    public void run() {
        super.run();

        // Получаем информацию о клиенте
        InetAddress add = packet.getAddress();
        int port = packet.getPort();

        try {
        // Распознаем команду пользователя
            CommandParse.commandParse(travellers, buf, port , packet, add, socket, flag, connect);
        } catch (IOException e) {
            System.out.println("Это метод commandParse, ему плохо... ");
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
