package RMI.Data;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class doiXung {
    public static boolean isPalindrome(int n) {
        String s = String.valueOf(n);
        return s.equals(new StringBuilder(s).reverse().toString());
    }

    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        DataService service = (DataService)
                registry.lookup("RMIDataService");

        // Gia su server tra ve int[] gom N va K
        int[] data = (int[]) service.requestData(sCode, qCode);

        int n = data[0];
        int k = data[1];

        List<Integer> result = new ArrayList<>();

        for (int i = n; i < k; i++) {
            if (isPalindrome(i)) {
                result.add(i);
            }
        }

        service.submitData(sCode, qCode, result);

        System.out.println(result);
    }
}
