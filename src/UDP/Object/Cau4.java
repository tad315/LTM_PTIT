package UDP.Object;

import UDP.Product;

import javax.xml.crypto.Data;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau4 {
    public static void main(String[] args) {
        String serverAddr = "";
        int port = 809;

        String sCode = "";
        String qCode = "";

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverIP = InetAddress.getByName(serverAddr);

            String request = ";" + sCode + ";" + qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverIP, port);
            socket.send(sendPacket);

            byte[] receiveData = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String requestId = new String(receivePacket.getData(), receivePacket.getOffset(), 8, StandardCharsets.UTF_8);

            ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData(), receivePacket.getOffset() + 8, receivePacket.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);

            Product product = (Product) ois.readObject();

            String[] words = product.getName().trim().split("\\s+");
            String tmp = words[0];
            words[0] = words[words.length - 1];
            words[words.length - 1] = tmp;
            product.setName(String.join(" ", words));

            String rev = new StringBuilder(String.valueOf(product.getQuantity())).reverse().toString();

            product.setQuantity(Integer.parseInt(rev));

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(product);
            oos.flush();

            byte[] objectByte = baos.toByteArray();
            byte[] requestIdByte = requestId.getBytes(StandardCharsets.UTF_8);

            ByteArrayOutputStream res = new ByteArrayOutputStream();
            res.write(requestIdByte);
            res.write(objectByte);

            byte[] resData = res.toByteArray();
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, port);
            socket.send(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
