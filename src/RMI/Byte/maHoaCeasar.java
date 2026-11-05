package RMI.Byte;

import RMI.ByteService;

import java.nio.charset.StandardCharsets;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class maHoaCeasar {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        String sv = "36.50.135.243";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        ByteService service = (ByteService) registry.lookup("RMIByteService");

        byte[] data = service.requestData(sCode, qCode);

        // So buoc dich = so ky tu trong mang
        int shift = data.length;

        // Ma hoa Caesar
        for (int i = 0; i < data.length; i++) {
            data[i] = (byte) ((data[i] + shift) % 128);
        }

        service.submitData(sCode, qCode, data);
    }
}
