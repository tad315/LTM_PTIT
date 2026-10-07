package TCP.Byte;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class _6O6A0GrI {
    public static void main (String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2206;

        String studentCode = "B23DCCN230";
        String qCode = "6O6A0GrI";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            socket.setSoTimeout(5000);

            InputStream is = socket.getInputStream();
            OutputStream os = socket.getOutputStream();

            String request = studentCode + ";" + qCode;
            os.write(request.getBytes());
            os.flush();
            System.out.println("Da gui: " + request);

            byte[] buffer = new byte[1024];
            int bytesRead = is.read(buffer);

            if (bytesRead != -1) {
                String response = new String(buffer, 0, bytesRead).trim();
                System.out.println("Nhan tu server: " + response);

                String[] strNums = response.split(",");
                List<Integer> nums = new ArrayList<>();
                for (String str : strNums) {
                    nums.add(Integer.parseInt(str.trim()));
                }

                Collections.sort(nums);

                int minDiff = Integer.MAX_VALUE;
                int num1 = -1, num2 = -1;

                for (int i = 0; i < nums.size() - 1; ++i) {
                    int d = nums.get(i + 1) - nums.get(i);
                    if (d <= minDiff) {
                        minDiff = d;
                        num1 = nums.get(i + 1);
                        num2 = nums.get(i);
                    }
                }

                String res = minDiff + "," + num2 + "," + num1;

                os.write(res.getBytes());
                os.flush();
                System.out.println("Da gui ket qua: " + res);
            }

            is.close();
            os.close();
            socket.close();
            System.out.println("Da dong ket noi");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
