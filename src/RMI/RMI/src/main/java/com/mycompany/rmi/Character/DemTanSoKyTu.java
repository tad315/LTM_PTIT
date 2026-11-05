/*Thực hiện thao tác đếm tần số xuất hiện của từng ký tự trong chuỗi đầu vào. 
Kết quả trả về là danh sách các ký tự kèm theo số lần xuất hiện của mỗi ký tự.
Vi du: Kết quả đếm tần số ký tự: {"H": 1, "e": 1, "l": 3, "o": 2, " ": 1, "w": 1, "r": 1, "d": 1}.*/
package com.mycompany.rmi.Character;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;
public class DemTanSoKyTu {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            CharacterService service = (CharacterService)registry.lookup("RMICharacterService");
            String data = service.requestCharacter(studentCode, qCode);
            Map<Character, Integer> frequency = new LinkedHashMap<>();
            for (int i = 0; i < data.length(); i++) {
                char c = data.charAt(i);
                frequency.put(c, frequency.getOrDefault(c, 0) + 1);
            }
            StringBuilder result = new StringBuilder("{");
            int count = 0;
            for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
                if (count > 0) {
                    result.append(", ");
                }
                result.append("\"").append(entry.getKey()).append("\": ").append(entry.getValue());

                count++;
            }
            String submitData = result.toString();
            service.submitCharacter(studentCode,qCode,submitData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}