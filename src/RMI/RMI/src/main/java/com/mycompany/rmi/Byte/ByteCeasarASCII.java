/*Thực hiện mã hóa Caesar cho mảng dữ liệu nhị phân bằng cách dịch 
chuyển mỗi byte trong mảng đi một số bước cố định trong bảng mã ASCII. 
Số bước dịch chuyển là số ký tự ASCII trong mảng dữ liệu.
*/
package com.mycompany.rmi.Byte;
import java.util.*;
import java.rmi.registry.*;
import RMI.ByteService;
public class ByteCeasarASCII {
    public static byte[] encryptCaesar(byte[] data, int shift) {
        byte[] result=new byte[data.length];
        for (int i=0; i<data.length; i++) {
            int value = data[i];
            if (value >= 'A' && value <= 'Z') {
                result[i] = (byte)('A'+(value-'A'+shift)%26);
            }
            else if (value>='a' && value<='z') {
                result[i] = (byte)('a'+(value-'a'+shift)%26);
            }
            else {
                result[i]=data[i];
            }
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
            byte[] encryptedData = encryptCaesar(data, 5);
            service.submitData(studentCode, qCode, encryptedData);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
