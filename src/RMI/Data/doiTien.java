package RMI.Data;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class doiTien {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        DataService service = (DataService)
                registry.lookup("RMIDataService");

        // Nhan so tien
        int amount = (Integer) service.requestData(sCode, qCode);

        int[] coins = {10, 5, 2, 1};

        List<Integer> result = new ArrayList<>();

        // Chon dong xu lon nhat co the
        for (int coin : coins) {
            while (amount >= coin) {
                result.add(coin);
                amount -= coin;
            }
        }

        // Tao chuoi gui server
        String answer = result.size() + "; " +
                String.join(",", result.stream()
                        .map(String::valueOf)
                        .toArray(String[]::new));

        service.submitData(sCode, qCode, answer);

        System.out.println(answer);
    }
}
