package WS.Data;

import SOAP.generated.DataService;
import SOAP.generated.SoapDataService;

import java.util.List;

public class q1yD0J9wV {
    public static void main(String[] args) {
        String sCode = "B23DCCN230";
        String qCode = "1yD0J9wV";

        DataService service = new DataService();
        SoapDataService port = service.getSoapDataServicePort();

        List<Integer> data = port.getData(sCode, qCode);

        int primeCnt = 0;
        long checkSum = 0;

        for (int i = 0; i < data.size(); i++) {
            int x = data.get(i);
            if (isPrime(x)) {
                primeCnt++;
            }
            checkSum = (checkSum + (long)(i + 1) * x) % 100000;
        }
        checkSum = (checkSum + 100000) % 100000;

        String res = "primeCount=" + primeCnt + ";checksum=" + checkSum;

        port.submitDataString(sCode, qCode, res);
    }
    public static boolean isPrime (int n) {
        if (n < 2) return false;
        for (int i = 2; i <= n; i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
