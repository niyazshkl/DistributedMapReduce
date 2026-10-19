package distributed.mapreduce.map;

import distributed.mapreduce.keyvalue.KeyValue;

import java.util.List;

public interface Mapper<K,V>{
    List<KeyValue<K,V>> map(List<K> keyList);
}
