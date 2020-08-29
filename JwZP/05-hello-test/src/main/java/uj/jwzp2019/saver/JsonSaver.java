package uj.jwzp2019.saver;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.utils.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@Qualifier("json")
public class JsonSaver implements Saver {

    private final ObjectMapper mapper;
    private final SystemUtils systemUtils;

    @Autowired
    public JsonSaver(ObjectMapper mapper, SystemUtils systemUtils) {
        this.mapper = mapper;
        this.systemUtils = systemUtils;
    }

    public void saveList(List<Person> list, String fileName) throws IOException {
        mapper.writer(new DefaultPrettyPrinter()).writeValue(new File(systemUtils.getUserDir() + fileName), list);
    }
}
