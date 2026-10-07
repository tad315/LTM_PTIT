package TCP.Byte;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Cau4 {
    public static void main(String[] args) {
        String serverAddress = "";
        int serverPort = 806;

        String studentCode = "B23DCCN230";
        String qCode = "";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String request = studentCode + ";" + qCode;
            System.out.println(request);

            out.write(request.getBytes());
            out.flush();

            byte[] buffer = new byte[1024];
            int byteRead = in.read(buffer);
            String received = new String(buffer, 0, byteRead).trim();
            System.out.println(received);

            int n = Integer.parseInt(received);

            String res = "";
            int cnt = 0;

            while (true) {
                res += n;
                cnt++;
                if (n == 1) {
                    break;
                }
                res += " ";
                if (n % 2 == 0) {
                    n /= 2;
                }
                else {
                    n = 3 * n + 1;
                }
            }

            res = res + ";" + cnt;
            System.out.println(res);
            out.write(res.getBytes());
            out.flush();

            in.close();
            out.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
