package UDP.DataType;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class JOPabPVR {
    public static void main (String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2207;

        String studentCode = "B23DCCN230";
        String qCode = "JOPabPVR";

        try {
            DatagramSocket socket = new DatagramSocket();
            socket.setSoTimeout(5000);
            InetAddress ip = InetAddress.getByName(serverAddress);

            String request = ";" + studentCode + ";" + qCode;
            byte[] sendData = request.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip, serverPort);
            socket.send(sendPacket);
            System.out.println("Da gui " + request);

            byte[] receiveData = new byte[2048];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength()).trim();
            System.out.println("Nhan tu server " + response);

            String[] parts = response.split(";", 3);
            String requestId = parts[0];
            int n = Integer.parseInt(parts[1].trim());
            String[] nums = parts[2].split(",");

            boolean[] check = new boolean[n + 1];

            for (String num : nums) {
                int val = Integer.parseInt(num);
                check[val] = true;
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
            System.out.println(res);

            byte[] resData = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, ip, serverPort);
            socket.send(resPacket);

            socket.close();
            System.out.println("Da dong ket noi");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
