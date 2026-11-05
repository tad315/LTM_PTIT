/*Thực hiện phân rã số N thành các thừa số nguyên tố*/
package com.mycompany.rmi.Data;
import java.util.*;
import RMI.DataService;
import java.rmi.registry.*;
public class DataThuaSoNT {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            DataService service = (DataService) registry.lookup("RMIDataService");
            String studentCode = "B23DCCN156";
            String qCode = "";
            Object receivedData = service.requestData(studentCode, qCode);
            long n = ((Number) receivedData).longValue();
            List<Long> factors = new ArrayList<>();
            long temp = n;
            while (temp % 2 == 0) {
                factors.add(2L);
                temp /= 2;
            }
            for (long i = 3; i <= temp / i; i += 2) {
                while (temp % i == 0) {
                    factors.add(i);
                    temp /= i;
                }
            }
            if (temp > 1) {
                factors.add(temp);
            }
            service.submitData(studentCode, qCode, factors);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
