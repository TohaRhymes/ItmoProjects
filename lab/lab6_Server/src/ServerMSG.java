import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Map;

public class ServerMSG {

    @Getter
    private String scommand;
    @Setter
    @Getter
    private ArrayList<String> sargs;

    private static InetAddress add;
    private static int port;
    private static DatagramSocket socket;

    public ServerMSG(String scommand, InetAddress add, int port, DatagramSocket socket) {
        this.scommand = scommand;
        this.sargs = new ArrayList<>();
        ServerMSG.add = add;
        ServerMSG.port = port;
        ServerMSG.socket = socket;
    }

    public ServerMSG(String scommand, ArrayList<String> sargs) {
        this.scommand = scommand;
        this.sargs = sargs;
    }

    public static void sendServerMSG(ServerMSG serverMSG) throws IOException {
        Gson gson = MainServer.getGson();
        DatagramPacket packet  = new DatagramPacket(
                gson.toJson(serverMSG).getBytes(),
                gson.toJson(serverMSG).getBytes().length,
                add, port);
        socket.send(packet);
    }

    public static void sendServerMSGtoAll(ServerMSG serverMSG) throws IOException {
        Gson gson = MainServer.getGson();
        for (User user:  AvailableCommands.clientBase) {
            DatagramPacket packet = new DatagramPacket(
                    gson.toJson(serverMSG).getBytes(),
                    gson.toJson(serverMSG).getBytes().length,
                    user.getAdd(), user.getPort());
            socket.send(packet);
        }
    }
}
