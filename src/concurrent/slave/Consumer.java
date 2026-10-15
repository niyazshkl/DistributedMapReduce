package concurrent.slave;

import concurrent.mapreduce.keyvalue.KeyVal;
import concurrent.mvc.model.TokenCounterModel;
import concurrent.partition.PartitionerFactory;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import concurrent.mapreduce.keyvalue.KeyValue;
import mapreduce.reduce.ReducerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Consumer extends AMapReduceTracer implements Slave{
    private int slaveNumber;
    private TokenCounterModel tokenCounterModel;
    @Override
    public List<KeyValue<String, Integer>> getPrivateStorage() {
        return privateStorage;
    }

    private List<KeyValue<String, Integer>> privateStorage = new ArrayList<>();
    private boolean firstTime = true;

    public Consumer(TokenCounterModel tokenCounterModel, int slaveNumber) {
        this.tokenCounterModel = tokenCounterModel;
        this.slaveNumber = slaveNumber;
    }

    private void add(Map partialMap){
        partialMap.forEach((k,v)->{
            int partitionIndex = PartitionerFactory.getPartitioner().getPartition(k,v,this.tokenCounterModel.getNumThreads());
            KeyValue<String,Integer> kv = new KeyVal((String)k,(Integer) v);
            synchronized (Consumer.class){
                this.tokenCounterModel.getReductionQueueList().get(partitionIndex).add(kv);
            }
        });
    }

    @Override
    public synchronized void run() {
        //Stub RUN
        while(true){
            try {
                BlockingQueue<KeyValue<String,Integer>> blockingQueue = this.tokenCounterModel.getKeyValueQueue();
                traceDequeueRequest(blockingQueue);
                KeyValue<String, Integer> eaten = blockingQueue.take();
                traceDequeue(eaten);
                if (eaten.getKey()==null & eaten.getValue()==null) {
                    Map partialMap = partialReducation();
                    add(partialMap);
                    this.tokenCounterModel.getBarrier().barrier();

                    privateStorage.clear(); //remove the partial stuff
                    Map<String, Integer> finalReduction = fullReducation();
                    for (Map.Entry entry: finalReduction.entrySet()){
                        KeyValue<String,Integer> c = new KeyVal((String) entry.getKey(), (Integer) entry.getValue());
                        privateStorage.add(c);
                    }

                    this.tokenCounterModel.getJoiner().finished();
                    this.wait(); // need to wait until master thread reads old value before clearing it.
                    privateStorage.clear(); //reset for next round

                }
                else
                {
                    privateStorage.add(eaten);
                }
            } catch (InterruptedException e) {
//                e.printStackTrace();
                break;
            }
        }
        traceQuit();
    }

    private Map partialReducation(){
        return ReducerFactory.getReducer().reduce(privateStorage);
    }

    private Map<String, Integer> fullReducation(){
        return ReducerFactory.getReducer().reduce(this.tokenCounterModel.getReductionQueueList().get(this.slaveNumber));

    }

    @Override
    public void notifySlave() {
        //called by master to unblock the sleeping slave
        if (firstTime) firstTime=false;
        else synchronized (this){
            this.notify();
        }
        traceNotify();
    }

    @Override
    public String toString() {
        return AMapReduceTracer.SLAVE;
    }
}
