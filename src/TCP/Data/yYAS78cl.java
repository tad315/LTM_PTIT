package TCP.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class yYAS78cl {
    public static void main (String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2207;

        String studentCode = "B23DCCN230";
        String qCode = "yYAS78cl";

        try {
            Socket socket = new Socket(serverAddress, serverPort);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String request = studentCode + ";" + qCode;
            dos.writeUTF(request);
            dos.flush();

            String encrypted = dis.readUTF();
            int s = dis.readInt();

            String decrypted = decryptCaesar(encrypted, s);

            dos.writeUTF(decrypted);
            dos.flush();

            System.out.println("Encrypted: " + encrypted);
            System.out.println("Shift s: " + s);
            System.out.println("Decrypted: " + decrypted);

            dis.close();
            dos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String decryptCaesar (String text, int s) {
        StringBuilder res = new StringBuilder();
        s = s % 26;
        for (char c : text.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                char decoded = (char) ((c - 'A' - s + 26) % 26 + 'A');
                res.append(decoded);
            }
            else if (c >= 'a' && c <= 'z') {
                char decoded = (char) ((c - 'a' -s + 26) % 26 + 'a');
                res.append(decoded);
            }
            else {
                res.append(c);
            }
        }
        return res.toString();
    }
}
