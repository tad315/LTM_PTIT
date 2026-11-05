/*Tìm phần tử xuất hiện nhiều nhất trong mảng byte[]. 
Nếu có nhiều phần tử có cùng số lần xuất hiện cao nhất, 
chỉ cần trả về phần tử đầu tiên xuất hiện trong các phần tử đó.*/
package com.mycompany.rmi.Byte;
import java.util.*;
import java.rmi.registry.*;
import RMI.ByteService;
public class ByteMaxCount {
    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ByteService service = (ByteService) registry.lookup("RMIByteService");
            String studentCode = "B23DCCN156";
            String qCode = "hcN6bYQs";
            byte[] data = service.requestData(studentCode, qCode);
            Map<Byte, Integer> map = new HashMap<>();
            for (byte b : data){
                map.put(b, map.getOrDefault(b, 0)+1);
            }
            byte maxElement = data[0];
            int maxCount = map.get(maxElement);
            for (byte b : data){
                if (map.get(b) > maxCount){
                    maxElement = b;
                    maxCount = map.get(b);
                }
            }
            byte[] result = {
                maxElement,
                (byte) maxCount
            };
            service.submitData(studentCode, qCode, result);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
