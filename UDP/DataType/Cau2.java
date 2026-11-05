package UDP.DataType;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau2 {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int port = 807;

        String sCode = "B23DCCN230";
        String qCode = "";

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverIP = InetAddress.getByName(serverAddress);

            String request = ";" + sCode + ";" + qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIP, port);
            socket.send(sendPacket);

            byte[] receiveData = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
            String[] parts = response.split(";", 2);
            String requestId = parts[0];
            String data = parts[1];

            String[] items = data.split(",");
            String[] sorted = new String[items.length + 1];

            for (String item : items) {
                String[] tmp = item.split(":");
                String str = tmp[0];
                int pos = Integer.parseInt(tmp[1].trim());
                sorted[pos] = str;
            }

            StringBuilder res = new StringBuilder();

            for (int i = 1; i < sorted.length; i++) {
                res.append(sorted[i]);
            }

            String sendRes = requestId + ";" + res.toString();

            byte[] resData = sendRes.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, port);
            socket.send(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
