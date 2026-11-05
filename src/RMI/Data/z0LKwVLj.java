package RMI.Data;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Locale;

public class z0LKwVLj {
    public static void main(String[] args) {
        String sv = "36.50.135.242";
        int port = 1099;

        String sCode = "B23DCCN230";
        String qCode = "z0LKwVLj";

        try {
            Registry registry = LocateRegistry.getRegistry(sv, port);
            DataService service = (DataService) registry.lookup("RMIDataService");

            String data = (String) service.requestData(sCode, qCode);

            String[] parts = data.trim().split(",");
            int n = parts.length;
            double[] a = new double[n];
            double sum = 0;

            for (int i = 0; i < n; i++) {
                a[i] = Double.parseDouble(parts[i].trim());
                sum += a[i];
            }

            double avg = sum / n;

            // lech chuan
            double variance = 0;
            for (int i = 0; i < n; i++) {
                variance += Math.pow(a[i] - avg, 2);
            }

            double stddev = Math.sqrt(variance / n);

            Arrays.sort(a);

            int idx = (int) Math.ceil(n * 0.95) - 1;
            double p95 = a[idx];

            String res = String.format(Locale.US, "average=%.2f;stddev=%.2f;p95=%.2f", avg, stddev, p95);
            service.submitData(sCode, qCode, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
