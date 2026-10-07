package TCP.Character;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Cau8 {

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
            String[] words = received.trim().split("\\s+");

            List<String> shortWords = new ArrayList<>();
            List<String> mediumWords = new ArrayList<>();
            List<String> longWords = new ArrayList<>();

            for (String word : words) {
                if (word.length() < 4) {
                    shortWords.add(word);
                }
                else if (word.length() <= 7) {
                    mediumWords.add(word);
                }
                else {
                    longWords.add(word);
                }
            }

            String res =
                    "[" + String.join(", ", shortWords) + "]," +
                            "[" + String.join(", ", mediumWords) + "]," +
                            "[" + String.join(", ", longWords) + "]";

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