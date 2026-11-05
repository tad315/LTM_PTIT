/*tìm phần tử lớn thứ K trong mảng số nguyên đã cho*/
package com.mycompany.rmi.Data;
import java.util.*;
import RMI.DataService;
import java.rmi.registry.*;
public class DataKLargest {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            DataService service = (DataService) registry.lookup("RMIDataService");
            String studentCode = "B23DCCN156";
            String qCode = "";
            Object receivedData = service.requestData(studentCode, qCode);
            String data = receivedData.toString();
            String[] parts = data.split(";");
            String[] elements = parts[0].trim().split(",");
            int[] numbers = new int[elements.length];
            for (int i = 0; i < elements.length; i++) {
                numbers[i] = Integer.parseInt(elements[i].trim());
            }
            int k = Integer.parseInt(parts[1].trim());
            Arrays.sort(numbers);
            int result = numbers[numbers.length - k];
            service.submitData(studentCode, qCode, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
