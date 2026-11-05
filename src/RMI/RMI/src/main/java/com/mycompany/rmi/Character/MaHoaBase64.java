package com.mycompany.rmi.Character;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Base64;
import java.nio.charset.StandardCharsets;
public class MaHoaBase64 {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            CharacterService service = (CharacterService)registry.lookup("RMICharacterService");
            String data = service.requestCharacter(studentCode, qCode);
            String encodedString = Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
            service.submitCharacter(studentCode, qCode, encodedString
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}