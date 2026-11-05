package RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ByteService extends Remote {
    byte[] requestData(String sCode, String qCode) throws RemoteException;
    void submitData(String sCode, String qCode, byte[] data) throws RemoteException;
}
