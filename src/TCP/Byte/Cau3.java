package TCP.Byte;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Cau3 {
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

            int n = in.read(buffer);

            String received = new String(buffer, 0, n).trim();
            System.out.println(received);

            String longest = "";
            for (int i = 0; i < received.length(); ++i) {
                String cur = "";
                for (int j = i; j < received.length(); ++j) {
                    char c = received.charAt(j);
                    if (cur.indexOf(c) != -1) {
                        break;
                    }
                    cur += c;
                }
                if (cur.length() > longest.length()) {
                    longest = cur;
                }
            }

            String res = longest + ";" + longest.length();
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
