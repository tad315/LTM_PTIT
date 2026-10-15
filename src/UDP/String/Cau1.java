package UDP.String;

import java.awt.dnd.DropTarget;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau1 {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int port = 808;

        String sCode = "";
        String qCode = "";

        try{
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverIP = InetAddress.getByName(serverAddress);

            String request = ";" + sCode + ";" + qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIP, port);
            socket.send(sendPacket);

            byte[] receiveData = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String (receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
            String[] parts = response.split(";", 2);
            String requestId = parts[0];
            String data = parts[1];

            int[] cnt = new int[256];

            for (int i = 0; i < data.length(); i++) {
                cnt[data.charAt(i)]++;
            }

            char maxChar = data.charAt(0);
            int maxCnt = 0;

            for (int i = 0; i < data.length(); i++) {
                char c = data.charAt(i);
                if (cnt[c] > maxCnt) {
                    maxChar = c;
                    maxCnt = cnt[c];
                }
            }

            StringBuilder pos = new StringBuilder();

            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == maxChar) {
                    pos.append(i + 1).append(",");
                }
            }

            String res = requestId + ";" + maxChar + ":" + pos.toString();
            byte[] resData = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, port);
            socket.send(resPacket);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
