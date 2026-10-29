package distributed.partition;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class TokenPartitioner extends AMapReduceTracer implements Partitioner<String, Object>{
    /**
     *
     * @param key
     * @param value
     * @param numberOfPartitions
     * @return a value in range 0...numberOfPartitions-1 showing where the partition for this <K,V> belongs to
     * Partioning algorithm defined
     * Let p be the number of partitions.
     * Let max partition size, s, be the ceiling of the number of letters divided by the number of partitions,
     *      that is, ceiling ( (‘z’ - ‘a’ + 1) / (double) p).
    * Then the partition assigned to a key whose first character is a letter with
     *  the lowercase value, f, is: floor ((f - ‘a’ + 1)/(double) s).
     */
    @Override
    public int getPartition(String key, Object value, int numberOfPartitions){

        char firstChar = key.charAt(0);
        boolean isFirstCharALetter = Character.isLetter(firstChar);
        if (!isFirstCharALetter) return 0;

        char firstCharToLowerCase = Character.toLowerCase(firstChar);

        int p = numberOfPartitions;
        double s = Math.ceil(('z'-'a'+1)/(double)p);
        char f = firstCharToLowerCase;
        int partition = (int) (Math.floor((f-'a'+1)/(double) s));

        tracePartitionAssigned(key, value, partition, numberOfPartitions);

        return partition;
    }

    @Override
    public String toString() {
        return AMapReduceTracer.PARTITIONER;
    }
}
