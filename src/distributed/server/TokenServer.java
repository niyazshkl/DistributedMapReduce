package distributed.server;

import distributed.ConcurrentMapReduce;
import distributed.mvc.model.RemoteModel;
import distributed.mvc.model.RemoteTokenCounterModel;
import distributed.mvc.model.TokenCounterModelInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class TokenServer extends Server{
    public static void main(String[] args){
        try {
            Registry rmiRegistry = LocateRegistry.createRegistry(SERVER_PORT);
            //create the object you want to craete proxy of
            RemoteModel remoteModel = new RemoteTokenCounterModel();

            // Create proxy -> will create a seperate thread that doesnt end with main thread.
            UnicastRemoteObject.exportObject((Remote) remoteModel, 0); //random port

            //Add the proxy to the registry so that we can find get in client

            rmiRegistry.rebind(SERVER_OBJECT_NAME, remoteModel);
            new ConcurrentMapReduce().demo((TokenCounterModelInterface) remoteModel);


            //do i have to add exit here too? I think so?
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
