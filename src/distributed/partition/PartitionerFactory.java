package distributed.partition;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class PartitionerFactory extends AMapReduceTracer {
    private static Partitioner partitioner;

    public static void setPartitioner(Partitioner partitioner) {
        PartitionerFactory.partitioner = partitioner;
        traceSingletonChange(PartitionerFactory.class, partitioner);
    }

    public static Partitioner getPartitioner() {
        if (partitioner==null) return new TokenPartitioner();
        return partitioner;
    }
}
