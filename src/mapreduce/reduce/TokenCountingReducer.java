package mapreduce.reduce;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import mapreduce.keyvalue.KeyValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenCountingReducer extends AMapReduceTracer implements Reducer<String, Integer> {

    @Override
    public Map<String, Integer> reduce(List<KeyValue<String, Integer>> givenList) {
        Map<String,Integer> reducedMap = new HashMap<>();
        givenList.stream().forEach(
                (kv) ->{
                    if (reducedMap.get(kv.getKey())==null) reducedMap.put(kv.getKey(), 1);
                    else reducedMap.put(kv.getKey(),reducedMap.get(kv.getKey()) + kv.getValue());

                }
        );

        traceReduce(givenList,reducedMap);

        return reducedMap;

    }

    @Override
    public String toString() {
        return AMapReduceTracer.REDUCER;
    }

}
