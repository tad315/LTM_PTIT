package com.mycompany.rmi.Object;
import RMI.Employee;
import RMI.ObjectService;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class EmployeeClient {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ObjectService service = (ObjectService)registry.lookup("RMIObjectService");
            Serializable receivedObject = service.requestObject(studentCode, qCode);
            Employee employee = (Employee) receivedObject;
            int years = employee.getExperienceYears();
            if (years < 0) {
                throw new IllegalArgumentException(
                        "Experience years khong duoc am!"
                );
            }
            int sumDigits = sumOfDigits(years);
            int divisorCount = countDivisors(years);
            double factor = (years + sumDigits + divisorCount) / 100.0;
            double finalSalary = employee.getBaseSalary() * (1 + factor);
            employee.setFinalSalary(finalSalary);
            service.submitObject(studentCode, qCode, employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Hàm tính tổng các chữ số
    public static int sumOfDigits(int n) {
        int sum = 0;

        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }

        return sum;
    }

    // Hàm đếm số lượng ước số
    public static int countDivisors(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(
                    "Experience years phai lon hon 0!"
            );
        }

        int count = 0;

        for (int i = 1; i <= n; i++) {
            if (n % i == 0) {
                count++;
            }
        }

        return count;
    }
}