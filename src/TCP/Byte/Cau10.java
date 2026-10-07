package TCP.Byte;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Cau10 {
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

            String[] parts = received.split(",");
            int[] nums = new int[parts.length];

            for (int i = 0; i < parts.length; ++i) {
                nums[i] = Integer.parseInt(parts[i].trim());
            }

            int maxLen = 0;
            int start = -1, end = -1;

            for (int i = 0; i < nums.length;  i++) {
                int sum = 0;
                for (int j = i; j < nums.length; ++j) {
                    sum += nums[j];
                    if (sum % 3 == 0) {
                        int len = j - i + 1;
                        if (len > maxLen) {
                            maxLen = len;
                            start = i;
                            end = j;
                        }
                    }
                }
            }

            String res = start + ";" + end;

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

    public static boolean checkSnt (int n) {
        if (n <= 1) return false;
        if (n == 2) return true;
        else if (n % 2 == 0) return false;
        for (int i = 3; i <= sqrt(n); i+=2) {
            if (n % i == 0) return false;
        }
        return true;
    }
}
