package UDP.Object;

import UDP.Employee;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau2 {
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

            Employee employee = (Employee) ois.readObject();

            String[] words = employee.getName().trim().toLowerCase().split("\\s+");
            StringBuilder normalized = new StringBuilder();
            for (String w : words) {
                if (normalized.length() > 0) {
                    normalized.append(" ");
                }
                normalized.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1));
            }
            employee.setName(normalized.toString());

            String[] parts = employee.getHireDate().trim().split("-", 3);
            String year = parts[0];
            int x = 0;
            for (int i = 0; i < 4; i++) {
                char c = year.charAt(i);
                x += c - '0';
            }
            double salary = employee.getSalary() * (1 + x / 100.0);
            employee.setSalary(salary);

            String newHireDate = parts[2] + "/" + parts[1] + "/" + parts[0];
            employee.setHireDate(newHireDate);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(employee);
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
