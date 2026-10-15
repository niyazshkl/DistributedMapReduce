package concurrent.slave;

import concurrent.mvc.model.TokenCounterModelInterface;
import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class Consumer extends AMapReduceTracer implements Slave{
    private int slaveNumber;
    private TokenCounterModelInterface tokenCounterModelInterface;

    public Consumer(TokenCounterModelInterface tokenCounterModelInterface, int slaveNumber) {
        this.tokenCounterModelInterface = tokenCounterModelInterface;
        this.slaveNumber = slaveNumber;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        //Stub RUN
    }

    @Override
    public synchronized void notifySlave() { //why does this have to be synchronized tho?
        traceNotify();
        this.notify();
    }

    @Override
    public String toString() {
        return AMapReduceTracer.SLAVE;
    }
}
