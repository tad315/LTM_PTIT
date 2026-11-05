/*Chuyển đổi mỗi byte trong mảng dữ liệu nhận được thành chuỗi 
biểu diễn số bát phân (octal). Mỗi byte sẽ được chuyển thành 
một chuỗi số bát phân gồm ba chữ số (nếu cần, thêm các chữ số 
0 ở đầu để đảm bảo đúng định dạng). */
package com.mycompany.rmi.Byte;
import java.util.*;
import java.nio.charset.StandardCharsets;
import java.rmi.registry.*;
import RMI.ByteService;
public class ByteToOctal {
    public static String bytesToOctal(byte[] data) {
        StringBuilder octal = new StringBuilder();
        for (byte b : data) {
            int value = b&0xFF;
            octal.append(String.format("%03o", value));
        }
        return octal.toString();
    }
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ByteService service = (ByteService) registry.lookup("RMIByteService");
            String studentCode = "B23DCCN156";
            String qCode = "";
            byte[] data = service.requestData(studentCode, qCode);
            String octal = bytesToOctal(data);
            byte[] result = octal.getBytes(StandardCharsets.US_ASCII);
            service.submitData(studentCode, qCode, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
