/*Thực hiện thao tác mã hóa URL (URL Encoding) 
cho chuỗi đầu vào nhận được từ server. 
Mã hóa theo quy tắc phần đường dẫn của URI 
(tương đương new java.net.URI(null, null, s, null).toASCIIString()): 
*/
package com.mycompany.rmi.Character;
import java.util.*;
import java.net.URI;
import java.rmi.registry.*;
import RMI.CharacterService;
public class CharacterURLEncoding {
    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            CharacterService service = (CharacterService)registry.lookup("RMICharacterService");
            String studentCode = "B23DCCN156";
            String qCode = "awfwn4R6";
            String s = service.requestCharacter(studentCode, qCode);
            String encoded = new URI(null, null, s, null).toASCIIString();
            service.submitCharacter(studentCode, qCode, encoded);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
