package concurrent.mapreduce.keyvalue;

public class KeyVal implements KeyValue<String, Integer> {
    private String Key;
    private Integer Value;
    @Override
    public String getKey() {
        return Key;
    }

    public KeyVal(String key, Integer value) {
        Key = key;
        Value = value;
    }

    @Override
    public Integer getValue() {
        return Value;
    }
    @Override
    public void setValue(Integer value) {
        Value = value;
    }

    @Override
    public String toString() {
        return "(" + Key + "," + Value +")";
    }
}
