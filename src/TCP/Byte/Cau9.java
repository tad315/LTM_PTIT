package TCP.Byte;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;

public class Cau9 {
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

            double avg = 0;
            for (int i = 0; i < nums.length; ++i) {
                avg +=  nums[i];
            }

            avg  /= nums.length;

            int num1  = 0, num2 = 0;
            double minD = Double.MAX_VALUE;

            for  (int i = 0; i < nums.length - 1;  ++i) {
                for (int j = i + 1; j < nums.length; ++j) {
                    double cur = abs((nums[i] + nums[j]) - avg);
                    if (cur < minD) {
                        minD = cur;
                        num1 = nums[i];
                        num2 = nums[j];
                    }
                }
            }
            String res = num1 + "," + num2;
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
