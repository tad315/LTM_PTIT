package TCP.Byte;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;

public class Cau5 {
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
            Arrays.sort(nums);

            int d = Integer.MAX_VALUE;

            int fi = 0;
            int se = 0;

            for (int i = 0; i < nums.length - 1; ++i) {
                int cur = nums[i + 1] - nums[i];
                if (cur < d) {
                    d = cur;
                    fi = nums[i];
                    se = nums[i + 1];
                }
            }

            String res = d + "," + fi + "," + se;
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
