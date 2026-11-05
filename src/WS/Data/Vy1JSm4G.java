package WS.Data;

import SOAP.generated.DataService;
import SOAP.generated.SoapDataService;

import java.util.List;

public class Vy1JSm4G {
    public static void main(String[] args) {
        String sCode = "B23DCCN230";
        String qCode = "Vy1JSm4G";

        DataService service = new DataService();
        SoapDataService port = service.getSoapDataServicePort();

        List<Integer> data = port.getData(sCode, qCode);

        int sum = 0;
        for (Integer n : data) {
            sum += n;
        }
        port.submitDataInt(sCode, qCode, sum);
    }
}
