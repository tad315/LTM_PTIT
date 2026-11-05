package UDP.Object;

import UDP.Book;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Cau5 {
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

            Book book = (Book) ois.readObject();

            String[] words = book.getTitle().trim().toLowerCase().split("\\s+");
            StringBuilder title = new StringBuilder();
            for (String w : words) {
                title.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1)).append(" ");
            }
            book.setTitle(title.toString().trim());

            String[] partName = book.getAuthor().trim().toLowerCase().split("\\s+");
            StringBuilder author = new StringBuilder();
            author.append(Character.toUpperCase(partName[0].charAt(0))).append(partName[0].substring(1)).append(", ");
            for (int i = 1; i < partName.length; i++) {
                author.append(Character.toUpperCase(partName[i].charAt(0))).append(partName[i].substring(1)).append(" ");
            }
            book.setAuthor(author.toString().trim());

            String ibsn = book.getIsbn().replace("-", "").trim();
            String newIbsn = ibsn.substring(0, 3) + "-"
                    + ibsn.substring(3, 4) + "-"
                    + ibsn.substring(4, 6) + "-"
                    + ibsn.substring(6, 12) + "-"
                    + ibsn.substring(12, 13);
            book.setIsbn(newIbsn);

            String[] partDate = book.getPublishDate().split("-", 3);
            String date = partDate[1] + "/" + partDate[0];
            book.setPublishDate(date);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(book);
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
