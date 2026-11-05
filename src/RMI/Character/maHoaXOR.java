
package RMI.Character;

import RMI.CharacterService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class maHoaXOR {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        CharacterService service = (CharacterService)
                registry.lookup("RMICharacterService");

        String data = service.requestCharacter(sCode, qCode);

        // Tach khoa va chuoi
        String[] parts = data.split(";", 2);

        String key = parts[0];
        String str = parts[1];

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {

            char c = str.charAt(i);
            char k = key.charAt(i % key.length());

            char encrypted = (char) (c ^ k);

            result.append(encrypted);
        }

        service.submitCharacter(sCode, qCode, result.toString());

        System.out.println(result);
    }
}
