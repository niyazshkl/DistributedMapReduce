package distributed.client;

import distributed.mvc.model.RemoteModel;
import distributed.slave.ClientLocal;
import distributed.slave.ClientProxy;
import distributed.slave.ClientReducer;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TokenClient extends Client {
    public static void main(String[] args) {
        try {
            Registry rmiRegistry = LocateRegistry.getRegistry(SERVER_PORT);

            Remote remoteObject = rmiRegistry.lookup(SERVER_OBECT_NAME);
            System.out.println(remoteObject);
            RemoteModel remoteModel =  (RemoteModel) remoteObject; //Why ClassCastException?
//            System.out.println(remoteObject);


            ClientLocal clientProxy = new ClientReducer();

            UnicastRemoteObject.exportObject((ClientProxy) clientProxy, 0);
            remoteModel.registerClient((ClientProxy) clientProxy);

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
