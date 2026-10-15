package concurrent.mapreduce.keyvalue;

public interface KeyValue<K,V> {

    K getKey();

    V getValue();

    void setValue(V value);
}
