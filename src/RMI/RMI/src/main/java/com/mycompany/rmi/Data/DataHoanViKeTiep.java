/*Sử dụng thuật toán sinh tổ hợp kế tiếp để tìm tổ hợp kế tiếp 
của chuỗi số này theo thứ tự từ điển. Nếu chuỗi đã là tổ hợp 
lớn nhất, trả về tổ hợp nhỏ nhất (sắp xếp lại từ đầu theo thứ tự từ điển).*/
package com.mycompany.rmi.Data;
import RMI.DataService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
public class DataHoanViKeTiep {
    public static void nextPermutation(int[] a) {
        int n = a.length;
        int i = n - 2;
        while (i >= 0 && a[i] >= a[i + 1]) {
            i--;
        }
        if (i < 0) {
            Arrays.sort(a);
            return;
        }
        int j = n - 1;
        while (a[j] <= a[i]) {
            j--;
        }
        int temp = a[i];
        a[i] = a[j];
        a[j] = temp;
        int left = i + 1;
        int right = n - 1;
        while (left < right) {
            temp = a[left];
            a[left] = a[right];
            a[right] = temp;
            left++;
            right--;
        }
    }
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            DataService service = (DataService) registry.lookup("RMIDataService");
            Object receivedData = service.requestData(studentCode, qCode);
            String data = receivedData.toString();
            String[] elements = data.split(",");
            int[] numbers = new int[elements.length];
            for (int i = 0; i < elements.length; i++) {
                numbers[i] = Integer.parseInt(elements[i].trim());
            }
            nextPermutation(numbers);
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < numbers.length; i++) {
                if (i > 0) {
                    result.append(", ");
                }
                result.append(numbers[i]);
            }
            service.submitData(studentCode, qCode, result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}