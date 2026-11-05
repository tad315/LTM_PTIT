
package RMI.Character;

import RMI.CharacterService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class soLaMa2 {

    public static int value(char c) {
        switch (c) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        String server = "36.50.135.242";
        int port = 1099;
        String sCode = "B23DCCN230";
        String qCode = "MA_CAU_HOI";

        try {
            Registry registry = LocateRegistry.getRegistry(server, port);

            CharacterService service = (CharacterService)
                    registry.lookup("RMICharacterService");

            String data = service.requestCharacter(sCode, qCode);

            int result = 0;

            for (int i = 0; i < data.length(); i++) {
                int current = value(data.charAt(i));

                if (i + 1 < data.length()
                        && current < value(data.charAt(i + 1))) {
                    result -= current;
                } else {
                    result += current;
                }
            }

            service.submitCharacter(sCode, qCode, String.valueOf(result));

            System.out.println("Roman: " + data);
            System.out.println("Decimal: " + result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
