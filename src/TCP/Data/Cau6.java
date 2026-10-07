package TCP.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cau6 {
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

            int[] count = new int[7];

            for (int i = 0; i < n; ++i) {
                int value = dis.readInt();
                count[value]++;
            }
            String res = "";
            float[] px = new float[7];
            for (int i = 1; i <= 6; ++i) {

                px[i] = (float) count[i] / n;
                res += px[i];

                if (i < 6) res += ",";
            }

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