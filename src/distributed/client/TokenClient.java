package distributed.client;

import distributed.mvc.model.DistributedModel;
import distributed.slave.Client;
import distributed.slave.DistributedClient;
import distributed.slave.DistributedClientReducer;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TokenClient extends distributed.client.Client {
    public static void main(String[] args) {
        try {
            Registry rmiRegistry = LocateRegistry.getRegistry(SERVER_PORT);

            Remote remoteObject = rmiRegistry.lookup(SERVER_OBECT_NAME);
            System.out.println(remoteObject);
            DistributedModel distributedModel =  (DistributedModel) remoteObject; //Why ClassCastException?
//            System.out.println(remoteObject);


            Client clientProxy = new DistributedClientReducer();

            UnicastRemoteObject.exportObject((DistributedClient) clientProxy, 0);
            distributedModel.registerClient((DistributedClient) clientProxy);

            clientProxy.waiting();

            System.out.println("TokenClient exitting soon");
            System.exit(-5);

        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) { //thrown by Registry.lookup
            throw new RuntimeException(e);
        }
    }

        }
