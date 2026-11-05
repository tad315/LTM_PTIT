
package RMI.Byte;

import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Base64;
import java.util.Arrays;

public class giaiMaBase64 {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI_4";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        ByteService service = (ByteService)
                registry.lookup("RMIByteService");

        // Nhan du lieu Base64
        byte[] data = service.requestData(sCode, qCode);

        // Giai ma Base64
        byte[] result = Base64.getDecoder().decode(data);

        // Gui du lieu goc ve server
        service.submitData(sCode, qCode, result);

        System.out.println(Arrays.toString(result));
    }
}
