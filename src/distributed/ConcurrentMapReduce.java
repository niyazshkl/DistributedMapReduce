package distributed;

import distributed.mvc.controller.TokenCounterController;
import distributed.mvc.controller.TokenCounterControllerInterface;
import distributed.mvc.model.TokenCounterModel;
import distributed.mvc.model.TokenCounterModelInterface;
import distributed.mvc.view.TokenCounterView;
import distributed.mvc.view.TokenCounterViewInterface;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class ConcurrentMapReduce extends AMapReduceTracer {
    public void demo(){
        TokenCounterModelInterface tokenCounterModel = new TokenCounterModel("hi");
        TokenCounterViewInterface tokenCounterView = new TokenCounterView();

        tokenCounterModel.addPropertyChangeListener(tokenCounterView);

        TokenCounterControllerInterface counterController = new TokenCounterController(tokenCounterModel);

        counterController.control();
    }
    public void demo(TokenCounterModelInterface tokenCounterModel){
       TokenCounterViewInterface tokenCounterView = new TokenCounterView();

        tokenCounterModel.addPropertyChangeListener(tokenCounterView);

        TokenCounterControllerInterface counterController = new TokenCounterController(tokenCounterModel);

        counterController.control();
    }

}
