package distributed.mvc.model;

import distributed.barrier.Barrier;
import distributed.barrier.SlaveBarrier;
import distributed.joiner.Joiner;
import distributed.joiner.TokenJoiner;
import distributed.mapreduce.keyvalue.KeyVal;
import distributed.mapreduce.keyvalue.KeyValue;
import distributed.mapreduce.map.MapperFactory;
import distributed.slave.Consumer;
import distributed.slave.Slave;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;
import util.misc.ThreadSupport;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TokenCounterModel extends PropertyChangeSupport implements Serializable, TokenCounterModelInterface {
    private String inputString;
    private Map<String,Integer> result;
    protected int NumThreads;
    protected Stack<Slave> slaves;
    protected List<Thread> threads;
    private final int BUFFER_SIZE = 2; // got from AMapReduceTracer
    private BlockingQueue<KeyValue<String, Integer>> KeyValueQueue = new ArrayBlockingQueue(BUFFER_SIZE); //bounded buffer -> shared data
    private List<LinkedList<KeyValue<String,Integer>>> ReductionQueueList = new ArrayList(NumThreads); //LinkedList are non-blocking queue
    private Joiner joiner;
    private Barrier barrier;

    private void createBarrier() {
        barrier = new SlaveBarrier(NumThreads);
        // I need to use AMapReduceTracer.traceBarrierCreated,
        // but I can't figure the right inheritence solution
        traceBarrierCreated(barrier, NumThreads);
    }


    protected void createThreads(){
        threads = new ArrayList<>();
        slaves = new Stack<>();
        for (int i =0; i<NumThreads; i++){
            Slave slave = new Consumer(this, i); //Directions: pass unique number:[0-NumThreads-1], this
            slaves.push(slave);
            Thread thread = new Thread(slave); //thread will eventually need to start that runnable
            thread.setName("Slave" + i);
            threads.add(thread);
        }
        PropertyChangeEvent propertyChangeEvent =
            new PropertyChangeEvent(this, "Threads", null, threads);
        firePropertyChange(propertyChangeEvent);
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

    public void notifySlaves(){
        slaves.forEach(Slave::notifySlave);
    }

    public void setNumThreads(int numThreads) {
        PropertyChangeEvent propertyChangeEvent =
                new PropertyChangeEvent(this, "NumThreads", null, numThreads);
        firePropertyChange(propertyChangeEvent);
        NumThreads = numThreads;
        for (int i =0; i<NumThreads; i++){
            ReductionQueueList.add(new LinkedList<KeyValue<String,Integer>>());
        }
        resetForRound();
        createThreads();
        createBarrier();
        createJoiner();
        startThreads();
    }
    public void resetForRound(){
        this.result.clear();
        this.ReductionQueueList.stream().forEach((lList->lList.clear()));
        this.KeyValueQueue.clear();
    }
    protected List<Slave> matchedSlaves = new ArrayList<>();
    @Override
    public void terminate(){
        threads.forEach(Thread::interrupt);
    }
    @Override
    public void setInputString(String inputString) {
        firePropertyChange(
                new PropertyChangeEvent(this, "InputString", this.inputString, inputString)
        );
        notifySlaves();
        this.inputString = inputString;
        String[] inputArray = inputString.split("\\s+");
        //Map & Reduce
        List<KeyVal> mappedResults = MapperFactory.getMapper().map(Arrays.asList(inputArray));

        for (KeyValue keyValue : mappedResults) {
            try {
                traceEnqueueRequest(keyValue);
                KeyValueQueue.put(keyValue);
                traceEnqueue(KeyValueQueue);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        for (int i=0; i<this.NumThreads; i++) {
            try {
                KeyVal keyValue = new KeyVal(null, null);
                traceEnqueueRequest(keyValue);
                KeyValueQueue.put(keyValue);
                traceEnqueue(KeyValueQueue);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        joiner.join(); //main waits until the slaves have fully finished

        //should read the private value of the storage instead!
        for (Slave slave:slaves){
            slave.getPrivateStorage().forEach(a ->result.put(a.getKey(), a.getValue()));
        }

        for (Slave slave:matchedSlaves){
            slave.getPrivateStorage().forEach(a ->result.put(a.getKey(), a.getValue()));
        }

        traceAddedToMap(null, result);

        firePropertyChange( new PropertyChangeEvent(this,"result", null, result));

        resetForRound();
    }
    public Barrier getBarrier() {
        return barrier;
    }

    public List<LinkedList<KeyValue<String, Integer>>> getReductionQueueList() {
        return ReductionQueueList;
    }

    public void setReductionQueueList(List<LinkedList<KeyValue<String, Integer>>> reductionQueueList) {
        ReductionQueueList = reductionQueueList;
    }

    public BlockingQueue<KeyValue<String, Integer>> getKeyValueQueue() {
        return KeyValueQueue;
    }

    public int getNumThreads() {
        return NumThreads;
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


    public Map<String, Integer> getResult() {
        return result;
    }
    protected void startThreads(){
        threads.forEach(Thread::start);
    }

    public List<Thread> getThreads() {
        return threads;
    }

    public String getInputString() {
        return inputString;
    }
    protected void traceAddedToMap(Object anOriginalMap, Object aReducedMap) { //From AMapReduceTracer.class
        this.trace("Added to Final Map:" + anOriginalMap + ":" + aReducedMap);
    }
    @Override
    public String toString() {
        return AMapReduceTracer.MODEL;
    }
    private String ENQUEUE = "enqueue:";
    protected void traceEnqueueRequest(Object anElement) {
        this.trace(ENQUEUE + anElement);
    }

    protected void traceEnqueue(Object aQueue) {
        this.trace(ENQUEUE + aQueue);
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
    public Joiner getJoiner() {
        return joiner;
    }
    protected void traceJoinerCreated(Object aJoiner, int aNumThreads) {
        this.trace("Joiner Created:" + aJoiner + ":" + aNumThreads);
    }
    private void createJoiner(){
        joiner = new TokenJoiner(NumThreads);
        traceJoinerCreated(joiner, NumThreads);
    }

    }

