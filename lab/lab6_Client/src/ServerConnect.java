import com.google.gson.Gson;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ServerConnect {

    private InetSocketAddress address;
    private DatagramChannel datagramChannel;
    private Gson gson;

    public ServerConnect(int port) throws IOException {
        address = new InetSocketAddress("localhost", port);
        datagramChannel = DatagramChannel.open();
        datagramChannel.connect(address);
        gson = MainClient.getGson();
        // TODO: Что это?
        datagramChannel.configureBlocking(false);
    }

    public DatagramChannel getDatagramChannel() {
        return datagramChannel;
    }

    public void sendMSG(ClientMSG msg) throws IOException {
        datagramChannel.write(ByteBuffer.wrap(gson.toJson(msg).getBytes()));
    }

//    public String receiveMSG() throws IOException {
//        ByteBuffer bb = ByteBuffer.allocate(datagramChannel.socket().getReceiveBufferSize()); // Создает буфер необходимого размера
//        while (bb.position() == 0)
//            datagramChannel.read(bb);
//        return new String(bb.array());
//
//    }

}
