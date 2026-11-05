package RMI.Character;

import RMI.CharacterService;

import java.net.URI;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class jvhIYafA {
    public static void main(String[] args) {
        String server = "36.50.135.242";
        int port = 1099;
        String sCode = "B23DCCN230";
        String qCode = "jvhIYafA";

        try {
            Registry registry = LocateRegistry.getRegistry(server, port);
            CharacterService service =(CharacterService) registry.lookup("RMICharacterService");

            String data = service.requestCharacter(sCode, qCode);

            String res = new URI(null, null, data, null).toASCIIString();

            service.submitCharacter(sCode, qCode, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
