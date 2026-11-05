/*Thực hiện giải mã dữ liệu nhận được từ định dạng Base64 về 
dữ liệu gốc. Sử dụng giải mã Base64 để chuyển mảng byte Base64 
thành mảng byte nguyên bản ban đầu.
 */
package com.mycompany.rmi.Byte;
import java.util.*;
import java.rmi.registry.*;
import RMI.ByteService;
public class ByteBase64 {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ByteService service = (ByteService) registry.lookup("RMIByteService");
            String studentCode = "B23DCCN156";
            String qCode = "";
            byte[] data = service.requestData(studentCode, qCode);
            byte[] result = Base64.getDecoder().decode(data);
            service.submitData(studentCode, qCode, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
