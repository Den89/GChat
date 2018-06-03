package sample.utils;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class MappingUtils {

    private static ObjectMapper mapper = new ObjectMapper();

    public static ObjectReader readerFor(Class type) {
        return mapper.readerFor(type);
    }
}