package distributed.mvc.model;

import distributed.slave.ClientProxy;
import distributed.slave.Consumer;
import distributed.slave.Slave;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Stack;

public class RemoteTokenCounterModel extends TokenCounterModel implements RemoteModel{
    /**
     * Constructs a {@code PropertyChangeSupport} object.
     *
     * @param sourceBean The bean to be given as the source for any events.
     */
    Stack<ClientProxy> clientProxies;
    public RemoteTokenCounterModel() {
        super("hi");
        clientProxies = new Stack<>();
    }

    @Override
    public void registerClient(ClientProxy clientProxy) {
        traceRegister(clientProxy);
        clientProxies.push(clientProxy);
        match();
    }
    protected void traceRegister(Object aClient) {
        this.trace("Register:" + aClient);
    }
    //matches slaves to clients
    private void match(){
        System.out.println("in match(): " + " clientProxies: " + clientProxies + " slaves: " +slaves);
        if((clientProxies==null) || (slaves==null)) return;

        if (!clientProxies.isEmpty() && !slaves.isEmpty()){
            Slave slave = slaves.pop();
            slave.setClientProxy(clientProxies.pop());
        }
        System.out.println("after match(): " + " clientProxies: " + clientProxies + " slaves: " +slaves);
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

    @Override
    public void terminateRemote(){
        threads.forEach(Thread::interrupt);

    }

}
