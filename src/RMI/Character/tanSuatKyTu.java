
package RMI.Character;

import RMI.CharacterService;
import java.rmi.registry.*;
import java.util.*;

public class tanSuatKyTu {
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

        StringJoiner sj = new StringJoiner(", ", "{", "}");

        for (char c : map.keySet()) {
            sj.add("\"" + c + "\": " + map.get(c));
        }

        String result = sj.toString();

        service.submitCharacter(sCode, qCode, result);
        System.out.println(result);
    }
}
