package WS.Object;

import SOAP.object.Customer;
import SOAP.object.ObjectService;
import SOAP.object.SoapObjectService;

import java.util.ArrayList;
import java.util.List;

public class YfA0xyNW {
    public static void main(String[] args) {
        String sCode = "B23DCCN230";
        String qCode = "YfA0xyNW";

        ObjectService service = new ObjectService();
        SoapObjectService port = service.getSoapObjectServicePort();

        List<Customer> data = port.requestListCustomer(sCode, qCode);

        List<Customer> res = new ArrayList<>();

        for (Customer c : data) {
            if (c.getTotalSpent() > 500 && c.getPurchaseCount() >= 3) {
                res.add(c);
            }
        }
        port.submitListCustomer(sCode, qCode, res);
    }
}
