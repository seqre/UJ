package uj.jwzp2019.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.saver.Saver;
import uj.jwzp2019.service.PeopleService;
import uj.jwzp2019.service.exceptions.HTIllegalArgumentException;
import uj.jwzp2019.utils.SystemUtils;

import java.io.IOException;
import java.util.List;

@RestController
public class SaveController {

    private static String PREFIX;
    private final PeopleService peopleService;
    @Qualifier("json")
    private final Saver jsonSaver;
    @Qualifier("yaml")
    private final Saver yamlSaver;
    private final SystemUtils systemUtils;

    @Autowired
    public SaveController(PeopleService peopleService, @Qualifier("json") Saver jsonSaver, @Qualifier("yaml") Saver yamlSaver, SystemUtils systemUtils) {
        this.peopleService = peopleService;
        this.jsonSaver = jsonSaver;
        this.yamlSaver = yamlSaver;
        this.systemUtils = systemUtils;
        PREFIX = "/" + systemUtils.getSystemPrefix();
    }

    @RequestMapping("/prefix")
    public void changePrefix(@RequestParam String prefix) {
        PREFIX = "/" + prefix;
    }

    @RequestMapping("/save")
    public String saveToFiles(@RequestParam(value = "id", defaultValue = "1") int id) throws IOException {
        if (id < 1) throw new HTIllegalArgumentException("id must be bigger than 0");

        String fileName = PREFIX + systemUtils.getTime();

        Person person = peopleService.getPersonById(id);
        person.setEye_color("pink");

        jsonSaver.saveList(List.of(person), fileName + ".json");
        yamlSaver.saveList(List.of(person), fileName + ".yaml");
        return "Data successfully saved";
    }
}
