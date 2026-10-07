package TCP.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cau1 {

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

            int a = in.readInt();
            int b = in.readInt();

            System.out.println("a = " + a);
            System.out.println("b = " + b);

            int sum = a + b;
            int product = a * b;

            System.out.println("Tong = " + sum);
            System.out.println("Tich = " + product);

            out.writeInt(sum);
            out.writeInt(product);
            out.flush();

            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}