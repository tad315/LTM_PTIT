package UDP.DataType;

import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau3 {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int port = 807;

        String sCode = "B23DCCN230";
        String qCode = "";

        try {
          DatagramSocket socket = new DatagramSocket();
          InetAddress serverIP = InetAddress.getByName(serverAddress);

          String request = ";" + sCode + ";" + qCode;
          byte[] requestData = request.getBytes(StandardCharsets.UTF_8);
          DatagramPacket requestPacket = new DatagramPacket(requestData, requestData.length, serverIP, port);
          socket.send(requestPacket);

          byte[] receiveData = new byte[65535];
          DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
          socket.receive(receivePacket);

          String response = new String (receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
          String[] parts = response.split(";", 2);
          String requestId = parts[0];
          String num = parts[1].trim();

          int sumDigits = 0;
          for (int i = 0; i < num.length(); i++){
              char c = num.charAt(i);
              sumDigits += c - '0';
          }

          String res = requestId + ";" + sumDigits;
          byte[] resData = res.getBytes(StandardCharsets.UTF_8);
          DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, port);
          socket.send(resPacket);

          socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
