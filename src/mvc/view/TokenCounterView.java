package mvc.view;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class TokenCounterView extends AMapReduceTracer implements TokenCounterViewInterface{

    @Override
    public void propertyChange(PropertyChangeEvent event){
        tracePropertyChange(event);
    }

    @Override
    public String toString() {
        return AMapReduceTracer.VIEW;
    }
}
