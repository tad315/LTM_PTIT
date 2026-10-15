package UDP.Object;

import UDP.Student;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class QkDQn34P7 {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2209;

        String studentCode = "B23DCCN230";
        String qCode = "kDQn34P7";

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

            String requestId = new String(receivePacket.getData(), receivePacket.getOffset(), 8, StandardCharsets.UTF_8);

            ByteArrayInputStream bais = new ByteArrayInputStream(receivePacket.getData(), receivePacket.getOffset() + 8, receivePacket.getLength() - 8);
            ObjectInputStream ois = new ObjectInputStream(bais);

            Student student = (Student) ois.readObject();

            String[] words = student.getName().trim().toLowerCase().split("\\s+");

            StringBuilder normalized = new StringBuilder();

            for (String w : words) {
                if (normalized.length() > 0) {
                    normalized.append(" ");
                }
                normalized.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1));
            }
            student.setName(normalized.toString());

            StringBuilder email = new StringBuilder();
            email.append(words[words.length - 1]);

            for (int i = 0; i < words.length - 1; i++) {
                email.append(words[i].charAt(0));
            }
            email.append("@ptit.edu.vn");
            student.setEmail(email.toString());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(student);
            oos.flush();

            byte[] requestIdData = requestId.getBytes(StandardCharsets.UTF_8);
            byte[] objectData = baos.toByteArray();

            ByteArrayOutputStream res = new ByteArrayOutputStream();

            res.write(requestIdData);
            res.write(objectData);

            byte[] resData = res.toByteArray();
            DatagramPacket resPacket = new DatagramPacket(resData, resData.length, serverIP, serverPort);
            socket.send(resPacket);

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
