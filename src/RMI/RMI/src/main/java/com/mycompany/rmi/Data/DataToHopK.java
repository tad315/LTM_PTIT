/*Sử dụng thuật toán sinh tổ hợp để tạo ra tất cả các tổ hợp 
kích thước K của tập hợp đã cho.*/
package com.mycompany.rmi.Data;
import java.util.*;
import RMI.DataService;
import java.rmi.registry.*;
public class DataToHopK {
    public static void generateCombinations(int[] numbers, int k, int start, List<Integer> current, List<List<Integer>> result) {
        if (current.size() == k) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < numbers.length; i++) {
            current.add(numbers[i]);
            generateCombinations(numbers, k, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }
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
            List<List<Integer>> combinations = new ArrayList<>();
            generateCombinations(numbers, k, 0, new ArrayList<>(), combinations);
            String result = combinations.toString();
            service.submitData(studentCode, qCode, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
