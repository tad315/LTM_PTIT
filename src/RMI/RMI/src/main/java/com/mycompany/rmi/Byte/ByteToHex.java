/*Chuyển đổi mảng dữ liệu nhị phân nhận được thành một chuỗi 
biểu diễn hex. Mỗi byte trong mảng sẽ được chuyển đổi thành hai 
ký tự hex tương ứng. */
package com.mycompany.rmi.Byte;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.rmi.registry.*;
import RMI.ByteService;
public class ByteToHex {
    public static String bytesToHex(byte[] data) {
        StringBuilder hex = new StringBuilder();
        for (byte b : data) {
            hex.append(String.format("%02x", b & 0xFF));
        }
        return hex.toString();
    }
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ByteService service = (ByteService) registry.lookup("RMIByteService");
            String studentCode = "B23DCCN156";
            String qCode = "";
            byte[] data = service.requestData(studentCode, qCode);
            String hex = bytesToHex(data);
            byte[] result = hex.getBytes(StandardCharsets.US_ASCII);
            service.submitData(studentCode, qCode, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
