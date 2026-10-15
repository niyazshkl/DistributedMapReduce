package mapreduce;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import mapreduce.controller.TokenCounterController;
import mapreduce.controller.TokenCounterControllerInterface;
import mapreduce.map.IntSummingMapper;
import mapreduce.map.MapperFactory;
import mapreduce.model.TokenCounterModel;
import mapreduce.model.TokenCounterModelInterface;
import mapreduce.view.TokenCounterView;
import mapreduce.view.TokenCounterViewInterface;

public class MapReduce extends AMapReduceTracer {
    public void intSummingMapReduceDemo() {
        //Only 1 extra line of code for IntSummingMapReduce.
        MapperFactory.setMapper(new IntSummingMapper());
        // Otherwise, behaves exactly like TokenCountingMapReduce
        tokenCountingMapReduceDemo();
    }

    public void tokenCountingMapReduceDemo(){
        TokenCounterModelInterface tokenCounterModel = new TokenCounterModel("hi");
        TokenCounterViewInterface tokenCounterView = new TokenCounterView();

        tokenCounterModel.addPropertyChangeListener(tokenCounterView);

        TokenCounterControllerInterface counterController = new TokenCounterController(tokenCounterModel);

        counterController.control();
    }

}
