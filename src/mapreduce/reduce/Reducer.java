package mapreduce.reduce;

import mapreduce.keyvalue.KeyVal;
import mapreduce.keyvalue.KeyValue;

import java.util.List;
import java.util.Map;

public interface Reducer<K,V> {
    Map<K,V> reduce(List<KeyValue<K,V>> givenList);
}
