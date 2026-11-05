/* Thực hiện mã hóa XOR cho mảng dữ liệu nhị phân bằng cách 
sử dụng một khóa là chuỗi "PTIT". Thực hiện phép XOR trên từng 
byte trong mảng dữ liệu với byte tương ứng trong khóa (
khóa sẽ được lặp lại để khớp với độ dài của mảng dữ liệu).*/
package com.mycompany.rmi.Byte;
import java.util.*;
import java.rmi.registry.*;
import RMI.ByteService;
public class ByteXOR {
    public static byte[] encryptXOR(byte[] data, String key) {
        byte[] keyBytes = key.getBytes(
            java.nio.charset.StandardCharsets.US_ASCII
        );
        byte[] result = new byte[data.length];
        for (int i=0; i<data.length; i++) {
            result[i] = (byte)(data[i]^keyBytes[i%keyBytes.length]);
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
            byte[] encryData = encryptXOR(data, "PTIT");
            service.submitData(studentCode, qCode, encryData);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
