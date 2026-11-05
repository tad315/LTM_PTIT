
package RMI.Byte;

import RMI.ByteService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class ptuLonThuK {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        ByteService service = (ByteService)
                registry.lookup("RMIByteService");

        byte[] data = service.requestData(sCode, qCode);

        // Lay K tu phan tu cuoi
        int k = Byte.toUnsignedInt(data[data.length - 1]);

        // Bo K ra khoi mang
        byte[] a = Arrays.copyOf(data, data.length - 1);

        // Sap xep tang dan
        Arrays.sort(a);

        // Tim phan tu lon thu K
        byte kth = a[a.length - k];

        // Gui ket qua
        byte[] result = {kth, (byte) k};

        service.submitData(sCode, qCode, result);

    }
}
