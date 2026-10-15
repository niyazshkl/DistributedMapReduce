package concurrent;

import concurrent.mvc.controller.TokenCounterController;
import concurrent.mvc.controller.TokenCounterControllerInterface;
import concurrent.mvc.model.TokenCounterModel;
import concurrent.mvc.model.TokenCounterModelInterface;
import concurrent.mvc.view.TokenCounterView;
import concurrent.mvc.view.TokenCounterViewInterface;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class ConcurrentMapReduce extends AMapReduceTracer {
    public void demo(){
        TokenCounterModelInterface tokenCounterModel = new TokenCounterModel("hi");
        TokenCounterViewInterface tokenCounterView = new TokenCounterView();

        tokenCounterModel.addPropertyChangeListener(tokenCounterView);

        TokenCounterControllerInterface counterController = new TokenCounterController(tokenCounterModel);

        counterController.control();
    }

}
