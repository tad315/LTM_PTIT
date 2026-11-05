package com.mycompany.rmi.Character;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class MaHoaXOR {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
            String data = service.requestCharacter(studentCode, qCode);
            String[] parts = data.split(";", 2);
            String key = parts[0];
            String input = parts[1];
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < input.length(); i++) {
                char c = (char) (
                        input.charAt(i)
                        ^ key.charAt(i % key.length())
                );
                result.append(c);
            }
            String encryptedString = result.toString();
            service.submitCharacter(studentCode, qCode, encryptedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}