package mapreduce.reduce;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class ReducerFactory extends AMapReduceTracer {
    private static Reducer reducer;

    public static Reducer getReducer() {
        if (reducer==null) reducer = new TokenCountingReducer();
        return reducer;
    }

    public static void setReducer(Reducer reducer) {
        ReducerFactory.reducer = reducer;
        traceSingletonChange(ReducerFactory.class, reducer);
    }
}
