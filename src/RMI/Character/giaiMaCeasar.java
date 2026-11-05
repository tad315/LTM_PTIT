
package RMI.Character;

import RMI.CharacterService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class giaiMaCeasar {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        CharacterService service = (CharacterService)
                registry.lookup("RMICharacterService");

        // Nhan chuoi tu server
        String data = service.requestCharacter(sCode, qCode);

        int shift = data.length() % 7;

        String result = "";

        for (char c : data.toCharArray()) {

            if (c >= 'A' && c <= 'Z') {
                c = (char) ('A' + (c - 'A' - shift + 26) % 26);
            }
            else if (c >= 'a' && c <= 'z') {
                c = (char) ('a' + (c - 'a' - shift + 26) % 26);
            }

            result += c;
        }

        service.submitCharacter(sCode, qCode, result);

        System.out.println("Shift: " + shift);
        System.out.println("Result: " + result);
    }
}
