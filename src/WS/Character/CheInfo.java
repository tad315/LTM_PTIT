package WS.Character;

import SOAP.generated.CharacterService;
import SOAP.generated.SoapCharacterService;

import java.util.ArrayList;
import java.util.List;

public class CheInfo {
    public static void main(String[] args) {
        String sCode = "B23DCCN230";
        String qCode = "YvwDtnm9";

        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();

        List<String> data = port.requestStringArray(sCode, qCode);
        List<String> res = new ArrayList<>();

        for (String s : data) {
            s = s.replaceAll("(?i)(email=)\\S+", "$1[EMAIL]");

            s = s.replaceAll("(?i)(phone=)\\S+", "$1[PHONE]");

            s = s.replaceAll("(?i)(token=)\\S+", "$1[TOKEN]");

            res.add(s);
        }

        res.sort((a, b) -> Integer.compare(priority(a), priority(b)));

        port.submitStringArray(sCode, qCode, res);
    }

    public static int priority (String s) {
        if (s.startsWith("ERROR")) return 0;
        if (s.startsWith("WARN")) return 1;
        if (s.startsWith("INFO")) return 2;
        return 3;
    }
}
