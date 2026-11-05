package RMI.Byte;

import RMI.ByteService;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class xhItNhat {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        String server = "36.50.135.242";
        int port = 1099;

        String sCode = "B23DCCN230";
        String qCode = "a2J2TnvE";

        Registry registry = LocateRegistry.getRegistry(server, port);

        ByteService service = (ByteService) registry.lookup("RMIByteService");

        byte[] data = service.requestData(sCode, qCode);

        byte minValue = data[0];
        int minCnt = data.length + 1;

        for (int i = 0; i < data.length; i++) {
            int cnt = 0;
            for (int j = 0; j < data.length; j++) {
                if (data[i] == data[j]) {
                        cnt++;
                }
            }
            if (cnt < minCnt) {
                minCnt = cnt;
                minValue = data[i];
            }
        }

        byte[] result = {minValue, (byte) minCnt};
        service.submitData(sCode, qCode, result);
    }
}
