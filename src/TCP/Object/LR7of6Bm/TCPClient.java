package TCP.Object.LR7of6Bm;

import TCP.Object.Customer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPClient {
    public static void main (String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2209;

        String studentCode = "B23DCCN230";
        String qCode = "LR7of6Bm";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            String request = studentCode + ";" + qCode;
            oos.writeObject(request);
            oos.flush();

            Customer customer = (Customer) ois.readObject();

            System.out.println("Before:");
            System.out.println("Name: " + customer.getName());
            System.out.println("DOB: " + customer.getDayOfBirth());
            System.out.println("Username: " + customer.getUserName());

            String originalName = customer.getName();

            customer.setName(formatName(originalName));
            customer.setDayOfBirth(formatDate(customer.getDayOfBirth()));
            customer.setUserName(createUsername(originalName));

            System.out.println("After:");
            System.out.println("Name: " + customer.getName());
            System.out.println("DOB: " + customer.getDayOfBirth());
            System.out.println("Username: " + customer.getUserName());

            oos.writeObject(customer);
            oos.flush();

            ois.close();
            oos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String formatName (String name) {
        String[] words = name.trim().toLowerCase().split("\\s+");
        String lastName = words[words.length - 1].toUpperCase();
        StringBuilder res = new StringBuilder();

        res.append(lastName).append(", ");
        for (int i = 0; i < words.length - 1; ++i) {
            String word = words[i];
            res.append(Character.toUpperCase(word.charAt(0)) + word.substring(1)) ;
            if (i < words.length - 2) {
                res.append(" ");
            }
        }
        return res.toString();
    }
    public static String formatDate (String date) {
        String[] parts = date.split("-");
        return parts[1] + "/" +parts[0] + "/" + parts[2];
    }
    public static String createUsername (String name) {
        String[] words = name.trim().toLowerCase().split("\\s+");
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < words.length - 1; ++i) {
            res.append(words[i].charAt(0));
        }
        res.append(words[words.length - 1]);
        return res.toString();

    }
}
