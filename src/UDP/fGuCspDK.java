package UDP;

import java.net.*;

public class fGuCspDK {
    public static void main(String[] args) {
        String serverAddress = "36.50.135.242";
        int serverPort = 2207;

        String studentCode = "B23DCCN230";
        String qCode = "fGuCspDK";

        try {
            DatagramSocket socket = new DatagramSocket();
            InetAddress serverIP = InetAddress.getByName(serverAddress);

            String request = ";" + studentCode + ";" + qCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
