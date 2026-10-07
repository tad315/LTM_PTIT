package TCP.Character;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Cau2 {

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

            String[] words = received.split("\\s+");

            String longest = "";

            for (String word : words) {
                if (word.length() > longest.length()) {
                    longest = word;
                }
            }

            int position = received.indexOf(longest);

            System.out.println("Longest: " + longest);
            System.out.println("Position: " + position);

            bw.write(longest);
            bw.newLine();
            bw.flush();

            bw.write(String.valueOf(position));
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