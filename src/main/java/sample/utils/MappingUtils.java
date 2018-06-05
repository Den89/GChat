package sample.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

public class MappingUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static ObjectReader readerFor(Class type) {
        return mapper.readerFor(type);
    }

    public static ObjectWriter writerFor(Class type) {
        return mapper.writerFor(type);
    }
}