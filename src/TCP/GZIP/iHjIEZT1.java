package TCP.GZIP;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class iHjIEZT1 {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2210;

        String studentCode = "B23DCCN230";
        String qCode = "iHjIEZT1";

        try {
            Socket socket = new Socket(serverAddress, serverPort);
            String request = studentCode + ";" + qCode;

            GZIPOutputStream out = new GZIPOutputStream(socket.getOutputStream());
            out.write((request + "\n").getBytes(StandardCharsets.UTF_8));
            out.finish();
            out.flush();

            GZIPInputStream in = new GZIPInputStream(socket.getInputStream());
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int b;

            try {
                while ((b = in.read()) != -1) {
                    if (b == '\n') {
                        break;
                    }
                    buffer.write(b);
                }
            } catch (EOFException e) {

            }

            String received = buffer.toString(StandardCharsets.UTF_8);

            System.out.println("Received: " + received);

            char[] chars = received.toCharArray();
            Arrays.sort(chars);
            String res = new String(chars);
            System.out.println("Sorted: " + res);

            GZIPOutputStream resOut = new GZIPOutputStream(socket.getOutputStream());
            resOut.write((res + "\n").getBytes(StandardCharsets.UTF_8));

            resOut.finish();
            resOut.flush();

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
