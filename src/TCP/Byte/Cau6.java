package TCP.Byte;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cau6 {
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

            List<Integer> c = new ArrayList<>();
            List<Integer> l = new ArrayList<>();

            for (int i = 0; i < nums.length; ++i) {
                if (nums[i] % 2 == 0) {
                    c.add(nums[i]);
                }
                else {
                    l.add(nums[i]);
                }

            }

            String res = c.toString() + ";" + l.toString();

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
