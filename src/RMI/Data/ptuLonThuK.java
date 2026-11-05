
package RMI.Data;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class ptuLonThuK {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        DataService service = (DataService)
                registry.lookup("RMIDataService");

        // Nhan chuoi tu server
        String data = (String) service.requestData(sCode, qCode);

        // Tach mang va K
        String[] parts = data.split(";");

        String[] numbers = parts[0].split(",");
        int k = Integer.parseInt(parts[1].trim());

        // Chuyen thanh mang int
        int[] a = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            a[i] = Integer.parseInt(numbers[i].trim());
        }

        // Sap xep tang dan
        Arrays.sort(a);

        // Tim phan tu lon thu K
        int result = a[a.length - k];

        // Gui ket qua ve server
        service.submitData(sCode, qCode, result);

        System.out.println("Result: " + result);
    }
}
