package UDP.DataType;

import java.awt.dnd.DropTarget;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau6 {
    public static void main(String[] args) {
        String serverAddress  = "36.50.135.242";
        int port =  807;

        String sCode = "";
        String qCode  = "";

        try  {
            DatagramSocket socket  = new DatagramSocket();
            InetAddress serverIP =  InetAddress.getByName(serverAddress);

            String request = ";" + sCode + ";" + qCode;
            byte[]  sendData = request.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIP, port);
            socket.send(sendPacket);

            byte[] receiveData = new  byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength(), StandardCharsets.UTF_8);
            String[] parts = response.split(";", 3);
            String requestId = parts[0];
            String str = parts[1];
            int s = Integer.parseInt(parts[2]);

            StringBuilder decode = new StringBuilder();

            s = ((s % 26) + 26) % 26;

            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    c = (char) ((c - 'A' - s + 26)  % 26 + 'A');
                }
                else if (c >= 'a' && c <= 'z') {
                    c = (char) ((c - 'a' - s + 26) % 26 + 'a');
                }
                decode.append(c);
            }

            String res = requestId + ";" + decode.toString();

            byte[] resData = res.getBytes(StandardCharsets.UTF_8);
            DatagramPacket resPacket =  new DatagramPacket(resData, resData.length, serverIP, port);
            socket.send(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
