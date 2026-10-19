package distributed.mapreduce.map;

import distributed.mapreduce.keyvalue.KeyVal;
import distributed.mapreduce.keyvalue.KeyValue;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

import java.util.ArrayList;
import java.util.List;

public class IntSummingMapper extends AMapReduceTracer implements Mapper<String, Integer> {

    @Override
    public List<KeyValue<String, Integer>> map(List<String> strings) {
        List<KeyValue<String, Integer>> mappedResult = new ArrayList();
        strings.stream().forEach((string)->mappedResult.add(new KeyVal("ResultKey", Integer.parseInt(string))));
        traceMap(strings, mappedResult);
        return mappedResult;
    }

    @Override
    public String toString() {
        return AMapReduceTracer.INT_SUMMING_MAPPER;
    }
}
