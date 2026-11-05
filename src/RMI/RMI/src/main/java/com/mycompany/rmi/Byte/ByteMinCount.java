/*Tìm phần tử có số lần xuất hiện ít nhất trong mảng, 
nếu có nhiều phần tử có cùng số lần xuất hiện ít nhất, 
chỉ cần trả về phần tử đầu tiên xuất hiện trong các phần tử đó*/
package com.mycompany.rmi.Byte;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.Map;
import RMI.ByteService;

public class ByteMinCount {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");

            ByteService service = (ByteService) registry.lookup("RMIByteService");

            String studentCode = "B23DCCN156";
            String qCode = "aEEkypOG";

            byte[] data = service.requestData(studentCode, qCode);

            Map<Byte, Integer> map = new HashMap<>();

            for (byte b : data) {
                map.put(b, map.getOrDefault(b, 0) + 1);
            }

            byte minElement = data[0];
            int minCount = map.get(minElement);

            for (byte b : data) {
                if (map.get(b) < minCount) {
                    minElement = b;
                    minCount = map.get(b);
                }
            }

            byte[] result = {
                minElement,
                (byte) minCount
            };

            service.submitData(studentCode, qCode, result);

            System.out.println("Da gui ket qua thanh cong!");
            System.out.println("Phan tu: " + minElement);
            System.out.println("So lan xuat hien: " + minCount);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
