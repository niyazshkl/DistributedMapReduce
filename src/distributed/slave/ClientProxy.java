package distributed.slave;

import distributed.mapreduce.keyvalue.KeyValue;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ClientProxy extends Remote, Serializable {
    Map<String, Integer> reduce(List<KeyValue<String,Integer>> givenList) throws RemoteException;
    void quit() throws RemoteException;
}
