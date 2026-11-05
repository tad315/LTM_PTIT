
package WS.Character;

import SOAP.generated.CharacterService;
import SOAP.generated.SoapCharacterService;

import java.util.*;

public class OrvnUJ12 {
    public static void main(String[] args) {

        String sCode = "B23DCCN230";
        String qCode = "OrvnUJ12";

        CharacterService service = new CharacterService();
        SoapCharacterService port =
                service.getSoapCharacterServicePort();

        List<String> data =
                port.requestStringArray(sCode, qCode);

        Map<Integer, List<String>> map = new TreeMap<>();

        for (String w : data) {
            int cnt = 0;

            for (char c : w.toCharArray()) {
                if ("aeiouAEIOU".indexOf(c) != -1) {
                    cnt++;
                }
            }

            map.computeIfAbsent(cnt, k -> new ArrayList<>()).add(w);
        }

        List<String> res = new ArrayList<>();

        for (List<String> gr : map.values()) {
            Collections.sort(gr);

            // Theo định dạng code tham khảo
            res.add(String.join(",", gr));
        }

        System.out.println("Input: " + data);
        System.out.println("Result: " + res);

        port.submitCharacterStringArray(sCode, qCode, res);
    }
}
