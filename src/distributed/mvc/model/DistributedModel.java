package distributed.mvc.model;

import distributed.slave.DistributedClient;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DistributedModel extends Remote {
    void registerClient(DistributedClient distributedClient) throws RemoteException;
}
