package mvc;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import mvc.controller.TokenCounterController;
import mvc.controller.TokenCounterControllerInterface;
import mvc.model.TokenCounterModel;
import mvc.model.TokenCounterModelInterface;
import mvc.view.TokenCounterView;
import mvc.view.TokenCounterViewInterface;

import java.beans.PropertyChangeSupport;

public class MVCTokenCounter extends AMapReduceTracer {
    public void demo() {

    PropertyChangeSupport tokenCounterModel = new TokenCounterModel("hi");
    TokenCounterViewInterface tokenCounterView = new TokenCounterView();

    tokenCounterModel.addPropertyChangeListener(tokenCounterView);

    TokenCounterControllerInterface counterController = new TokenCounterController( (TokenCounterModelInterface) tokenCounterModel);

    counterController.control();
    }
}
