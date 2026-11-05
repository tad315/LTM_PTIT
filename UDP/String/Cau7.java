package UDP.String;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cau7 {
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

            Map<Character, Integer> count = new HashMap<>();

            for (char c : data.toCharArray()) {
                count.put(c, count.getOrDefault(c, 0) + 1);
            }

            List<Map.Entry<Character, Integer>> list =
                    new ArrayList<>(count.entrySet());

            list.sort((a, b) -> {
                // Tần suất giảm dần
                int cmp = Integer.compare(b.getValue(), a.getValue());

                if (cmp != 0) {
                    return cmp;
                }

                // Cùng tần suất: ký tự tăng dần
                return Character.compare(a.getKey(), b.getKey());
            });

            StringBuilder strOutput = new StringBuilder();

            for (Map.Entry<Character, Integer> entry : list) {
                if (strOutput.length() > 0) {
                    strOutput.append(",");
                }

                strOutput.append(entry.getKey())
                        .append(":")
                        .append(entry.getValue());
            }

            String res = requestId + ";" + strOutput;
            byte[] resData = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, port);
            socket.send(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

