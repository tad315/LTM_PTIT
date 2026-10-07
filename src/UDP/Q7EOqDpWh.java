package UDP;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class Q7EOqDpWh {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2208;

        String studentCode = "B23DCCN230";
        String qCode = "7EOqDpWh";

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverIP = InetAddress.getByName(serverAddress);

            //a. Gui request
            String request = ";" + studentCode + ";" + qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);

            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIP, serverPort);
            socket.send(sendPacket);

            //b. Nhan request, data
            byte[] receiveData = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);

            String[] parts = response.split(";", 2);
            String requestId = parts[0];
            String data = parts[1];

            int[] cnt = new int[256];

            for (int i = 0; i < data.length(); i++) {
                cnt[data.charAt(i)]++;
            }

            char maxChar = data.charAt(0);
            int maxCnt = 0;

            for (int i = 0; i < data.length(); i++){
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

            String res = requestId + ";" + maxChar + ":" + pos;

            byte[] resData = res.getBytes(StandardCharsets.UTF_8);

            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, serverPort);
            socket.send(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
