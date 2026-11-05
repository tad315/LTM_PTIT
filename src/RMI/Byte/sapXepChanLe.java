
package RMI.Byte;

import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class sapXepChanLe {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        ByteService service = (ByteService)
                registry.lookup("RMIByteService");

        byte[] data = service.requestData(sCode, qCode);

        byte[] result = new byte[data.length];

        int k = 0;

        // Vong 1: Lay cac so chan
        for (int i = 0; i < data.length; i++) {
            if (data[i] % 2 == 0) {
                result[k] = data[i];
                k++;
            }
        }

        // Vong 2: Lay cac so le
        for (int i = 0; i < data.length; i++) {
            if (data[i] % 2 != 0) {
                result[k] = data[i];
                k++;
            }
        }

        service.submitData(sCode, qCode, result);
    }
}
