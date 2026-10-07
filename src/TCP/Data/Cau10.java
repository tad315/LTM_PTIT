package TCP.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cau10 {

    public static void main(String[] args) {

        String serverAddress = "";
        int serverPort = 807;

        String studentCode = "B23DCCN230";
        String qCode = "";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String request = studentCode + ";" + qCode;

            System.out.println(request);

            dos.writeUTF(request);
            dos.flush();

            int k = dis.readInt();

            String received = dis.readUTF();

            System.out.println("k = " + k);
            System.out.println("Received: " + received);

            String[] parts = received.split(",");

            int[] nums = new int[parts.length];

            for (int i = 0; i < parts.length; ++i) {
                nums[i] = Integer.parseInt(parts[i].trim());
            }

            int n = nums.length;

            k = k % n;

            int[] rotated = new int[n];

            for (int i = 0; i < n; ++i) {
                int newPos = (i + k) % n;
                rotated[newPos] = nums[i];
            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < n; ++i) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(rotated[i]);
            }

            String res = sb.toString();

            System.out.println("Result: " + res);

            dos.writeUTF(res);
            dos.flush();

            dis.close();
            dos.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}