package com.mycompany.rmi.Character;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
public class MaHoaURL {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = "MÃ_CÂU_HỎI"; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            CharacterService service = (CharacterService)registry.lookup("RMICharacterService");
            String data = service.requestCharacter(studentCode, qCode);
            String encodedString = URLEncoder.encode(data, StandardCharsets.UTF_8).replace("+", "%20");
            service.submitCharacter(studentCode,qCode,encodedString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}