package distributed.mvc.model;

import java.beans.PropertyChangeListener;
import java.util.Map;

public interface TokenCounterModelInterface {

    void setNumThreads(int numThreads);

    void terminate();

    void setInputString(String inputString);
    void addPropertyChangeListener(PropertyChangeListener listener);
}
