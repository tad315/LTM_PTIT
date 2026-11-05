package UDP.String;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class Q8eZb9MGG {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2208;

        String studentCode = "B23DCCN230";
        String qCode = "8eZb9MGG";

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverIP = InetAddress.getByName(serverAddress);

            String request = ";" + studentCode + ";" + qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIP, serverPort);
            socket.send(sendPacket);

            byte[] receiveData = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(receiveData, 0, receivePacket.getLength(), StandardCharsets.UTF_8);
            String[] parts = response.split(";", 2);
            String requestId = parts[0];
            String data = parts[1];

            String[] words = data.trim().split("\\s+");

            StringBuilder normalized = new StringBuilder();

            for (String w : words) {
                if (!w.isEmpty()) {
                    normalized.append(Character.toUpperCase(w.charAt(0)));
                    if (w.length() > 1) {
                        normalized.append(w.substring(1).toLowerCase());
                    }
                    normalized.append(" ");
                }
            }

            String res = requestId + ";" + normalized.toString().trim();
            byte[] resData = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, serverPort);
            socket.send(resPacket);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
