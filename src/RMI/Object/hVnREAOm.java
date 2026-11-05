package RMI.Object;

import RMI.ObjectService;
import RMI.TicketSla;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class hVnREAOm {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        String sv = "36.50.135.242";
        String sCode = "B23DCCN230";
        String qCode = "hVnREAOm";

        Registry registry = LocateRegistry.getRegistry(sv);
        ObjectService service = (ObjectService) registry.lookup("RMIObjectService");

        TicketSla ticketSla = (TicketSla) service.requestObject(sCode, qCode);

        String priority = ticketSla.getPriority();
        int h = ticketSla.getOpenedHoursAgo();

        boolean breached = false;

        if (priority.equals("CRITICAL") && h > 2) {
            breached = true;
        }
        else if (priority.equals("HIGH") && h > 8) {
            breached = true;
        }
        else if (priority.equals("MEDIUM") && h > 24) {
            breached = true;
        }
        else if (priority.equals("LOW") && h > 72) {
            breached = true;
        }

        ticketSla.setBreached(breached);

        if (!breached) {
            ticketSla.setAction("MONITOR");
        }
        else {
            if (priority.equals("CRITICAL") || h > 96) {
                ticketSla.setAction("ESCALATE_L2");
            }
            else {
                ticketSla.setAction("ESCALATE_L1");
            }
        }

        service.submitObject(sCode, qCode, ticketSla);
    }
}
