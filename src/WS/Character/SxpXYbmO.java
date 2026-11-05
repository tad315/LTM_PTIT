package WS.Character;

import SOAP.generated.CharacterService;
import SOAP.generated.SoapCharacterService;
import SOAP.generated.SoapDataService;

import java.util.Collections;

public class SxpXYbmO {
    public static void main(String[] args) {
        String sCode = "B23DCCN230";
        String qCode = "SxpXYbmO";

        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();

        String data = port.requestString(sCode, qCode);

        String res = new StringBuilder(data).reverse().toString();

        port.submitString(sCode, qCode, res);
    }
}
