package mapreduce.model;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import mapreduce.keyvalue.KeyVal;
import mapreduce.keyvalue.KeyValue;
import mapreduce.map.Mapper;
import mapreduce.map.MapperFactory;
import mapreduce.reduce.ReducerFactory;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenCounterModel extends PropertyChangeSupport implements TokenCounterModelInterface {
    private String inputString;
    private Map<String,Integer> result;

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
    /**
     * I (Niyaz) came up with a smart idea to make the wiring up of MVC much better in the demo! Rest is copy from parent docs
     * Add a PropertyChangeListener to the listener list.
     * The listener is registered for all properties.
     * The same listener object may be added more than once, and will be called
     * as many times as it is added.
     * If {@code listener} is null, no exception is thrown and no action
     * is taken.
     *
     * @param listener  The PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        super.addPropertyChangeListener(listener);
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
        String[] inputArray = inputString.split("\\s+");

        //Map & Reduce
        List<KeyValue> mappedResults = MapperFactory.getMapper().map(Arrays.asList(inputArray));
        result = ReducerFactory.getReducer().reduce(mappedResults);
        firePropertyChange( new PropertyChangeEvent(this,"result", null, result));
    }

    @Override
    public String toString() {
        return AMapReduceTracer.MODEL;
    }

    }

