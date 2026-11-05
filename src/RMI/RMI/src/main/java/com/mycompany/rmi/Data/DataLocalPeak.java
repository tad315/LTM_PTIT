/*Triệu gọi requestData(studentCode, qCode) 
để nhận một mảng số nguyên từ server. Ví dụ: [3,8,5,10,7].
Một phần tử là đỉnh cục bộ nếu lớn hơn phần tử liền kề bên trái
và bên phải; phần tử đầu/cuối chỉ cần lớn hơn phần tử kề duy nhất. 
Trả về vị trí theo chỉ số bắt đầu từ 1.
Triệu gọi submitData(studentCode, qCode, data) 
để gửi List<Integer> các vị trí đỉnh cục bộ. Ví dụ: [2,4].
*/
package com.mycompany.rmi.Data;
import java.util.*;
import java.rmi.registry.*;
import RMI.DataService;
public class DataLocalPeak {
    public static int[] convertToArray(Object data) {
        if (data instanceof int[]) {
            return (int[]) data;
        }
        if (data instanceof Integer[]) {
            Integer[] arr = (Integer[]) data;
            int[] result = new int[arr.length];
            for (int i = 0; i < arr.length; i++) {
                result[i] = arr[i];
            }
            return result;
        }
        if (data instanceof List<?>) {
            List<?> list = (List<?>) data;
            int[] result = new int[list.size()];
            for (int i = 0; i < list.size(); i++) {
                result[i] = ((Number) list.get(i)).intValue();
            }
            return result;
        }
        throw new IllegalArgumentException(
            "Kieu du lieu server tra ve khong hop le: "
            + data.getClass().getName()
        );
    }
    public static List<Integer> findLocalPeaks(int[] arr) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            boolean isPeak = true;
            if (i > 0 && arr[i] <= arr[i - 1]) {
                isPeak = false;
            }
            if (i < arr.length - 1 && arr[i] <= arr[i + 1]) {
                isPeak = false;
            }
            if (isPeak) {
                result.add(i + 1);
            }
        }
        return result;
    }
    public static void main(String[] args) {

        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            DataService service = (DataService) registry.lookup("RMIDataService");
            String studentCode = "B23DCCN156";
            String qCode = "cymdPKlS";
            Object data = service.requestData(studentCode, qCode);
            int[] arr = convertToArray(data);
            List<Integer> peaks = findLocalPeaks(arr);
            service.submitData(studentCode, qCode, peaks);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}