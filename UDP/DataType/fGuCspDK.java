package UDP.DataType;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class fGuCspDK {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2207;

        String studentCode = "B23DCCN230";
        String qCode = "fGuCspDK";

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

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);

            String[] parts = response.split(";", 2);
            String requestId = parts[0];
            String data = parts[1];
            String[] nums = data.split(",");

            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;

            for (String n : nums) {
                int val = Integer.parseInt(n.trim());
                if (val > max) {
                    max = val;
                }
                if (val < min) {
                    min = val;
                }
            }

            String res = requestId + ";" + max + "," + min;

            byte[] resData = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, serverPort);
            socket.send(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
