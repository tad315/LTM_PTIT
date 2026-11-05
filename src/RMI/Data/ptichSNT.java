
package RMI.Data;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class ptichSNT {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        DataService service = (DataService)
                registry.lookup("RMIDataService");

        // Nhan N tu server
        long n = ((Number) service.requestData(sCode, qCode)).longValue();

        List<Long> result = new ArrayList<>();

        // Phan tich thua so nguyen to
        for (long i = 2; i <= n / i; i++) {
            while (n % i == 0) {
                result.add(i);
                n /= i;
            }
        }

        // Neu con thua so nguyen to lon hon 1
        if (n > 1) {
            result.add(n);
        }

        service.submitData(sCode, qCode, result);
    }
}
