package com.mycompany.rmi.Character;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class MaHoaVigenere {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
            String data = service.requestCharacter(studentCode, qCode);
            String[] parts = data.split(";", 2);
            String keyword = parts[0].trim().toUpperCase();
            String input = parts[1].trim().toUpperCase();
            StringBuilder result = new StringBuilder();
            int keyIndex = 0;
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    int shift = keyword.charAt(keyIndex % keyword.length()) - 'A';
                    char encrypted = (char) (
                            'A' + (c - 'A' + shift) % 26
                    );
                    result.append(encrypted);
                    keyIndex++;
                } else {
                    result.append(c);
                }
            }
            String encryptedString = result.toString();
            service.submitCharacter(studentCode, qCode, encryptedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}