package concurrent.barrier;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class SlaveBarrier extends AMapReduceTracer implements Barrier {
    int target;
    int count;
    int exit;

    public SlaveBarrier(int target) {
        this.target = target;
        this.count = 0;
        this.exit = 0;
    }

    @Override //multi-threads will call this so need to prevent concurrency issues
    public synchronized void barrier() {
        count++;
        while(count < target){ //why we need while?
            try {
                traceBarrierWaitStart(this, target, count);
                wait();
//                count--;
                leave();
                traceBarrierWaitEnd(this, target, count);
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        exit = 1;
        notifyAll();
        traceBarrierReleaseAll(this, target, count);
    }

    private synchronized void leave(){
        exit++;
        count--;
        if (exit==target) count = 0; //only the last thread should reset count
    }

    @Override
    public String toString() {
        return AMapReduceTracer.BARRIER;
    }
}
