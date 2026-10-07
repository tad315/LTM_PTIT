package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

            String[] parts = response.split(";");
            if (parts.length >= 3) {
                String requestId = parts[0];
                int n = Integer.parseInt(parts[1].trim());

                Set<Integer> receiveNums = new HashSet<>();
                if (!parts[2].isEmpty()) {
                    String[] numStrList = parts[2].split(",");
                    for (String str : numStrList) {
                        receiveNums.add(Integer.parseInt(str.trim()));
                    }
                }

                List<String> missingNums = new ArrayList<>();
                for (int i = 1; i <= n; ++i) {
                    if (!receiveNums.contains(i)) {
                        missingNums.add(String.valueOf(i));
                    }
                }
                String res = requestId + ";" + String.join(",", missingNums);
                byte[] resBytes = res.getBytes();
                DatagramPacket resPacket = new DatagramPacket(resBytes, resBytes.length, ip, serverPort);
                socket.send(resPacket);
                System.out.println("Da gui kq " + res);
            }

            socket.close();
            System.out.println("Da dong ket noi");
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
