package WS.Character;

import SOAP.generated.CharacterService;
import SOAP.generated.SoapCharacterService;

import java.util.List;

public class YvwDtnm9 {
    public static void main(String[] args) {
        String sCode = "B23CCN230";
        String qCode = "YvwDtnm9";

        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();

        List<String> data = port.requestStringArray(sCode, qCode);


    }

    public static int priority (String s) {
        if (s.startsWith("ERROR")) return 0;
        if (s.startsWith("WARN")) return 1;
        if
    }
}
