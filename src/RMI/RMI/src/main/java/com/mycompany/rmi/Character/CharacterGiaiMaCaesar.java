/*Thực hiện thao tác giải mã Caesar (mã hóa chuỗi bằng cách dịch từng ký tự đi 
một số lượng vị trí nhất định)*/
package com.mycompany.rmi.Character;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class CharacterGiaiMaCaesar {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
            String data = service.requestCharacter(studentCode, qCode);
            int k = data.length() % 7;
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < data.length(); i++) {
                char c = data.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    c = (char) ('A' + (c - 'A' - k + 26) % 26);
                } else if (c >= 'a' && c <= 'z') {
                    c = (char) ('a' + (c - 'a' - k + 26) % 26);
                }
                result.append(c);
            }
            String decodedString = result.toString();
            service.submitCharacter(studentCode, qCode, decodedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}