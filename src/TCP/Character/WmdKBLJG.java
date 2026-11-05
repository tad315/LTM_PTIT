package TCP.Character;

import java.io.*;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class WmdKBLJG {
    public static void main (String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2208;

        String studentCode = "B23DCCN230";
        String qCode = "WmdKBLJG";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            socket.setSoTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String request = studentCode + ";" + qCode;
            writer.write(request);
            writer.newLine();
            writer.flush();
            System.out.println("Da gui " + request);

            String response = reader.readLine();
            System.out.println("Nhan tu server " + response);

            if (response != null && !response.isEmpty()) {
                Map<Character, Integer> charCnt = new LinkedHashMap<>();
                for (char ch : response.toCharArray()) {
                    if (Character.isLetterOrDigit(ch)) {
                        charCnt.put(ch, charCnt.getOrDefault(ch, 0) + 1);
                    }
                }
                StringBuffer sb = new StringBuffer();
                for (Map.Entry<Character, Integer> entry : charCnt.entrySet()) {
                    if (entry.getValue() > 1) {
                        sb.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
                    }
                }
                String res = sb.toString();

                writer.write(res);
                writer.newLine();
                writer.flush();
                System.out.println("Da gui " + res);
            }

            reader.close();
            writer.close();
            socket.close();
            System.out.println("Da dong ket noi");

        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}
