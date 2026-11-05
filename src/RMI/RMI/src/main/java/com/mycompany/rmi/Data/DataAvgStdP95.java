/*Tính giá trị trung bình, độ lệch chuẩn trên toàn bộ dãy và 
phân vị 95 tại vị trí ceil(n * 0.95) - 1 sau khi sắp xếp tăng dần.*/
package com.mycompany.rmi.Data;
import java.util.*;
import java.rmi.registry.*;
import RMI.DataService;
public class DataAvgStdP95 {
    public static double calculateAverage(double[] arr) {
        double sum = 0;
        for (double x : arr) {
            sum += x;
        }
        return sum / arr.length;
    }
    public static double calculateStdDev(double[] arr, double avg) {
        double sum = 0;
        for (double x : arr) {
            sum += Math.pow(x - avg, 2);
        }
        return Math.sqrt(sum / arr.length);
    }
    public static double calculateP95(double[] arr) {
        double[] sorted = arr.clone();
        Arrays.sort(sorted);
        int index = (int) Math.ceil(sorted.length * 0.95) - 1;
        return sorted[index];
    }
    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            DataService service = (DataService) registry.lookup("RMIDataService");
            String studentCode = "B23DCCN156";
            String qCode = "pcVzSaX2";
            Object data = service.requestData(studentCode, qCode);
            String csv = data.toString();
            String[] path = csv.split(",");
            double[] number = new double[path.length];
            for (int i=0; i<path.length; i++){
                number[i] = Double.parseDouble(path[i].trim());
            }
            double avg = calculateAverage(number);
            double std = calculateStdDev(number, avg);
            double p95 = calculateP95(number);
            String result = String.format(java.util.Locale.US, "average=%.2f;stddev=%.2f;p95=%.2f", avg, std, p95);
            service.submitData(studentCode, qCode, result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
