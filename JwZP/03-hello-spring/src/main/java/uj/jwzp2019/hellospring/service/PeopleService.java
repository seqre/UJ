package uj.jwzp2019.hellospring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uj.jwzp2019.hellospring.exception.HSIllegalArgumentException;
import uj.jwzp2019.hellospring.model.Person;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class PeopleService {

    private final String starWarsApiUrl;
    private final RestTemplate restTemplate;
    private final PlanetService planetService;

    @Autowired
    public PeopleService(@Value("${starwars.api.url}") String starWarsApiUrl, RestTemplate restTemplate, PlanetService planetService) {
        this.starWarsApiUrl = starWarsApiUrl;
        this.restTemplate = restTemplate;
        this.planetService = planetService;
    }

    public Person getPersonById(int id) {
        if (id < 1) throw new HSIllegalArgumentException("id must be bigger than 0");

        ResponseEntity<Person> responseEntity = restTemplate.getForEntity(starWarsApiUrl + "/people/" + id + "?format=json", Person.class);
        Objects.requireNonNull(responseEntity.getBody()).setPlanet(planetService.getPlanetByUrl(responseEntity.getBody().getHomeworld()));
        return responseEntity.getBody();
    }

    public List<Person> getPeopleInRangeWithEyeColor(int fromId, int toId, String color) {
        if (fromId > toId) throw new HSIllegalArgumentException("fromId cannot be bigger than toId");
        if (fromId < 1) throw new HSIllegalArgumentException("fromId must be bigger than 0");

        return IntStream.range(fromId, toId + 1)
                .mapToObj(this::getPersonById)
                .filter(obj -> Objects.nonNull(obj) && obj.getEye_color().equals(color))
                .collect(Collectors.toList());
    }
}
