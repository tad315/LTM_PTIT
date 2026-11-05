//
//package RMI.Character;
//
//import RMI.CharacterService;
//import com.google.gson.*;
//
//import java.rmi.registry.*;
//import java.util.*;
//
//public class tachJSON {
//    public static void main(String[] args) throws Exception {
//        String server = "36.50.135.242";
//        String sCode = "B23DCCN230";
//        String qCode = "MA_CAU_HOI";
//
//        Registry registry = LocateRegistry.getRegistry(server, 1099);
//        CharacterService service = (CharacterService)
//                registry.lookup("RMICharacterService");
//
//        String data = service.requestCharacter(sCode, qCode);
//
//        JsonObject json = JsonParser.parseString(data).getAsJsonObject();
//
//        StringJoiner even = new StringJoiner(", ");
//        StringJoiner odd = new StringJoiner(", ");
//
//        int i = 0;
//
//        for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
//            String key = entry.getKey();
//            JsonElement value = entry.getValue();
//
//            String text = key + ": " +
//                    (value.isJsonPrimitive() && value.getAsJsonPrimitive().isString()
//                            ? value.getAsString()
//                            : value.toString());
//
//            if (i % 2 == 0) {
//                even.add(text);
//            } else {
//                odd.add(text);
//            }
//
//            i++;
//        }
//
//        String result = even + "; " + odd;
//
//        service.submitCharacter(sCode, qCode, result);
//        System.out.println(result);
//    }
//}
