//Xác định tất cả các số nguyên đối xứng (Palindrome Number) trong khoảng từ N đến K.
package com.mycompany.rmi.Data;
import java.util.*;
import RMI.DataService;
import java.rmi.registry.*;
public class DataSoDoiXung {
    public static boolean isPalindrome(int number) {
        int original = number;
        int reverse = 0;
        while (number > 0) {
            int digit = number % 10;
            reverse = reverse * 10 + digit;
            number /= 10;
        }
        return original == reverse;
    }
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = "";
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            DataService service = (DataService) registry.lookup("RMIDataService");
            Object receivedData = service.requestData(studentCode, qCode);
            int n;
            int k;
            if (receivedData instanceof int[]) {
                int[] values = (int[]) receivedData;
                n = values[0];
                k = values[1];
            } else if (receivedData instanceof Integer[]) {
                Integer[] values = (Integer[]) receivedData;
                n = values[0];
                k = values[1];
            } else if (receivedData instanceof List<?>) {
                List<?> values = (List<?>) receivedData;
                n = ((Number) values.get(0)).intValue();
                k = ((Number) values.get(1)).intValue();
            } else {
                throw new IllegalArgumentException("Du lieu server tra ve khong hop le!");
            }
            List<Integer> palindromes = new ArrayList<>();
            for (int i = n; i < k; i++) {
                if (isPalindrome(i)) {
                    palindromes.add(i);
                }
            }
            service.submitData(studentCode, qCode, palindromes);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
