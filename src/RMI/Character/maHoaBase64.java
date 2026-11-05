
package RMI.Character;

import RMI.CharacterService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class maHoaBase64 {
    public static void main(String[] args) throws Exception {

        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        Registry registry = LocateRegistry.getRegistry(sv, 1099);

        CharacterService service = (CharacterService)
                registry.lookup("RMICharacterService");

        // Nhan chuoi
        String data = service.requestCharacter(sCode, qCode);

        // Ma hoa Base64
        String result = Base64.getEncoder()
                .encodeToString(data.getBytes(StandardCharsets.UTF_8));

        // Gui server
        service.submitCharacter(sCode, qCode, result);

        System.out.println(result);
    }
}
