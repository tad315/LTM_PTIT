package WS.Object;

import SOAP.object.ObjectService;
import SOAP.object.ProductY;
import SOAP.object.SoapObjectService;

public class finalPrice {
    public static void main(String[] args) {
        String sCode = "B23DCCN230";
        String qCode = "71o7afhI";

        ObjectService service = new ObjectService();
        SoapObjectService port = service.getSoapObjectServicePort();

        ProductY product = port.requestProductY(sCode, qCode);

        float price = product.getPrice();
        float taxRate = product.getTaxRate();
        float discount = product.getDiscount();

        float finalPrice = (float) (price * (1 + taxRate / 100) * (1 - discount / 100.0));
        // lam tron finalPrice = Math.round(finalPrice * 100.0) / 100.0;

        product.setFinalPrice(finalPrice);

        port.submitProductY(sCode, qCode, product);


    }
}
