package UDP.DataType;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau4 {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int port = 807;

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

            String response = new String (receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
            String[] parts = response.split(";", 3);
            String requestId = parts[0];
            int n = Integer.parseInt(parts[1]);
            String data = parts[2];

            String[] nums = data.split(",");
            boolean[] check = new boolean[n + 1];

            for (String num : nums) {
                int val = Integer.parseInt(num);
                check[val]  = true;
            }
            StringBuilder missingNum = new StringBuilder();
            for (int i = 1; i <= n; i++) {
                if (!check[i]) {
                    if (missingNum.length() > 0) {
                        missingNum.append(",");
                    }
                    missingNum.append(i);
                }
            }

            String res = requestId + ";" + missingNum.toString();

            byte[] resData = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, port);
            socket.send(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
