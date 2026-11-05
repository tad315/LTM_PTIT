
package RMI.Data;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class toHop {

    static List<List<Integer>> result = new ArrayList<>();

    public static void backtrack(int[] a, int k,
                                 int start, List<Integer> temp) {

        if (temp.size() == k) {
            result.add(new ArrayList<>(temp));
            return;
        }

        for (int i = start; i < a.length; i++) {
            temp.add(a[i]);

            backtrack(a, k, i + 1, temp);

            temp.remove(temp.size() - 1);
        }
    }

    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        DataService service = (DataService)
                registry.lookup("RMIDataService");

        String data = (String) service.requestData(sCode, qCode);

        // Tach mang va K
        String[] parts = data.split(";");
        String[] numbers = parts[0].split(",");
        int k = Integer.parseInt(parts[1].trim());

        int[] a = new int[numbers.length];

        for (int i = 0; i < numbers.length; i++) {
            a[i] = Integer.parseInt(numbers[i].trim());
        }

        // Sinh cac to hop
        backtrack(a, k, 0, new ArrayList<>());

        // De yeu cau gui chuoi
        String answer = result.toString();

        service.submitData(sCode, qCode, answer);

        System.out.println(answer);
    }
}
