package TCP.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cau4 {

    public static void main(String[] args) {

        String serverAddress = "";
        int serverPort = 807;

        String studentCode = "B23DCCN230";
        String qCode = "";

        try {
            Socket socket = new Socket(serverAddress, serverPort);

            DataInputStream in = new DataInputStream(socket.getInputStream());

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            String request = studentCode + ";" + qCode;

            System.out.println(request);

            out.writeUTF(request);
            out.flush();

            String received = in.readUTF();
            int s = in.readInt();

            System.out.println(received);
            System.out.println(s);

            String res = ceasar(received, s);
            System.out.println(res);

            out.writeUTF(res);
            out.flush();

            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String ceasar (String text, int s) {
        String res = "";

        for  (int i = 0; i < text.length(); ++i) {
            char c = text.charAt(i);
            if (c >= 'A' && c <= 'Z') {
                c  = (char) ((c - 'A' + s) % 26 + 'A');
            }
            else if (c >= 'a' && c <= 'z') {
                c = (char) ((c - 'a' + s) % 26 + 'a');
            }
            res += c;
        }

        return res;
    }
}