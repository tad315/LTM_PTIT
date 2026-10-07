package TCP.Object.p9LeE616;

import TCP.Object.Laptop;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPClient {
    public static void main (String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2209;

        String studentCode = "B23DCCN230";
        String qCode = "p9LeE616";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

            String request = studentCode + ";" + qCode;
            oos.writeObject(request);
            oos.flush();

            Laptop laptop = (Laptop) ois.readObject();

            System.out.println("Before: " + laptop.getName() + " " + laptop.getQuantity());

            laptop.setName(fixName(laptop.getName()));
            laptop.setQuanity(revNum(laptop.getQuantity()));

            System.out.println("After: " + laptop.getName() + " " + laptop.getQuantity());

            oos.writeObject(laptop);
            oos.flush();

            ois.close();
            oos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String fixName (String name) {
        String[] words = name.trim().split("\\s+");

        if (words.length >=2) {
            String tmp = words[0];
            words[0] = words[words.length - 1];
            words[words.length - 1] = tmp;
        }
        return String.join(" ", words);
    }

    public static int revNum (int num) {
        int rev = 0;
        while (num > 0) {
            rev = rev * 10 + num % 10;
            num /= 10;
        }
        return rev;
    }
}
