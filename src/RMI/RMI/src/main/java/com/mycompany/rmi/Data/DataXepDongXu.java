/*Sử dụng thuật toán xếp đồng xu với các mệnh giá cố định 
để xác định số lượng đồng xu tối thiểu cần thiết để đạt được số tiền amount.*/
package com.mycompany.rmi.Data;
import java.util.*;
import RMI.DataService;
import java.rmi.registry.*;
public class DataXepDongXu {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = "";
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            DataService service = (DataService) registry.lookup("RMIDataService");
            Object receivedData = service.requestData(studentCode, qCode);
            int amount = ((Number) receivedData).intValue();
            int[] coins = {10, 5, 2, 1};
            List<Integer> result = new ArrayList<>();
            int remaining = amount;
            for (int coin : coins) {
                while (remaining >= coin) {
                    result.add(coin);
                    remaining -= coin;
                }
            }
            String submitData;
            if (remaining != 0 || amount < 0) {
                submitData = "-1";
            } else {
                String coinList = result.toString().replace("[", "").replace("]", "").replace(" ", "");
                submitData = result.size() + "; " + coinList;
            }
            System.out.println("Ket qua: " + submitData);
            service.submitData(studentCode, qCode, submitData);
            System.out.println("Da gui ket qua thanh cong!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
