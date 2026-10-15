package UDP.Object;

import UDP.Customer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau3 {
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

            Customer customer = (Customer) ois.readObject();

            String[] words = customer.getName().trim().toLowerCase().split("\\s+");
            StringBuilder newName = new StringBuilder();

            newName.append(words[words.length - 1].toUpperCase()).append(", ");
            for (int i = 0; i < words.length - 1; i++) {
                String str = words[i];
                newName.append(Character.toUpperCase(str.charAt(0))).append(str.substring(1)).append(" ");
            }
            customer.setName(newName.toString().trim());

            String[] parts = customer.getDayOfBirth().trim().split("-");
            String newDoB = parts[1] + "/" + parts[0] + "/" + parts[2];
            customer.setDayOfBirth(newDoB);

            StringBuilder userName = new StringBuilder();
            for (int i = 0; i < words.length - 1; i++) {
                userName.append(words[i].charAt(0));
            }
            userName.append(words[words.length - 1]);
            customer.setUserName(userName.toString());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(customer);
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
