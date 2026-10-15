package mapreduce.map;

import mapreduce.keyvalue.KeyValue;

import java.util.List;

public interface Mapper<K,V>{
    List<KeyValue<K,V>> map(List<String> strings);
}
