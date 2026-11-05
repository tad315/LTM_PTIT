/*Thực hiện đếm tần số xuất hiện của mỗi ký tự trong chuỗi đầu vào 
và tạo ra chuỗi kết quả theo định dạng <Ký tự><Số lần xuất hiện>, 
sắp xếp theo thứ tự xuất hiện của các ký tự trong chuỗi.*/
package com.mycompany.rmi.Character;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.LinkedHashMap;
import java.util.Map;
public class TanSoKyTu {
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
            StringBuilder result = new StringBuilder();
            for (Map.Entry<Character, Integer> entry : frequency.entrySet()) {
                result.append(entry.getKey());
                result.append(entry.getValue());
            }
            String submitData = result.toString();
            service.submitCharacter(studentCode,qCode,submitData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}