package uj.jwzp.exam2020.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.simpleflatmapper.csv.CsvParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class DataMaker {

    private final static ObjectMapper mapper = new ObjectMapper();

    public static List read(Class<? extends WithId> clazz, String fileName) {
        try (Reader reader = new InputStreamReader((DataMaker.class.getResourceAsStream(fileName)))) {
            return CsvParser.mapTo(clazz).stream(reader).collect(toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public static String jsonSingle(List<? extends WithId> objects, int id) {
        try {
            var obj = objects.stream().filter(o -> o.id() == id).findFirst();
            return mapper.writeValueAsString(obj.orElse(null));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String jsonMulti(List<? extends WithId> objects, List<Integer> ids) {
        var sublist = objects.stream().filter(o -> ids.contains(o.id())).collect(toList());
        try {
            return mapper.writeValueAsString(sublist);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }
    }

}
