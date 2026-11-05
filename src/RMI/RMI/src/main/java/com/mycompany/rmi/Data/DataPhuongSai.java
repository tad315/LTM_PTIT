/*Tính toán phương sai (variance) và độ lệch chuẩn (standard deviation) của tập hợp*/
package com.mycompany.rmi.Data;
import java.util.*;
import java.rmi.registry.*;
import RMI.DataService;
public class DataPhuongSai {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            DataService service = (DataService) registry.lookup("RMIDataService");
            String studentCode = "B23DCCN156";
            String qCode = "";
            Object receivedData = service.requestData(studentCode, qCode);
            String data = receivedData.toString();
            String[] elements = data.split(",");
            double[] numbers = new double[elements.length];
            for (int i = 0; i < elements.length; i++) {
                numbers[i] = Double.parseDouble(elements[i].trim());
            }
            double sum = 0;
            for (double x : numbers) {
                sum += x;
            }
            double mean = sum / numbers.length;
            double variance = 0;
            for (double x : numbers) {
                variance += Math.pow(x - mean, 2);
            }
            variance = variance / numbers.length;
            double standardDeviation = Math.sqrt(variance);
            variance = Math.round(variance * 100.0) / 100.0;
            standardDeviation = Math.round(standardDeviation * 100.0) / 100.0;
            String result = String.format(Locale.US, "%.2f : %.2f", variance, standardDeviation);
            service.submitData(studentCode, qCode, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
