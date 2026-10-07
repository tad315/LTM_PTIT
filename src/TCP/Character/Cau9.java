package TCP.Character;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Cau9 {

    public static void main(String[] args) {

        String serverAddress = "";
        int serverPort = 808;

        String studentCode = "B23DCCN230";
        String qCode = "";

        try {
            Socket socket = new Socket(serverAddress, serverPort);

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );

            BufferedWriter bw = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );

            String request = studentCode + ";" + qCode;

            System.out.println(request);

            bw.write(request);
            bw.newLine();
            bw.flush();

            String received = br.readLine();

            System.out.println("Received: " + received);

            String[] words = received.trim().split("\\s+");

            String res = "";

            for (int i = 0; i < words.length; ++i) {

                String reversed = new StringBuilder(words[i]).reverse().toString();
                if (i > 0) {
                    res += " ";
                }
                res += reversed;
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