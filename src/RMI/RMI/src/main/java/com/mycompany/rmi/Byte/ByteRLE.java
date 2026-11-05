/*Thực hiện nén dữ liệu sử dụng thuật toán Run-Length Encoding(RLE) 
bằng cách ghi nhận mỗi byte và số lần lặp liên tiếp của nó. 
Kết quả nén là một mảng mới biểu diễn các cặp (byte, số lần lặp).*/
package com.mycompany.rmi.Byte;
import java.util.*;
import java.rmi.registry.*;
import RMI.ByteService;
public class ByteRLE {
    public static byte[] compressRLE(byte[] data) {
        ArrayList<Byte> result = new ArrayList<>();
        int i=0;
        while (i<data.length) {
            byte current=data[i];
            int count=1;
            while (i+1<data.length && data[i+1]==current && count<255) {
                count++;
                i++;
            }
            result.add(current);
            result.add((byte) count);
            i++;
        }
        byte[] compressed = new byte[result.size()];
        for (int j=0; j<result.size(); j++) {
            compressed[j]=result.get(j);
        }
        return compressed;
    }
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ByteService service = (ByteService) registry.lookup("RMIByteService");
            String studentCode = "B23DCCN156";
            String qCode = "";
            byte[] data = service.requestData(studentCode, qCode);
            byte[] result = compressRLE(data);
            service.submitData(studentCode, qCode, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
