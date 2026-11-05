package WS.Object;

import SOAP.object.Customer;
import SOAP.object.ObjectService;
import SOAP.object.SoapObjectService;

import java.util.ArrayList;
import java.util.List;

public class CustomerVIP {
    public static void main(String[] args) {
        String sCode = "B23DCCN230";
        String qCode = "P8HfSVOn";

        ObjectService service = new ObjectService();
        SoapObjectService port = service.getSoapObjectServicePort();

        List<Customer> data = port.requestListCustomer(sCode, qCode);

        List<Customer> res = new ArrayList<>();

        for (Customer c : data) {
            if (c.getPurchaseCount() >= 6 && c.getTotalSpent() >= 4000) {
                res.add(c);
            }
        }

        res.sort((a, b) -> {
            // totalSpent giảm dần
            int cmp = Float.compare(
                    b.getTotalSpent(),
                    a.getTotalSpent()
            );
            if (cmp != 0) {
                return cmp;
            }
            // customerId tăng dần nếu totalSpent bằng nhau
            return a.getCustomerId().compareTo(b.getCustomerId());
        });
        port.submitListCustomer(sCode, qCode, res);
    }
}
