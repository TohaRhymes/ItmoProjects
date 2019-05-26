import com.google.gson.Gson;

import javax.swing.*;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.ArrayList;

public class ClientReceiver extends SwingWorker<String, String> {

    private DatagramChannel datagramChannel;
    private Gson gson;
    private String scommand;
    private ArrayList<String> sargs;

    public ClientReceiver(DatagramChannel datagramChannel) {
        this.datagramChannel = datagramChannel;
        gson = MainClient.getGson();
    }

    @Override
    public String doInBackground() {
        try {
            // Приём исходных данных для первой отрисовки, захватывает блокировку для того, чтобы мы успели принять данные раньше, чем начнем отрисовывать окно в параллельном потоке
            MainClient.getLock().lock();
            String mes = receiveMSG().trim();
            mes = mes.substring(0, mes.lastIndexOf("&"));
            String[] bufmass = mes.split("&");
            for (String s: bufmass) {
                MainClient.datamass.add(s);
            }

            MainClient.getLock().unlock();

            while (true) { // В бесконечном цикле ожидаем сообщений от сервера и обрабатываем их

                String s = receiveMSG();
                ServerMSG serverMSG = gson.fromJson(s.trim(), ServerMSG.class);
                scommand = serverMSG.getScommand();
                sargs = serverMSG.getSargs();

                switch (scommand) {

                    case "login":
                        System.out.println(sargs.get(0));
                        try {
                            MainClient.setCookie(sargs.get(0).split("#")[1]);

                            MainClient.getFrame().repaintMenuBar();
                            MainClient.getFrame().setVisible(true);
                            MainClient.getLoginOrRegisterWindow().setVisible(false);


                        } catch (Exception e) {
                            MainClient.getFrame().setVisible(false);
                            MainClient.getLoginOrRegisterWindow().showMes(sargs.get(0));
                        }
                        break;

                    case "update":
                        TimeTraveller nw = MainClient.getGson().fromJson(sargs.get(0), TimeTraveller.class);
                        MyTable.getTardisModel().replaceTime(nw);
                        // TODO: add delete
                        break;

                    case "remove":
                        TimeTraveller removeTraveller = MainClient.getGson().fromJson(sargs.get(0), TimeTraveller.class);
                        MyTable.getTardisModel().removeTr(removeTraveller);
                        MainFrame.getMyTable().setRowSelectionInterval(0, 0);
                        break;

                    case "add":
                        TimeTraveller newTraveller = MainClient.getGson().fromJson(sargs.get(0), TimeTraveller.class);
                        MyTable.getTardisModel().addTimeTr(newTraveller);
//                        MyTable.getTardisModel().fireTableDataChanged();
                        int indexView = MainFrame.getMyTable().getRowSorter().convertRowIndexToView(MainFrame.getMyTable().getTardisModel().getData().indexOf(newTraveller));
                        MainFrame.getMyTable().setRowSelectionInterval(indexView, indexView);
                        break;

                    case "register":
                        System.out.println(sargs.get(0));
                        try {
                            String chek = sargs.get(0).split("#")[1];
                            MainClient.getLoginOrRegisterWindow().successfulReg();

                        } catch (Exception e) {
                            MainClient.getLoginOrRegisterWindow().incorrectReg();
                        }

                        break;
                }
            }
        } catch (IOException e) {
            System.out.println("Мяу, прием сообщений сломался");
            return "Я завершился";
        }
    }

    public String receiveMSG() throws IOException {
        ByteBuffer bb = ByteBuffer.allocate(datagramChannel.socket().getReceiveBufferSize()); // Создает буфер необходимого размера
        while (bb.position() == 0)
            datagramChannel.read(bb);
        return new String(bb.array());

    }
}
