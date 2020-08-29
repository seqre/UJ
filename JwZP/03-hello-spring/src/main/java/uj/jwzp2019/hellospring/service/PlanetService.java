package uj.jwzp2019.hellospring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import uj.jwzp2019.hellospring.exception.HSIllegalArgumentException;
import uj.jwzp2019.hellospring.model.Planet;

import java.util.Objects;
import java.util.stream.IntStream;

@Service
public class PlanetService {

    private final String starWarsApiUrl;
    private final RestTemplate restTemplate;

    @Autowired
    public PlanetService(@Value("${starwars.api.url}") String starWarsApiUrl, RestTemplate restTemplate) {
        this.starWarsApiUrl = starWarsApiUrl;
        this.restTemplate = restTemplate;
    }

    public Planet getPlanetById(int id) {
        if (id < 1) throw new HSIllegalArgumentException("id must be bigger than 0");

        ResponseEntity<Planet> responseEntity = restTemplate.getForEntity(starWarsApiUrl + "/planets/" + id + "?format=json", Planet.class);
        return responseEntity.getBody();
    }

    public Planet getPlanetByUrl(String url) {
        ResponseEntity<Planet> responseEntity = restTemplate.getForEntity(url, Planet.class);
        return responseEntity.getBody();
    }

    public Planet getSmallestPlanetInRange(int fromId, int toId) {
        if (fromId > toId) throw new HSIllegalArgumentException("fromId cannot be bigger than toId");
        if (fromId < 1) throw new HSIllegalArgumentException("fromId must be bigger than 0");

        return IntStream.range(fromId, toId + 1)
                .mapToObj(this::getPlanetById)
                .filter(Objects::nonNull)
                .min((o1, o2) -> (int) (o1.getDiameter() - o2.getDiameter()))
                .orElse(null);
    }
}
