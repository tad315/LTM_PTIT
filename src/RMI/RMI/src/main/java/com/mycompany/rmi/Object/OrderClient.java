package com.mycompany.rmi.Object;
import RMI.Order;
import RMI.ObjectService;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class OrderClient {
    public static void main(String[] args) {

        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ObjectService service = (ObjectService)registry.lookup("RMIObjectService");
            Serializable data = service.requestObject(studentCode, qCode);
            Order order = (Order) data;
            String shippingType = order.getShippingType().trim();
            String shippingCode = shippingType.substring(0, 2).toUpperCase();
            String customerCode = order.getCustomerCode();
            String customerPart = customerCode.substring(customerCode.length() - 3);
            String[] dateParts = order.getOrderDate().split("-");
            String dayMonth = dateParts[2] + dateParts[1];
            String orderCode = shippingCode + customerPart + dayMonth;
            order.setOrderCode(orderCode);
            service.submitObject(studentCode, qCode, order);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
