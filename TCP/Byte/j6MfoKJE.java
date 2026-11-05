package TCP.Byte;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class j6MfoKJE {
    public static void main (String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2206;

        String studentCode = "B23DCCN230";
        String qCode = "j6MfoKJE";

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
                System.out.println("Da nhan: " + response);

                String[] strNums = response.split(",");
                List<Integer> nums = new ArrayList<>();
                for (String str: strNums) {
                    nums.add(Integer.parseInt(str.trim()));
                }
                int maxVal = Integer.MIN_VALUE;
                for (int num: nums) {
                    if (num > maxVal) {
                        maxVal = num;
                    }
                }

                int secMax = Integer.MIN_VALUE;
                int secIdx = -1;
                for (int i = 0; i < nums.size() - 1; ++i) {
                    int val = nums.get(i);
                    if (val < maxVal && val > secMax) {
                        secMax = val;
                        secIdx = i;
                    }
                }

                String res = secMax + "," + secIdx;

                os.write(res.getBytes());
                os.flush();
                System.out.println("Da gui ket qua: " + res);
            }

            is.close();
            os.close();
            socket.close();
            System.out.println("Da dong server");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
