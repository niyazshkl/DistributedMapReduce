package concurrent.slave;

import concurrent.mapreduce.keyvalue.KeyValue;

import java.util.List;

public interface Slave extends Runnable{
    List<KeyValue<String, Integer>> getPrivateStorage();

    void notifySlave();

}
