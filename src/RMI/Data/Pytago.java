package RMI.Data;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Pytago {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        DataService service = (DataService)
                registry.lookup("RMIDataService");

        int n = (Integer) service.requestData(sCode, qCode);

        List<List<Integer>> result = new ArrayList<>();

        for (int a = 1; a <= n; a++) {
            for (int b = a + 1; b <= n; b++) {

                long sum = (long) a * a + (long) b * b;
                int c = (int) Math.sqrt(sum);

                if (c <= n && (long) c * c == sum) {
                    result.add(Arrays.asList(a, b, c));
                }
            }
        }

        service.submitData(sCode, qCode, result);

        System.out.println(result);
    }
}
