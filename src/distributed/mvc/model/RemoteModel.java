package distributed.mvc.model;

import distributed.slave.ClientProxy;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteModel extends Remote {
    void registerClient(ClientProxy clientProxy) throws RemoteException;
    void terminateRemote() throws RemoteException;
}
