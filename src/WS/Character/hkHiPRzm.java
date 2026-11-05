package WS.Character;

import SOAP.generated.CharacterService;
import SOAP.generated.SoapCharacterService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class hkHiPRzm {
    public static void main(String[] args) {
        String sCode = "B23DCCN230";
        String qCode = "hkHiPRzm";

        CharacterService service = new CharacterService();
        SoapCharacterService port = service.getSoapCharacterServicePort();

        List<Integer> data = port.requestCharacter(sCode, qCode);

        int n = data.size();
        int k = data.get(0);
        k = k % n;

        List<Integer> res = new ArrayList<>(data);
        Collections.rotate(res, k);

        port.submitCharacterCharArray(sCode, qCode, res);
    }
}
