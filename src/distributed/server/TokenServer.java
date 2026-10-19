package distributed.server;

import distributed.ConcurrentMapReduce;
import distributed.mvc.model.DistributedModel;
import distributed.mvc.model.DistributedTokenCounterModel;
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
            DistributedModel distributedModel = new DistributedTokenCounterModel();

            // Create proxy -> will create a seperate thread that doesnt end with main thread.
            UnicastRemoteObject.exportObject((Remote) distributedModel, 0); //random port

            //Add the proxy to the registry so that we can find get in client

            rmiRegistry.rebind(SERVER_OBJECT_NAME, distributedModel);
            new ConcurrentMapReduce().demo((TokenCounterModelInterface) distributedModel);


            //do i have to add exit here too? I think so?
        } catch (RemoteException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
}
