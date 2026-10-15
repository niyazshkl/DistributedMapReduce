package config;

import concurrent.barrier.SlaveBarrier;
import concurrent.joiner.TokenJoiner;
import concurrent.partition.PartitionerFactory;
import concurrent.partition.TokenPartitioner;
import concurrent.slave.Consumer;
import gradingTools.comp533s21.assignment1.interfaces.MapReduceConfiguration;
import concurrent.mapreduce.map.MapperFactory;
import concurrent.mapreduce.map.TokenCountingMapper;
import concurrent.mapreduce.reduce.ReducerFactory;
import concurrent.mapreduce.reduce.TokenCountingReducer;
import concurrent.mvc.controller.TokenCounterController;
import concurrent.mvc.model.TokenCounterModel;
import concurrent.mvc.view.TokenCounterView;
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
        return MapperFactory.class;
    }

    @Override
    public Class getReducerFactory() {
        return ReducerFactory.class;
    }

    @Override
    public Class getKeyValueClass() {
        return null;
    }

    @Override
    public Class getTokenCountingMapperClass() {
        return TokenCountingMapper.class;
    }

    @Override
    public Class getIntSummingMapperClass() {
        return null;
    }

    @Override
    public Class getReducerClass() {
        return TokenCountingReducer.class;
    }

    @Override
    public Object getTokenCountingMapper() {
        return new TokenCountingMapper();
    }

    @Override
    public Object getIntSummingMapper() {
        return null;
    }

    @Override
    public Object getReducer() {
        return new TokenCountingReducer();
    }

    @Override
    public Class getPartitionerClass() {
        return TokenPartitioner.class;
    }

    @Override
    public Class getPartitionerFactory() {
        return PartitionerFactory.class;
    }

    @Override
    public Object getPartitioner() {
        return PartitionerFactory.getPartitioner();
    }

    @Override
    public Class getJoinerClass() {
        return TokenJoiner.class;
    }

    @Override
    public Class getBarrierClass() {
        return SlaveBarrier.class;
    }

    @Override
    public Class getSlaveClass() {
        return Consumer.class;
    }

    @Override
    public Object getBarrier(int i) {
        return new SlaveBarrier(i);
    }

    @Override
    public Object getJoiner(int i) {
        return new TokenJoiner(i);
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
