package mvc.model;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TokenCounterModel extends PropertyChangeSupport implements TokenCounterModelInterface {
    private String inputString;
    private Map<String,Integer> result;
    private PropertyChangeSupport propertyChangeSupport;

    /**
     * Constructs a {@code PropertyChangeSupport} object.
     *
     * @param sourceBean The bean to be given as the source for any events.
     */
    public TokenCounterModel(Object sourceBean) {
        super(sourceBean);
        inputString = new String();
        result = new HashMap<>();
    }


    @Override
    public Map<String, Integer> getResult() {
        return result;
    }

    @Override
    public String getInputString() {
        return inputString;
    }

    @Override
    public void setInputString(String inputString) {
        firePropertyChange(
                new PropertyChangeEvent(this, "InputString", null, inputString)
        );
        this.inputString = inputString;
        reduce(inputString);
    }

    private void reduce(String inputString){
        String[] inputArray = inputString.split("\\s+");
        // do the counting manualy per item
        result.clear();
        Arrays.stream(inputArray).forEach((item) -> {
            if (!result.containsKey(item)) result.put(item, 1);
            else result.put(item, result.get(item) + 1);
        });

        firePropertyChange(new PropertyChangeEvent(this,"Result",null,result));
    }

    @Override
    public String toString() {
        return AMapReduceTracer.MODEL;
    }

    }

