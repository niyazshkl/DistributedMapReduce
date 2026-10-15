package mapreduce.map;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import mapreduce.keyvalue.KeyVal;
import mapreduce.keyvalue.KeyValue;

import java.util.ArrayList;
import java.util.List;

public class TokenCountingMapper extends AMapReduceTracer implements Mapper<String, Integer> {
    @Override
    public List<KeyValue<String, Integer>> map(List<String> strings) {
        List<KeyValue<String,Integer>> result = new ArrayList<>();

        strings.stream().forEach((string)->{
            result.add(new KeyVal(string,Integer.valueOf(1)) {
            });
        });

        traceMap(strings, result);
        return result;
    }
    @Override
    public String toString() {
        return AMapReduceTracer.TOKEN_COUNTING_MAPPER;
    }
}
