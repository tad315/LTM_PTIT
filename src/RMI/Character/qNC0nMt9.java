package RMI.Character;

import RMI.CharacterService;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class qNC0nMt9 {
    public static void main(String[] args) {
        String server = "36.50.135.242";
        int port = 1099;

        String sCode = "B23DCCN230";
        String qCode = "qNC0nMt9";

        try {
            Registry registry = LocateRegistry.getRegistry(server, port);
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");

            String data = service.requestCharacter(sCode, qCode);
            int n = Integer.parseInt(data.trim());

            int[] values = {
                    1000, 900, 500, 400,
                    100, 90, 50, 40,
                    10, 9, 5, 4, 1
            };

            String[] roman = {
                    "M", "CM", "D", "CD",
                    "C", "XC", "L", "XL",
                    "X", "IX", "V", "IV", "I"
            };

            String res = "";

            for (int i = 0; i < values.length; i++) {
                while (n >= values[i]) {
                    res += roman[i];
                    n -= values[i];
                }
            }

            service.submitCharacter(sCode, qCode, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
