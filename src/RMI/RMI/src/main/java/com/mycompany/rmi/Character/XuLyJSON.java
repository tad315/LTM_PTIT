/*Phân tích cú pháp chuỗi JSON nhận được và trích xuất các cặp key: value dựa trên vị trí của chúng*/
package com.mycompany.rmi.Character;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
public class XuLyJSON {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
            String data = service.requestCharacter(studentCode, qCode);
            JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
            List<String> evenList = new ArrayList<>();
            List<String> oddList = new ArrayList<>();
            int index = 0;
            for (Map.Entry<String, JsonElement> entry: jsonObject.entrySet()) {
                String key = entry.getKey();
                JsonElement value = entry.getValue();
                String valueString;
                if (value.isJsonPrimitive()) {
                    valueString = value.getAsString();
                } else {
                    valueString = value.toString();
                }
                String pair = key + ": " + valueString;
                if (index % 2 == 0) {
                    evenList.add(pair);
                } else {
                    oddList.add(pair);
                }
                index++;
            }
            String firstString = String.join(", ", evenList);
            String secondString = String.join(", ", oddList);
            String result = firstString + "; " + secondString;
            service.submitCharacter(studentCode,qCode,result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}