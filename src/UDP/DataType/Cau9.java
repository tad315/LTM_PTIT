package UDP.DataType;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau9 {
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

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
            String[] parts = response.split(";", 4);
            String requestId = parts[0];
            int n = Integer.parseInt(parts[1].trim());
            int k = Integer.parseInt(parts[2].trim());
            String data = parts[3];

            String[] vals = data.split(",");
            int[] nums = new int[n];

            for (int i = 0; i < n; i++) {
                nums[i] = Integer.parseInt(vals[i].trim());
            }

            StringBuilder tmp = new StringBuilder();

            for (int i = 0; i <= n - k; i++) {
                int max = Integer.MIN_VALUE;
                for (int j = i; j < i + k; j++) {
                    if (nums[j] > max) {
                        max = nums[j];
                    }
                }
                if (tmp.length() > 0){
                    tmp.append(",");
                }
                tmp.append(max);
            }

            String res = requestId + ";" + tmp.toString();
            byte[] resData = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, port);
            socket.send(resPacket);

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
