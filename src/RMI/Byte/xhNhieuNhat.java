package RMI.Byte;

import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class xhNhieuNhat {
    public static void main(String[] args) {
        String server = "36.50.135.242";
        int port = 1099;

        String sCode = "B23DCCN230";
        String qCode = "IM2fVWtf";

        try {
            Registry registry = LocateRegistry.getRegistry(server, port);
            ByteService service = (ByteService) registry.lookup("RMIByteService");
            byte[] data = service.requestData(sCode, qCode);

            byte maxVal = data[0];
            int maxCnt = 0;
            for (int i = 0; i < data.length; i++) {
                int cnt = 0;
                for (int j = 0; j < data.length; j++) {
                    if (data[i] == data[j]) {
                        cnt++;
                    }
                }
                if (cnt > maxCnt) {
                    maxCnt = cnt;
                    maxVal = data[i];
                }
            }
            byte[] res = {maxVal, (byte) maxCnt};
            service.submitData(sCode, qCode, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
