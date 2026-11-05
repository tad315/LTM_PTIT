/*Thực hiện phân chia mảng byte[] nhận được thành hai phần: 
phần đầu chứa các byte có giá trị chẵn và phần sau chứa các 
byte có giá trị lẻ, duy trì thứ tự xuất hiện của các phần tử 
trong từng nhóm. */
package com.mycompany.rmi.Byte;
import java.util.*;
import java.rmi.registry.*;
import RMI.ByteService;
public class ByteEvenOdd {
    public static byte[] rearrangeEvenOdd(byte[] data) {
        ArrayList<Byte> even = new ArrayList<>();
        ArrayList<Byte> odd = new ArrayList<>();
        for (byte b : data) {
            if (b % 2 == 0) { even.add(b);
            } else { odd.add(b);
            }
        }
        byte[] result = new byte[data.length];
        int index = 0;
        for (byte b : even) {
            result[index++] = b;
        }
        for (byte b : odd) {
            result[index++] = b;
        }
        return result;
    }
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ByteService service = (ByteService) registry.lookup("RMIByteService");
            String studentCode = "B23DCCN156";
            String qCode = "";
            byte[] data = service.requestData(studentCode, qCode);
            byte[] result = rearrangeEvenOdd(data);
            service.submitData(studentCode, qCode, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
