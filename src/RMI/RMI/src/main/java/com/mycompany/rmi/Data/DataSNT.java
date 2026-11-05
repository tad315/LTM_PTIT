/*Thực hiện tìm tất cả các số nguyên tố trong khoảng từ 1 đến N*/
package com.mycompany.rmi.Data;
import java.util.*;
import RMI.DataService;
import java.rmi.registry.*;
public class DataSNT {
    public static boolean isPrime(int number) {
        if (number<2) {
            return false;
        }
        for (int i=2; i<=Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = "";
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            DataService service = (DataService) registry.lookup("RMIDataService");
            Object receivedData = service.requestData(studentCode, qCode);
            int n = ((Number) receivedData).intValue();
            List<Integer> primes = new ArrayList<>();
            for (int i = 2; i <= n; i++) {
                if (isPrime(i)) {
                    primes.add(i);
                }
            }
            service.submitData(studentCode, qCode, primes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}