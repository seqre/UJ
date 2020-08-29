package uj.jwzp2019.hellospring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uj.jwzp2019.hellospring.model.Person;
import uj.jwzp2019.hellospring.service.PeopleService;

import java.util.List;

@RestController
public class PeopleController {

    private final PeopleService peopleService;

    @Autowired
    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @RequestMapping("/person")
    public Person getPersonById(@RequestParam(value = "id", defaultValue = "1") int id) {
        return peopleService.getPersonById(id);
    }

    @RequestMapping("/person/eye")
    public List<Person> getPeopleInRangeWithMatchingColor(@RequestParam(value = "fromId", defaultValue = "1") int fromId,
                                                          @RequestParam(value = "toId", defaultValue = "3") int toId,
                                                          @RequestParam(value = "color", defaultValue = "brown") String color) {
        return peopleService.getPeopleInRangeWithEyeColor(fromId, toId, color);
    }

}
