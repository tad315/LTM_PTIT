package TCP.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class bAEG8DPW {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2207;

        String studentCode = "B23DCCN230";
        String qCode = "bAEG8DPW";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            socket.setSoTimeout(5000);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String request = studentCode + ";" + qCode;
            dos.writeUTF(request);
            dos.flush();
            System.out.println("Đã gửi: " + request);

            int a = dis.readInt();
            int b = dis.readInt();
            System.out.println("Nhận từ Server: a = " + a + ", b = " + b);

            int sum = a + b;
            int product = a * b;

            dos.writeInt(sum);
            dos.writeInt(product);
            dos.flush();
            System.out.println("Đã gửi Tổng = " + sum + ", Tích = " + product);

            dis.close();
            dos.close();
            socket.close();
            System.out.println("Đã đóng kết nối.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}