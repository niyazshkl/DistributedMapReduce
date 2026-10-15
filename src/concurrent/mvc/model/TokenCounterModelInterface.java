package concurrent.mvc.model;

import java.beans.PropertyChangeListener;
import java.util.Map;

public interface TokenCounterModelInterface {
    Map<String, Integer> getResult();

    String getInputString();
    void setNumThreads(int numThreads);

    void setInputString(String inputString);
    void addPropertyChangeListener(PropertyChangeListener listener);
}
