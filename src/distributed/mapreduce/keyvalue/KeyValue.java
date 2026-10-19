package distributed.mapreduce.keyvalue;

import java.io.Serializable;

public interface KeyValue<K,V> extends Serializable {

    K getKey();

    V getValue();

    void setValue(V value);
}
