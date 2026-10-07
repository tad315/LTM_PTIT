package TCP.Character;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Cau3 {

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

            String normal = "";
            String special = "";

            for (int i = 0; i < received.length(); ++i) {
                char c = received.charAt(i);
                if (Character.isLetterOrDigit(c)) {
                    normal += c;
                } else {
                    special += c;
                }
            }

            System.out.println("Normal: " + normal);
            System.out.println("Special: " + special);

            bw.write(normal);
            bw.newLine();
            bw.flush();

            bw.write(special);
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