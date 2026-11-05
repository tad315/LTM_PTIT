package UDP.DataType;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau1 {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int Port = 807;

        String sCode = "B23DCCN230";
        String qCode = "";

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverIP = InetAddress.getByName(serverAddress);

            String request = ";" + sCode + ";" + qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIP, Port);
            socket.send(sendPacket);

            byte[] receiveData = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
            String[] parts = response.split(";", 3);
            String requestId = parts[0].trim();
            int n = Integer.parseInt(parts[1].trim());
            String data = parts[2].trim();

            boolean[] check = new boolean[n + 1];

            if (!data.isEmpty()) {
                String[] nums = data.split(",");
                for (String num : nums) {
                    int val = Integer.parseInt(num.trim());
                    check[val] = true;
                }
            }

            StringBuilder missingNum = new StringBuilder();
            for (int i = 1; i <= n; ++i) {
                if (!check[i]) {
                    if (missingNum.length() > 0) {
                        missingNum.append(",");
                    }
                    missingNum.append(i);
                }
            }

            String res = requestId + ";" + missingNum.toString();

            byte[] sendRes = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket = new DatagramPacket(sendRes, sendRes.length, serverIP, Port);
            socket.receive(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
