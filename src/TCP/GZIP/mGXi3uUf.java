package TCP.GZIP;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class mGXi3uUf {

    public static void main(String[] args) {

        String serverAddress = "36.50.135.242";
        int serverPort = 2210;

        String studentCode = "B23DCCN230";
        String qCode = "mGXi3uUf";

        try {
            Socket socket = new Socket(serverAddress, serverPort);

            String request = studentCode + ";" + qCode;

            GZIPOutputStream gzipOut = new GZIPOutputStream(socket.getOutputStream());

            gzipOut.write((request + "\n").getBytes(StandardCharsets.UTF_8));

            gzipOut.finish();
            gzipOut.flush();

            GZIPInputStream gzipIn = new GZIPInputStream(socket.getInputStream());

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int b;

            try {
                while ((b = gzipIn.read()) != -1) {
                    if (b == '\n') {
                        break;
                    }
                    buffer.write(b);
                }
            } catch (EOFException e) {

            }

            String received = buffer.toString(StandardCharsets.UTF_8);

            System.out.println("Received: " + received);

            String reversed = new StringBuilder(received).reverse().toString();

            String base64 = Base64.getEncoder().encodeToString(reversed.getBytes(StandardCharsets.UTF_8));

            String result = reversed + "|" + base64;

            System.out.println("Reversed: " + reversed);
            System.out.println("Base64: " + base64);
            System.out.println("Send: " + result);

            GZIPOutputStream resultOut = new GZIPOutputStream(socket.getOutputStream());

            resultOut.write((result + "\n").getBytes(StandardCharsets.UTF_8));

            resultOut.finish();
            resultOut.flush();

            socket.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}