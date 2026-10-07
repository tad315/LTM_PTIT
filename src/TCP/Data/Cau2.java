package TCP.Data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cau2 {
    public static void main(String[] args) {
        String serverAddress = "";
        int serverPort = 807;

        String  studentCode = "B23DCCN230";
        String qCode = "";

        try  {
            Socket socket = new Socket(serverAddress, serverPort);

            DataInputStream  dis = new DataInputStream(socket.getInputStream());

            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            String  request = studentCode + ";" +  qCode;
            System.out.println(request);

            dos.writeUTF(request);
            dos.flush();

            int a = dis.readInt();
            int b = dis.readInt();

            int ucln = UCLN(a,b);
            int bcnn = a * b  / ucln;
            int sum = a + b;
            int p = a* b;

            System.out.println(ucln);
            System.out.println(bcnn);
            System.out.println(sum);
            System.out.println(p);

            dos.writeInt(ucln);
            dos.writeInt(bcnn);
            dos.writeInt(sum);
            dos.writeInt(p);
            dos.flush();

            dis.close();
            dos.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int UCLN (int a, int b) {
        while (b != 0) {
            int tmp = b;
            b = a % b;
            a = tmp;
        }
        return a;
    }
}
