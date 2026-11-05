
package RMI.Character;

import RMI.CharacterService;
import java.rmi.registry.*;
import java.util.*;

public class demVaGhep {
    public static void main(String[] args) throws Exception {
        String server = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(server, 1099);
        CharacterService service = (CharacterService)
                registry.lookup("RMICharacterService");

        String data = service.requestCharacter(sCode, qCode);

        Map<Character, Integer> map = new LinkedHashMap<>();

        for (char c : data.toCharArray()) {
            map.put(c, map.getOrDefault(c, 0) + 1);
        }

        String result = "";

        for (char c : map.keySet()) {
            result += c + String.valueOf(map.get(c));
        }

        service.submitCharacter(sCode, qCode, result);
        System.out.println(result);
    }
}
