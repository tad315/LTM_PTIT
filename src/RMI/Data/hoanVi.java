
package RMI.Data;

import RMI.DataService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;

public class hoanVi {

    public static void nextPermutation(int[] a) {
        int n = a.length;

        // Tim vi tri i sao cho a[i] < a[i+1]
        int i = n - 2;
        while (i >= 0 && a[i] >= a[i + 1]) {
            i--;
        }

        if (i >= 0) {
            // Tim phan tu lon hon a[i] tu ben phai
            int j = n - 1;
            while (a[j] <= a[i]) {
                j--;
            }

            // Hoan doi a[i] va a[j]
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }

        // Dao nguoc doan tu i+1 den cuoi
        int left = i + 1;
        int right = n - 1;

        while (left < right) {
            int temp = a[left];
            a[left] = a[right];
            a[right] = temp;
            left++;
            right--;
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

        // Chuyen chuoi thanh mang int
        String[] parts = data.split(",");
        int[] a = new int[parts.length];

        for (int i = 0; i < parts.length; i++) {
            a[i] = Integer.parseInt(parts[i].trim());
        }

        // Tim hoan vi ke tiep
        nextPermutation(a);

        // Chuyen ket qua thanh chuoi
        String answer = "";

        for (int i = 0; i < a.length; i++) {
            if (i > 0) {
                answer += ", ";
            }
            answer += a[i];
        }

        service.submitData(sCode, qCode, answer);

        System.out.println(answer);
    }
}
