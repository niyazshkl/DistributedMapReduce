package concurrent.joiner;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class TokenJoiner extends AMapReduceTracer implements Joiner{
    private int numThreads;
    private int finishedThreads;

    public TokenJoiner(int numThreads) {
        this.numThreads = numThreads;
        this.finishedThreads = 0;
    }

    @Override //using synchronized(this) because read&write same value
    public synchronized void join() {
        while (finishedThreads < numThreads){
            try {
                traceJoinerWaitStart(this, this.numThreads, this.finishedThreads);
                wait();
                finishedThreads--;
                traceJoinerWaitEnd(this, this.numThreads, this.finishedThreads);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        notifyAll();
        traceJoinerRelease(this, this.numThreads, this.finishedThreads);
    }

    @Override
    public synchronized void finished() {
            finishedThreads++;
            traceJoinerFinishedTask(this, this.numThreads, this.finishedThreads);
    }

    @Override
    public String toString() {
        return AMapReduceTracer.JOINER;
    }
}
