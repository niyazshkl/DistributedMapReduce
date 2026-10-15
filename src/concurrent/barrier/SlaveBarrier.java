package concurrent.barrier;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class SlaveBarrier extends AMapReduceTracer implements Barrier {
    int count;
    int now;

    public SlaveBarrier(int count) {
        this.count = count;
        this.now = 0;
    }

    @Override //multi-threads will call this so need to prevent concurrency issues
    public synchronized void barrier() {
        while(now<count){
            try {
                now++;
                traceBarrierWaitStart(this, count, now);
                wait();
                now--;
                traceBarrierWaitEnd(this, count, now);
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
//        now = 0;
        notifyAll();
        traceBarrierReleaseAll(this, count, now);
    }

    @Override
    public String toString() {
        return AMapReduceTracer.BARRIER;
    }
}
