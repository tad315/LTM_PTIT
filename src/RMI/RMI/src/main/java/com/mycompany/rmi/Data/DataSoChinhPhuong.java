//Xác định tất cả các số chính phương nhỏ hơn N.
package com.mycompany.rmi.Data;
import java.util.*;
import RMI.DataService;
import java.rmi.registry.*;
public class DataSoChinhPhuong {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            DataService service = (DataService) registry.lookup("RMIDataService");
            String studentCode = "B23DCCN156";
            String qCode = "";
            Object receivedData = service.requestData(studentCode, qCode);
            int n = ((Number) receivedData).intValue();
            List<Integer> squares = new ArrayList<>();
            for (int i = 1; i * i < n; i++) {
                squares.add(i * i);
            }
            service.submitData(studentCode, qCode, squares);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

