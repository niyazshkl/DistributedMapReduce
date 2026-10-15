package concurrent.mapreduce.map;

import concurrent.mapreduce.keyvalue.KeyValue;

import java.util.List;

public interface Mapper<K,V>{
    List<KeyValue<K,V>> map(List<K> keyList);
}
