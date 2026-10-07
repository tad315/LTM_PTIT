package TCP.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cau5 {
    public static void main(String[] args) {

        String serverAddress = "";
        int serverPort = 807;

        String studentCode = "B23DCCN230";
        String qCode = "";

        try {
            Socket socket = new Socket(serverAddress, serverPort);

            DataInputStream dis =
                    new DataInputStream(socket.getInputStream());

            DataOutputStream dos =
                    new DataOutputStream(socket.getOutputStream());

            String request = studentCode + ";" + qCode;

            dos.writeUTF(request);
            dos.flush();

            int n = dis.readInt();

            int[] nums = new int[n];

            for (int i = 0; i < n; ++i) {
                nums[i] = dis.readInt();
            }

            int sum = 0;

            for (int num : nums) {
                sum += num;
            }

            float avg = (float) sum / n;

            float variance = 0;

            for (int num : nums) {
                variance += (num - avg) * (num - avg);
            }

            variance /= n;

            System.out.println("Sum = " + sum);
            System.out.println("Average = " + avg);
            System.out.println("Variance = " + variance);

            dos.writeInt(sum);
            dos.writeFloat(avg);
            dos.writeFloat(variance);

            dos.flush();

            dis.close();
            dos.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}