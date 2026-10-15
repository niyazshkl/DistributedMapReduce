package concurrent.mvc.model;

import concurrent.barrier.Barrier;
import concurrent.barrier.SlaveBarrier;
import concurrent.joiner.Joiner;
import concurrent.mapreduce.keyvalue.KeyVal;
import concurrent.mapreduce.keyvalue.KeyValue;
import concurrent.mapreduce.map.MapperFactory;
import concurrent.mapreduce.reduce.ReducerFactory;
import concurrent.slave.Consumer;
import concurrent.slave.Slave;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import util.misc.ThreadSupport;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TokenCounterModel extends PropertyChangeSupport implements TokenCounterModelInterface {
    private String inputString;
    private Map<String,Integer> result;
    private int NumThreads;
    private List<Thread> threads;
    private final int BUFFER_SIZE = 2; // got from AMapReduceTracer
    private BlockingQueue<KeyValue<String, Integer>> KeyValueQueue = new ArrayBlockingQueue(BUFFER_SIZE); //bounded buffer -> shared data
    private List<LinkedList<KeyValue<String,Integer>>> ReductionQueueList = new ArrayList(); //LinkedList are non-blocking queue
    private Joiner joiner;
    private Barrier barrier;

    public int getNumThreads() {
        return NumThreads;
    }

    public void setNumThreads(int numThreads) {
        PropertyChangeEvent propertyChangeEvent =
            new PropertyChangeEvent(this, "NumThreads", null, numThreads);
        firePropertyChange(propertyChangeEvent);
        NumThreads = numThreads;
        createThreads();
        createBarrier();
        startThreads();
    }

    private void createBarrier() {
        barrier = new SlaveBarrier(NumThreads);
        // I need to use AMapReduceTracer.traceBarrierCreated,
        // but I can't figure the right inheritence solution
        traceBarrierCreated(barrier, NumThreads);
    }
    private List<Slave> slaves = new ArrayList<>();


    private void createThreads(){
        threads = new ArrayList<>();
        for (int i =0; i<NumThreads; i++){
            Slave slave = new Consumer(this, i); //Directions: pass unique number:[0-NumThreads-1], this
            slaves.add(slave);
            Thread thread = new Thread(slave); //thread will eventually need to start that runnable
            thread.setName("Slave" + i);
            threads.add(thread);
        }
        PropertyChangeEvent propertyChangeEvent =
            new PropertyChangeEvent(this, "Threads", null, threads);
        firePropertyChange(propertyChangeEvent);
    }
    private void startThreads(){
        threads.forEach(Thread::start);
    }

    public List<Thread> getThreads() {
        return threads;
    }

    /**
     * Constructs a {@code PropertyChangeSupport} object.
     *
     * @param sourceBean The bean to be given as the source for any events.
     */
    public TokenCounterModel(Object sourceBean) {
        super(sourceBean);
        inputString = null;
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

    public void resetForRound(){
        this.result.clear();
        this.ReductionQueueList.clear();
        this.KeyValueQueue.clear();
    }

    public void notifySlaves(){
        slaves.forEach(Slave::notifySlave);
    }

    @Override
    public void setInputString(String inputString) {
        firePropertyChange(
                new PropertyChangeEvent(this, "InputString", this.inputString, inputString)
        );
        resetForRound();
        notifySlaves();
        this.inputString = inputString;
        String[] inputArray = inputString.split("\\s+");
        //Map & Reduce
        List<KeyVal> mappedResults = MapperFactory.getMapper().map(Arrays.asList(inputArray));

        mappedResults.forEach(keyValue -> {
            try {
                traceEnqueueRequest(keyValue);
                KeyValueQueue.put(keyValue);
                traceEnqueue(KeyValueQueue);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }

        });

        for (int i=0; i<this.NumThreads; i++) {
            try {
                KeyValueQueue.put(new KeyVal(null, null));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        joiner.join(); //main waits until the slaves have fully finished

        ReductionQueueList.forEach(l->result.put(l.element().getKey(), l.element().getValue()));

        result = ReducerFactory.getReducer().reduce(mappedResults);
        traceAddedToMap(null, result);

        firePropertyChange( new PropertyChangeEvent(this,"result", null, result));
    }

    private String ENQUEUE = "enqueue:";
    protected void traceEnqueueRequest(Object anElement) {
        this.trace(ENQUEUE + anElement);
    }

    protected void traceEnqueue(Object aQueue) {
        this.trace(ENQUEUE + aQueue);
    }

    protected void traceAddedToMap(Object anOriginalMap, Object aReducedMap) { //From AMapReduceTracer.class
        this.trace("Added to Final Map:" + anOriginalMap + ":" + aReducedMap);
    }
    @Override
    public String toString() {
        return AMapReduceTracer.MODEL;
    }

    protected void traceBarrierCreated(Object aBarrier, int aNumThreads) {
        this.trace("Barrier Created:" + aBarrier + ":" + aNumThreads);
    }
    protected String toPrefix() {
        return Thread.currentThread() + ":" + this + ":";
    }
    protected void trace(String aSuffix) {
        System.out.println(this.toPrefix() + aSuffix);
        System.out.flush();
        ThreadSupport.sleep(100L);
    }

    }

