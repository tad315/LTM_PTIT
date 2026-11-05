
package RMI.Byte;

import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

public class byteSangHex {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI_5";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        ByteService service = (ByteService)
                registry.lookup("RMIByteService");

        byte[] data = service.requestData(sCode, qCode);

        String hex = HexFormat.of().formatHex(data);

        byte[] result = hex.getBytes(StandardCharsets.US_ASCII);

//        String octal = "";
//
//        for (byte b : data) {
//            octal += String.format("%03o", Byte.toUnsignedInt(b));
//        }
//
//        byte[] result = octal.getBytes(StandardCharsets.US_ASCII);

        service.submitData(sCode, qCode, result);

        System.out.println("Hex: " + hex);
    }
}
