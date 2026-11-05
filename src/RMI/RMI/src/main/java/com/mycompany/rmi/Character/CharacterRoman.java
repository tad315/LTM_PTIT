/*Chuyển đổi số thập phân nhận được thành chuỗi số La Mã (Roman).
Quy tắc chuyển đổi:
Các ký tự La Mã chính bao gồm: I=1, V=5, X=10, L=50, C=100, D=500, M=1000.*/
package com.mycompany.rmi.Character;
import java.util.*;
import java.io.*;
import java.rmi.registry.*;
import RMI.CharacterService;
public class CharacterRoman {
    public static String toRoman(int number){
       int[] value = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};
       String[] symbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
       StringBuilder result = new StringBuilder();
       for (int i=0; i< value.length; i++){
           while (number >= value[i]){
           result.append(symbols[i]);
           number -= value[i];
           }
       }
       return result.toString();
    }
    public static void main(String[] args) {
        try{
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            CharacterService service = (CharacterService) registry.lookup("RMICharacterService");
            String studentCode = "B23DCCN156";
            String qCode = "Xy9vDJrZ";
            String data = service.requestCharacter(studentCode, qCode);
            int number = Integer.parseInt(data.trim());
            String roman = toRoman(number);
            System.out.println(roman);
            service.submitCharacter(studentCode, qCode, roman);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
