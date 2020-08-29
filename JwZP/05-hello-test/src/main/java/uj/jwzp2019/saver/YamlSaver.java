package uj.jwzp2019.saver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.Yaml;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.utils.SystemUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
@Qualifier("yaml")
public class YamlSaver implements Saver {

    private final SystemUtils systemUtils;

    @Autowired
    public YamlSaver(SystemUtils systemUtils) {
        this.systemUtils = systemUtils;
    }

    public void saveList(List<Person> list, String fileName) throws IOException {
        Yaml yaml = new Yaml();
        yaml.dump(list, new FileWriter(systemUtils.getUserDir() + fileName));
    }
}
