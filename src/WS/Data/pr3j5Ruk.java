package WS.Data;

import SOAP.generated.DataService;
import SOAP.generated.SoapDataService;
import java.util.ArrayList;
import java.util.List;

public class pr3j5Ruk {
    public static void main(String[] args) {
        String sCode = "B23DCCN230";
        String qCode = "pr3j5Ruk";

        DataService service = new DataService();
        SoapDataService port = service.getSoapDataServicePort();

        List<Integer> data = port.getData(sCode, qCode);
        List<String> nums = new ArrayList<>();
        for (Integer n : data) {
            nums.add(String.valueOf(n));
        }

        nums.sort((a, b) -> (b + a).compareTo(a + b));

        StringBuilder sb = new StringBuilder();

        for (String s : nums) {
            sb.append(s);
        }

        String res = sb.toString();

        if (!res.isEmpty() && res.charAt(0) == '0') {
            res = "0";
        }
        port.submitDataString(sCode, qCode, res);
    }
}
