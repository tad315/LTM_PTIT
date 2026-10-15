package UDP.Object;

import UDP.Customer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Q6wPI3pnC {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2209;

        String studentCode = "B23DCCN230";
        String qCode = "6wPI3pnC";

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverID = InetAddress.getByName(serverAddress);

            String request = ";" + studentCode + ";" + qCode;
            byte[] sendData = request.getBytes(StandardCharsets.UTF_8);
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverID, serverPort);
            socket.send(sendPacket);

            byte[] receiveData = new byte[65535];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(receivePacket);

            String requestId = new String (receivePacket.getData(), receivePacket.getOffset(), 8, StandardCharsets.UTF_8);

            ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData(), 8, receivePacket.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);

            Customer customer = (Customer) ois.readObject();

            String name = customer.getName();
            String[] words = name.trim().toLowerCase().split("\\s+");
            StringBuilder normalizedName = new StringBuilder();

            normalizedName.append(words[words.length - 1].toUpperCase()).append(", ");

            for (int i = 0; i < words.length - 1; i++) {
                String w = words[i];
                normalizedName.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1)).append(" ");
            }
            String newName = normalizedName.toString().trim();

            customer.setName(newName);

            StringBuilder userName = new StringBuilder();
            for (int i = 0; i < words.length - 1; i++) {
                String w = words[i];
                userName.append(w.charAt(0));
            }
            userName.append(words[words.length - 1]);
            customer.setUserName(userName.toString());

            String dob = customer.getDayOfBirth();
            String[] parts = dob.split("-");
            String newDob = parts[1] + "/" + parts[0]+ "/" + parts[2];
            customer.setDayOfBirth(newDob);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(customer);
            oos.flush();

            byte[] objectData = baos.toByteArray();
            byte[] requestIdData = requestId.getBytes(StandardCharsets.UTF_8);

            ByteArrayOutputStream res = new ByteArrayOutputStream();

            res.write(requestIdData);
            res.write(objectData);

            byte[] resData = res.toByteArray();

            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverID, serverPort);
            socket.send(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
