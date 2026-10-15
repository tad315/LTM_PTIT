package UDP.DataType;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Cau7 {
    public static void main(String[] args) {
        String serverAddress =  "36.50.135.242";
        int port = 807;

        String sCode = "";
        String qCode = "";

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverIP  = InetAddress.getByName(serverAddress);

            String request = ";" + sCode + ";" + qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIP, port);
            socket.send(sendPacket);

            byte[] receiveData = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String (receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
            String[] parts = response.split(";", 2);
            String requestId = parts[0];
            String data = parts[1];

            String[] vals = data.split(",");
            int[] nums = new int[vals.length];

            for (int i = 0; i < vals.length; i++) {
                nums[i]  = Integer.parseInt(vals[i].trim());
            }

            Arrays.sort(nums);

            int min = nums[0];
            int max = nums[nums.length - 1];

            int seMin = min;
            int seMax = max;

            for (int i = 0; i  < nums.length; i++) {
                if (nums[i] > min) {
                    seMin = nums[i];
                    break;
                }
            }
            for (int i = nums.length - 1; i >= 0; i--) {
                if (nums[i] < max) {
                    seMax = nums[i];
                    break;
                }
            }

            String res = requestId + ";" + seMax + "," + seMin;

            byte[] resData = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, port);
            socket.send(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
