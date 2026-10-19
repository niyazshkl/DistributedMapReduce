package distributed.slave;

import distributed.mapreduce.keyvalue.KeyValue;
import distributed.mapreduce.reduce.ReducerFactory;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

import java.util.List;
import java.util.Map;

public class ClientReducer extends AMapReduceTracer implements ClientProxy,ClientLocal{
    @Override
    public Map<String, Integer> reduce(List<KeyValue<String, Integer>> givenList) {
        traceRemoteList(givenList);
        Map<String,Integer> result = ReducerFactory.getReducer().reduce(givenList);
        traceRemoteResult(result);
        return result;
    }

    @Override
    public synchronized void quit() {
        System.out.println(System.identityHashCode(this));
        notifyAll();
    }

    public synchronized void waiting(){
        try {
            System.out.println(System.identityHashCode(this));
            wait();
            System.out.println(System.identityHashCode(this));
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
