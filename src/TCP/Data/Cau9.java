package TCP.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Cau9 {

    public static void main(String[] args) {

        String serverAddress = "";
        int serverPort = 807;

        String studentCode = "B23DCCN230";
        String qCode = "";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String request = studentCode + ";" + qCode;

            System.out.println(request);

            dos.writeUTF(request);
            dos.flush();

            String received = dis.readUTF();

            System.out.println("Received: " + received);

            String[] parts = received.split(",");
            int[] nums = new int[parts.length];

            for (int i = 0; i < parts.length; ++i) {
                nums[i] = Integer.parseInt(parts[i].trim());
            }

            int[] dp = new int[nums.length];
            int[] prev = new int[nums.length];

            int maxLen = 1;
            int end = 0;

            for (int i = 0; i < nums.length; ++i) {
                dp[i] = 1;
                prev[i] = -1;

                for (int j = 0; j < i; ++j) {
                    if (nums[j] < nums[i] &&
                            dp[j] + 1 > dp[i]) {

                        dp[i] = dp[j] + 1;
                        prev[i] = j;
                    }
                }

                if (dp[i] > maxLen) {
                    maxLen = dp[i];
                    end = i;
                }
            }

            List<Integer> lis = new ArrayList<>();

            while (end != -1) {
                lis.add(nums[end]);
                end = prev[end];
            }

            Collections.reverse(lis);

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < lis.size(); ++i) {
                if (i > 0) {
                    sb.append(",");
                }
                sb.append(lis.get(i));
            }

            String res = sb.toString();

            System.out.println("LIS: " + res);
            System.out.println("Length: " + maxLen);

            dos.writeUTF(res);
            dos.writeInt(maxLen);

            dos.flush();

            dis.close();
            dos.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}