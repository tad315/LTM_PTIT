package RMI.Byte;

import RMI.ByteService;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class nenRLE {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        String sv = "36.50.135.243";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";
        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        ByteService service = (ByteService) registry.lookup("RMIByteService");

        byte[] data = service.requestData(sCode, qCode);

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        int i = 0;

        while (i < data.length) {
            byte value = data[i];
            int count = 0;

            while (i < data.length && data[i] == value && count < 255) {
                count++;
                i++;
            }

            out.write(value);
            out.write(count);
        }

        byte[] result = out.toByteArray();

        service.submitData(sCode, qCode, result);
    }
}
