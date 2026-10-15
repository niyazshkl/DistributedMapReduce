package mapreduce.model;

import java.util.Map;

public interface TokenCounterModelInterface {
    Map<String, Integer> getResult();

    String getInputString();

    void setInputString(String inputString);
}
