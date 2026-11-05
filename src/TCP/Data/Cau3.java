package TCP.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cau3 {

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

            int n = in.readInt();

            String binary = Integer.toBinaryString(n);
            String hex = Integer.toHexString(n).toUpperCase();

//            // Nhị phân -> thập phân
//            String binary = "11101101111010";
//            int decimal1 = Integer.parseInt(binary, 2);
//
//            // Thập lục phân -> thập phân
//            String hex = "3B7A";
//            int decimal2 = Integer.parseInt(hex, 16);

            String res = binary + ";" + hex;

            out.writeUTF(res);
            out.flush();

            in.close();
            out.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}