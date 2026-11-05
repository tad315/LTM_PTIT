
package TCP.Character;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class l6b05RHL {
    public static void main (String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2208;

        String studentCode = "B23DCCN230";
        String qCode = "l6b05RHL";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            socket.setSoTimeout(5000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String request = studentCode + ";" + qCode;
            writer.write(request);
            writer.newLine();
            writer.flush();
            System.out.println("Đã gửi: " + request);

            String response = reader.readLine();
            System.out.println("Đã nhận: " + response);

            if (response != null && !response.isEmpty()) {
                String[] domains = response.split(",");
                List<String> eduDomains = new ArrayList<>();

                for (String domain : domains) {
                    domain = domain.trim();
                    if (domain.endsWith(".edu")) {
                        eduDomains.add(domain);
                    }
                }

                String result = String.join(", ", eduDomains);

                writer.write(result);
                writer.newLine();
                writer.flush();
                System.out.println("Đã gửi kết quả: " + result);
            }

            reader.close();
            writer.close();
            socket.close();
            System.out.println("Đã đóng kết nối.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
