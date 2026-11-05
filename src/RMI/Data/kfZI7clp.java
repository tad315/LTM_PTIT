package RMI.Data;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;

public class kfZI7clp {
    public static void main(String[] args) {
        String sv = "36.50.135.242";
        int port = 1099;

        String sCode = "B23DCCN230";
        String qCode = "kfZI7clp";

        try {
            Registry registry = LocateRegistry.getRegistry(sv, port);
            DataService service = (DataService) registry.lookup("RMIDataService");
            Object obj = service.requestData(sCode, qCode);

            int[] data = (int[]) obj;

            List<Integer> res = new ArrayList<>();

            for (int i = 0; i < data.length; i++) {
                boolean l = (i == 0 || data[i] > data[i - 1]);
                boolean r = (i == data.length - 1 || data[i] > data[i + 1]);
                if (l && r) {
                    res.add(i + 1);
                }
            }
            service.submitData(sCode, qCode, res);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
