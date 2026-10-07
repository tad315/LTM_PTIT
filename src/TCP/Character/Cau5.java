package TCP.Character;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Cau5 {

    public static void main(String[] args) {

        String serverAddress = "";
        int serverPort = 808;

        String studentCode = "B23DCCN230";
        String qCode = "";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String request = studentCode + ";" + qCode;
            System.out.println(request);
            bw.write(request);
            bw.newLine();
            bw.flush();

            String received = br.readLine();
            System.out.println("Received: " + received);

            int[] count = new int[256];

            for (int i = 0; i < received.length(); ++i) {
                char c = received.charAt(i);
                if (Character.isLetterOrDigit(c)) {
                    count[c]++;
                }
            }

            String res = "";

            for (int i = 0; i < received.length(); ++i) {
                char c = received.charAt(i);
                if (Character.isLetterOrDigit(c) && count[c] > 1) {
                    res += c + ":" + count[c] + ",";
                    count[c] = 0;
                }
            }

            System.out.println("Result: " + res);

            bw.write(res);
            bw.newLine();
            bw.flush();

            br.close();
            bw.close();
            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}