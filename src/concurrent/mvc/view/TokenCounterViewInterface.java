package concurrent.mvc.view;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public interface TokenCounterViewInterface extends PropertyChangeListener {
    void propertyChange(PropertyChangeEvent event);
}
