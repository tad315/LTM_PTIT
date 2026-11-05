package com.mycompany.rmi.Object;
import RMI.ObjectService;
import RMI.TicketSla;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.Serializable;
public class TicketSlaClient {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = "EtsaEZ8r";
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ObjectService service = (ObjectService) registry.lookup("RMIObjectService");
            Serializable receivedObject = service.requestObject(studentCode, qCode);
            TicketSla ticket = (TicketSla) receivedObject;
            String priority = ticket.getPriority().toUpperCase();
            int hours = ticket.getOpenedHoursAgo();
            boolean breached = false;
            switch (priority) {
                case "CRITICAL":
                    breached = hours > 2;
                    break;
                case "HIGH":
                    breached = hours > 8;
                    break;
                case "MEDIUM":
                    breached = hours > 24;
                    break;
                case "LOW":
                    breached = hours > 72;
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Priority khong hop le: " + priority
                    );
            }
            ticket.setBreached(breached);
            if (!breached) {
                ticket.setAction("MONITOR");
            } else if (priority.equals("CRITICAL") || hours > 96) {
                ticket.setAction("ESCALATE_L2");
            } else {
                ticket.setAction("ESCALATE_L1");
            }
            service.submitObject(studentCode, qCode, ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}