package TCP.Byte;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Cau1 {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 809;

        String studentCode = "B23DCCN230";
        String qCode = "2B3A6510";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            InputStream in = socket.getInputStream();
            OutputStream out = socket.getOutputStream();

            String request = studentCode + ";" + qCode;

            out.write(request.getBytes());;
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

            int max = Integer.MIN_VALUE;
            int secondMax = Integer.MIN_VALUE;

            for (int num : nums) {
                if (num > max) {
                    secondMax = max;
                    max = num;
                }
                else if (num > secondMax && num < max) {
                    secondMax = num;
                }
            }

            int pos = -1;
            for (int i = 0; i < nums.length; ++i) {
                if (nums[i] == secondMax) {
                    pos = i;
                    break;
                }
            }

            String res = secondMax + "," + pos;
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
