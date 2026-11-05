package TCP.Byte;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Cau7 {
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

            int max = 1, cur = 1;

            for (int i = 0; i < nums.length - 1; ++i) {
                if (nums[i] < nums[i + 1]) {
                    cur++;
                }
                else {
                    cur = 1;
                }
                if (cur > max) {
                    max = cur;
                }
            }

            String res = String.valueOf(max);
// Trường hợp yêu cầu in thêm chuỗi con
//            int max = 1, cur = 1;
//            int end = 0;
//
//            for (int i = 0; i < nums.length - 1; ++i) {
//                if (nums[i] < nums[i + 1]) {
//                    cur++;
//                } else {
//                    cur = 1;
//                }
//
//                if (cur > max) {
//                    max = cur;
//                    end = i + 1;
//                }
//            }
//
//// Vị trí bắt đầu
//            int start = end - max + 1;
//
//// Lấy chuỗi con
//            String sub = "";
//
//            for (int i = start; i <= end; ++i) {
//                sub += nums[i];
//
//                if (i < end) {
//                    sub += ",";
//                }
//            }

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
