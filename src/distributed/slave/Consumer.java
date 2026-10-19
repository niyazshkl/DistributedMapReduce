package distributed.slave;

import distributed.mapreduce.keyvalue.KeyVal;
import distributed.mvc.model.TokenCounterModel;
import distributed.partition.PartitionerFactory;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import distributed.mapreduce.keyvalue.KeyValue;
import distributed.mapreduce.reduce.ReducerFactory;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

public class Consumer extends AMapReduceTracer implements Slave{
    private int slaveNumber;
    private TokenCounterModel tokenCounterModel;
    private ClientProxy clientProxy;
    @Override
    public void setClientProxy(ClientProxy clientProxy) {
        this.clientProxy = clientProxy;
    }

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
                    System.out.println("FINAL REDUCTION: " + finalReduction);
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
        if (clientProxy==null) return;
        try {
            clientProxy.quit();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

    }

    private Map partialReducation(){
        System.out.print("ABOUT TO REDUCE:");
        traceReduce(this, privateStorage);
        Map result = new HashMap();
        if (clientProxy==null)
            result = ReducerFactory.getReducer().reduce(privateStorage);
        else {
            try {
               result = clientProxy.reduce(privateStorage);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.print("REDUCED RETURN:");
        traceReduce(this, result);
        return result;
    }

    private Map<String, Integer> fullReducation(){
        Map<String, Integer> result = new HashMap<>();
        if (clientProxy==null)
            result = ReducerFactory.getReducer().reduce(this.tokenCounterModel.getReductionQueueList().get(this.slaveNumber));
        else {
            try {
                result = clientProxy.reduce(this.tokenCounterModel.getReductionQueueList().get(this.slaveNumber));
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
        return result;
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
