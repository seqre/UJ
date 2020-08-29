package uj.jwzp2019.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.model.Planet;
import uj.jwzp2019.service.exceptions.HTIllegalArgumentException;
import uj.jwzp2019.service.exceptions.HTRestClientException;

@Service
public class PeopleService {

    private final String starWarsApiUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public PeopleService(@Value("${starwars.api.url}") String starWarsApiUrl, RestTemplate restTemplate) {
        this.starWarsApiUrl = starWarsApiUrl;
        this.restTemplate = restTemplate;
    }

    public Person getPersonById(int id) {
        if (id < 1) throw new HTIllegalArgumentException("id must be bigger than 0");

        try {
            Person person = restTemplate.getForObject(starWarsApiUrl + "people/" + id, Person.class);
            if (person != null) {
                person.setPlanet(restTemplate.getForObject(person.getHomeworld(), Planet.class));
            }
            return person;
        } catch (RestClientException ex) {
            throw new HTRestClientException(starWarsApiUrl + " is unavailable at the moment", ex);
        }
    }
}
