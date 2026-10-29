package distributed.mvc.model;

import distributed.slave.DistributedClient;
import distributed.slave.Consumer;
import distributed.slave.Slave;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DistributedTokenCounterModel extends TokenCounterModel implements DistributedModel {
    /**
     * Constructs a {@code PropertyChangeSupport} object.
     *
     * @param sourceBean The bean to be given as the source for any events.
     */
    Stack<DistributedClient> clientProxies;
    public DistributedTokenCounterModel() {
        super("hi");
        clientProxies = new Stack<>();
    }

    @Override
    public void registerClient(DistributedClient distributedClient) {
        traceRegister(distributedClient);
        clientProxies.push(distributedClient);
        match();
    }
    protected void traceRegister(Object aClient) {
        this.trace("Register:" + aClient);
    }

    //matches slaves to clients
    private void match(){
        if((clientProxies==null) || (slaves==null)) return;

        if (!clientProxies.isEmpty() && !slaves.isEmpty()){
            Slave slave = slaves.pop();
            matchedSlaves.add(slave);
            slave.setClientProxy(clientProxies.pop());
        }
    }
    @Override
    protected void createThreads(){
        threads = new ArrayList<>();
        slaves = new Stack<>();
        for (int i =0; i<NumThreads; i++){
            Slave slave = new Consumer(this, i); //Directions: pass unique number:[0-NumThreads-1], this
            slaves.push(slave);
            System.out.println("Slave: " + slave + " created");
            match();
            Thread thread = new Thread(slave); //thread will eventually need to start that runnable
            thread.setName("Slave" + i);
            threads.add(thread);
        }
        PropertyChangeEvent propertyChangeEvent =
                new PropertyChangeEvent(this, "Threads", null, threads);
        firePropertyChange(propertyChangeEvent);
    }
    @Override
    protected void startThreads(){
        match();
        threads.forEach(Thread::start);
    }

}
