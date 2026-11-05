package com.mycompany.rmi.Object;
import RMI.Student;
import RMI.ObjectService;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class StudentClient {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ObjectService service = (ObjectService)registry.lookup("RMIObjectService");
            Serializable receivedObject = service.requestObject(studentCode, qCode);
            Student student = (Student) receivedObject;
            String normalizedName = normalizeName(student.getName());
            String[] parts = normalizedName.split("\\s+");
            String firstName = parts[parts.length - 1].toUpperCase();
            StringBuilder initials = new StringBuilder();
            for (int i = 0; i < parts.length - 1; i++) {
                initials.append(parts[i].charAt(0));
            }

            String yearCode = String.format("%02d",Math.floorMod(student.getEnrollmentYear(), 100));
            String code = "B"+ yearCode+ firstName+ "_"+ initials.toString().toUpperCase();
            student.setName(normalizedName);
            student.setCode(code);
            service.submitObject(studentCode, qCode, student);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String normalizeName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Ten sinh vien khong hop le!");
        }
        String[] words = name.trim().toLowerCase().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(word.substring(0, 1).toUpperCase());
            result.append(word.substring(1));
        }
        return result.toString();
    }
}