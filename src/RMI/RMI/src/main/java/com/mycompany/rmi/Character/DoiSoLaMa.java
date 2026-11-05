//Thực hiện chuyển đổi chuỗi số La Mã nhận được thành số thập phân (Decimal)
package com.mycompany.rmi.Character;
import RMI.CharacterService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class DoiSoLaMa {
    public static int romanToDecimal(String roman) {
        int result = 0;
        for (int i = 0; i < roman.length(); i++) {
            int current = romanValue(roman.charAt(i));
            if (i + 1 < roman.length()) {
                int next = romanValue(roman.charAt(i + 1));
                if (current < next) {
                    result -= current;
                } else {
                    result += current;
                }
            } else {
                result += current;
            }
        }
        return result;
    }
    public static int romanValue(char c) {
        switch (c) {
            case 'I':
                return 1;
            case 'V':
                return 5;
            case 'X':
                return 10;
            case 'L':
                return 50;
            case 'C':
                return 100;
            case 'D':
                return 500;
            case 'M':
                return 1000;
            default:
                throw new IllegalArgumentException(
                        "Ky tu La Ma khong hop le: " + c
                );
        }
    }
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            CharacterService service = (CharacterService)registry.lookup("RMICharacterService");
            String data = service.requestCharacter(studentCode, qCode);
            int result = romanToDecimal(data);
            String decimalString = String.valueOf(result);
            service.submitCharacter(studentCode,qCode,decimalString);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}