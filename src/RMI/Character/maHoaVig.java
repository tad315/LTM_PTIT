
package RMI.Character;

import RMI.CharacterService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class maHoaVig {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        CharacterService service = (CharacterService)
                registry.lookup("RMICharacterService");

        String data = service.requestCharacter(sCode, qCode);

        // Tach tu khoa va chuoi
        String[] parts = data.split(";", 2);

        String key = parts[0].toUpperCase();
        String str = parts[1].toUpperCase();

        String result = "";

        for (int i = 0; i < str.length(); i++) {

            char c = str.charAt(i);
            char k = key.charAt(i % key.length());

            int shift = k - 'A';

            if (c >= 'A' && c <= 'Z') {
                char encrypted = (char)
                        ('A' + (c - 'A' + shift) % 26);

                result += encrypted;
            } else {
                result += c;
            }
        }

        service.submitCharacter(sCode, qCode, result);

        System.out.println(result);
    }
}
