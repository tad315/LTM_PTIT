package WS.Data;

import SOAP.generated.DataService;
import SOAP.generated.SoapDataService;

import java.util.ArrayList;
import java.util.List;

public class q0TORlTmx {
    public static void main(String[] args) {
        String sCode = "B23DCCN230";
        String qCode = "0TORlTmx";

        DataService service = new DataService();
        SoapDataService port = service.getSoapDataServicePort();
        List<Integer> data = port.getData(sCode, qCode);

        int c = 0, l = 0;

        for (Integer n : data) {
            if (n % 2 == 0) {
                c++;
            }
            else {
                l++;
            }
        }

        List<String> res = new ArrayList<>();
        res.add("EVEN=" + c);
        res.add("ODD=" + l);

        port.submitDataStringArray(sCode, qCode, res);
    }
}
