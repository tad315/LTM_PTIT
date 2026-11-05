package com.mycompany.rmi.Object;
import RMI.Ticket;
import RMI.ObjectService;
import java.io.Serializable;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
public class TicketClient {
    public static String findMissingDigits(String date) {
        boolean[] appeared = new boolean[10];
        for (int i = 0; i < date.length(); i++) {
            char c = date.charAt(i);
            if (Character.isDigit(c)) {
                appeared[c - '0'] = true;
            }
        }
        int max = -1;
        for (int i = 9; i >= 0; i--) {
            if (!appeared[i]) {
                max = i;
                break;
            }
        }
        int min = -1;
        for (int i = 0; i <= 9; i++) {
            if (!appeared[i]) {
                min = i;
                break;
            }
        }
        return "" + max + min;
    }
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = "YOUR_QCODE"; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ObjectService service = (ObjectService)registry.lookup("RMIObjectService");
            Serializable data =service.requestObject(studentCode, qCode);
            Ticket ticket = (Ticket) data;
            String eventName = ticket.getEventName().trim();
            String nameCode = ("" + eventName.charAt(0) + eventName.charAt(eventName.length() - 1)).toUpperCase();
            String[] dateParts = ticket.getSaleDate().split("/");
            String monthDay = dateParts[1] + dateParts[0];
            String missingDigits = findMissingDigits(ticket.getSaleDate());
            String ticketCode = nameCode + monthDay + missingDigits;
            ticket.setTicketCode(ticketCode);
            service.submitObject(studentCode, qCode, ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
