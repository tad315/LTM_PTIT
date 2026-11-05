package com.mycompany.rmi.Object;
import RMI.Event;
import RMI.ObjectService;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.Serializable;
public class EventClient {
    public static void main(String[] args) {
        String studentCode = "B23DCCN156";
        String qCode = ""; 
        try {
            Registry registry = LocateRegistry.getRegistry("36.50.135.242");
            ObjectService service = (ObjectService)registry.lookup("RMIObjectService");
            Serializable data = service.requestObject(studentCode, qCode);
            Event event = (Event) data;
            String level;
            if (event.getExpectedAttendance() >= 1000) {
                level = "L";
            } else if (event.getExpectedAttendance() >= 500) {
                level = "M";
            } else {
                level = "S";
            }
            String name = event.getEventName().trim();
            char firstChar = name.charAt(0);
            char lastChar = name.charAt(name.length() - 1);
            String nameCode = ("" + firstChar + lastChar).toUpperCase();
            String[] dateParts = event.getEventDate().split("-");
            String dayMonth = dateParts[2] + dateParts[1];
            String eventCode = level + nameCode + dayMonth;
            event.setEventCode(eventCode);
            service.submitObject(studentCode, qCode, event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
