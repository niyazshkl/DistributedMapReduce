package distributed.mapreduce.map;

import gradingTools.comp533s19.assignment0.AMapReduceTracer;

public class MapperFactory extends AMapReduceTracer {
    private static Mapper mapper;

    public static Mapper getMapper() {
        if (mapper==null) mapper = new TokenCountingMapper();
        return mapper;
    }

    public static void setMapper(Mapper mapper) {
        MapperFactory.mapper = mapper;
        traceSingletonChange(MapperFactory.class,mapper);
    }
}
