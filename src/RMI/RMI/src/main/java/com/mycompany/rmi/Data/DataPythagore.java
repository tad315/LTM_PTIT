/*Xác định tất cả các bộ ba số nguyên (a, b, c) sao cho a² + b² = c² và a < b < c ≤ N. 
Kết quả trả về là danh sách các bộ ba số Pythagore thỏa mãn yêu cầu. */
package com.mycompany.rmi.Data;
import java.util.*;
import RMI.DataService;
import java.rmi.registry.*;
public class DataPythagore {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = "";
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            DataService service = (DataService) registry.lookup("RMIDataService");
            Object receivedData = service.requestData(studentCode, qCode);
            int n = ((Number) receivedData).intValue();
            List<List<Integer>> result = new ArrayList<>();
            for (int a = 1; a <= n; a++) {
                for (int b = a + 1; b <= n; b++) {
                    for (int c = b + 1; c <= n; c++) {
                        if (a * a + b * b == c * c) {
                            List<Integer> triple = new ArrayList<>();
                            triple.add(a);
                            triple.add(b);
                            triple.add(c);
                            result.add(triple);
                        }
                    }
                }
            }
            service.submitData(studentCode, qCode, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
