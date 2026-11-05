/*Tìm phần tử lớn thứ K trong mảng byte[] với K là 
phần tử cuối cùng của mảng.*/
package com.mycompany.rmi.Byte;
import java.util.*;
import java.rmi.registry.*;
import RMI.ByteService;
public class ByteKLargest {
    public static int findKthLargest(byte[] data, int k) {
        int n = data.length-1;
        if (k<1 || k>n) {
            throw new IllegalArgumentException("Gia tri K khong hop le!");
        }
        Integer[] arr = new Integer[n];

        for (int i=0; i<n; i++) {
            arr[i]=data[i]&0xFF;
        }
        Arrays.sort(arr, (a, b) -> Integer.compare(b, a));
        return arr[k - 1];
    }
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ByteService service = (ByteService) registry.lookup("RMIByteService");
            String studentCode = "B23DCCN156";
            String qCode = "";
            byte[] data = service.requestData(studentCode, qCode);
            int k = data[data.length-1] & 0xFF;
            int klargest = findKthLargest(data, k);
            byte[] result = {(byte)klargest, (byte)k};
            service.submitData(studentCode, qCode, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
