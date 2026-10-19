package distributed.slave;

import distributed.mapreduce.keyvalue.KeyValue;

import java.util.List;

public interface Slave extends Runnable{
    void setClientProxy(DistributedClient distributedClient);

    List<KeyValue<String, Integer>> getPrivateStorage();

    void notifySlave();

}
