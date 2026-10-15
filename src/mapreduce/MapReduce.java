package mapreduce;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import mapreduce.controller.TokenCounterController;
import mapreduce.controller.TokenCounterControllerInterface;
import mapreduce.model.TokenCounterModel;
import mapreduce.model.TokenCounterModelInterface;
import mapreduce.view.TokenCounterView;
import mapreduce.view.TokenCounterViewInterface;

import java.beans.PropertyChangeSupport;

public class MapReduce extends AMapReduceTracer {
    public void demo() {

    PropertyChangeSupport tokenCounterModel = new TokenCounterModel("hi");
    TokenCounterViewInterface tokenCounterView = new TokenCounterView();

    tokenCounterModel.addPropertyChangeListener(tokenCounterView);

    TokenCounterControllerInterface counterController = new TokenCounterController( (TokenCounterModelInterface) tokenCounterModel);

    counterController.control();
    }
}
