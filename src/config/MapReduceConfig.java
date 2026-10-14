package config;

import gradingTools.comp533s21.assignment1.interfaces.MapReduceConfiguration;
import mvc.controller.TokenCounterController;
import mvc.model.TokenCounterModel;
import mvc.view.TokenCounterView;
import standalone.StandAloneTokenCounter;

public class MapReduceConfig implements MapReduceConfiguration {
    @Override
    public Class getStandAloneTokenCounter() {
        return StandAloneTokenCounter.class;
    }

    @Override
    public Class getStandAloneIntegerSummer() {
        return null;
    }

    @Override
    public Class getModelClass() {
        return TokenCounterModel.class;
    }

    @Override
    public Class getViewClass() {
        return TokenCounterView.class;
    }
    @Override
    public Class getControllerClass() {
        return TokenCounterController.class;
    }

    @Override
    public Class getMapperFactory() {
        return null;
    }

    @Override
    public Class getReducerFactory() {
        return null;
    }

    @Override
    public Class getKeyValueClass() {
        return null;
    }

    @Override
    public Class getTokenCountingMapperClass() {
        return null;
    }

    @Override
    public Class getIntSummingMapperClass() {
        return null;
    }

    @Override
    public Class getReducerClass() {
        return null;
    }

    @Override
    public Object getTokenCountingMapper() {
        return null;
    }

    @Override
    public Object getIntSummingMapper() {
        return null;
    }

    @Override
    public Object getReducer() {
        return null;
    }

    @Override
    public Class getPartitionerClass() {
        return null;
    }

    @Override
    public Class getPartitionerFactory() {
        return null;
    }

    @Override
    public Object getPartitioner() {
        return null;
    }

    @Override
    public Class getJoinerClass() {
        return null;
    }

    @Override
    public Class getBarrierClass() {
        return null;
    }

    @Override
    public Class getSlaveClass() {
        return null;
    }

    @Override
    public Object getBarrier(int i) {
        return null;
    }

    @Override
    public Object getJoiner(int i) {
        return null;
    }

    @Override
    public Class getRemoteModelInterface() {
        return null;
    }

    @Override
    public Class getRemoteClientObjectInterface() {
        return null;
    }

    @Override
    public Class getRemoteClientObjectClass() {
        return null;
    }

    @Override
    public Class getServerTokenCounter() {
        return null;
    }

    @Override
    public Class getServerIntegerSummer() {
        return null;
    }

    @Override
    public Class getClientTokenCounter() {
        return null;
    }

    @Override
    public Class getStandAloneFacebookMapReduce() {
        return null;
    }

    @Override
    public Class getServerFacebookMapReduce() {
        return null;
    }

    @Override
    public Class getRemoteClientFacebookMapReduce() {
        return null;
    }
}
