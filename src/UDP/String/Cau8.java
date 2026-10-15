package UDP.String;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau8 {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int port = 808;

        String sCode = "";
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

            String[] words = data.trim().split("\\s+");

            StringBuilder strOutput = new StringBuilder();

            for (String word : words) {
                if (word.isEmpty()) {
                    continue;
                }
                boolean val = true;
                for (int i = 0; i < word.length(); i++) {
                    char c = Character.toLowerCase(word.charAt(i));
                    if ("aeuio".indexOf(c) == -1) {
                        val = false;
                        break;
                    }
                }
                if (val) {
                    if (strOutput.length() > 0) {
                        strOutput.append(",");
                    }
                    strOutput.append(word);
                }
            }

            String res = requestId + ";" + strOutput.toString();
            byte[] resData = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, port);
            socket.send(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

