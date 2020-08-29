package uj.jwzp2019.hellospring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import uj.jwzp2019.hellospring.model.Person;
import uj.jwzp2019.hellospring.model.Planet;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PeopleServiceTest {

    private static final String KORELIA = "Korelia";
    private static final String FAKE_URL_API = "http://fake.url/api/";
    private static final String HOME_WORLD = "http://fake.url/api/planets/1";
    private static final String BROWN = "BROWN";
    private static final String BLUE = "BLUE";
    private static final String LUKE_SKYWALKER = "Luke Skywalker";
    private static final String HAN_SOLO = "Han Solo";
    private static final int FROM_ID = 1;
    private static final int TO_ID = 2;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PlanetService planetService;

    @Test
    void getPersonByIdReturnsPersonWithPlanet() {
        // given
        Person person = new Person();
        Planet planet = new Planet();
        planet.setName(KORELIA);
        person.setName(HAN_SOLO);
        person.setHomeworld(HOME_WORLD);
        PeopleService peopleService = new PeopleService(FAKE_URL_API, restTemplate, planetService);
        given(restTemplate.getForObject(FAKE_URL_API + "people/" + FROM_ID, Person.class)).willReturn(person);
        given(planetService.getPlanetByUrl(HOME_WORLD)).willReturn(planet);
        // when
        Person person1 = peopleService.getPersonById(FROM_ID);
        // then
        assertThat(person1.getPlanet().getName()).isEqualTo(KORELIA);
    }

    @Test
    void getPeopleInRangeWithEyeColorReturnsEmptyListWhenRangeEmpty() {
        PeopleService peopleService = new PeopleService(FAKE_URL_API, restTemplate, planetService);
        // when
        List<Person> blueEyedPeople = peopleService.getPeopleInRangeWithEyeColor(FROM_ID, 0, BLUE);
        //then
        assertThat(blueEyedPeople).isEmpty();
    }

    @Test
    void getPeopleInRangeWithEyeColorReturnsEmptyListWhenNobodyMatches() {
        Person han = new Person();
        han.setName(HAN_SOLO);
        han.setEye_color(BROWN);
        han.setHomeworld(HOME_WORLD);
        Person luke = new Person();
        luke.setName(LUKE_SKYWALKER);
        luke.setHomeworld(HOME_WORLD);
        luke.setEye_color(BROWN);
        Planet planet = new Planet();
        PeopleService peopleService = new PeopleService(FAKE_URL_API, restTemplate, planetService);
        given(restTemplate.getForObject(anyString(), any())).willReturn(han, luke);
        given(planetService.getPlanetByUrl(HOME_WORLD)).willReturn(planet);
        // when
        List<Person> blueEyedPeople = peopleService.getPeopleInRangeWithEyeColor(FROM_ID, TO_ID, BLUE);
        //then
        assertThat(blueEyedPeople).isEmpty();
    }

    @Test
    void getPeopleInRangeWithEyeColorReturnsOnlyMatchingPeople() {
        Person han = new Person();
        han.setName(HAN_SOLO);
        han.setEye_color(BROWN);
        han.setHomeworld(HOME_WORLD);
        Person luke = new Person();
        luke.setName(LUKE_SKYWALKER);
        luke.setHomeworld(HOME_WORLD);
        luke.setEye_color(BLUE);
        Planet planet = new Planet();
        PeopleService peopleService = new PeopleService(FAKE_URL_API, restTemplate, planetService);
        given(restTemplate.getForObject(anyString(), any())).willReturn(han, luke);
        given(planetService.getPlanetByUrl(HOME_WORLD)).willReturn(planet);
        // when
        List<Person> blueEyedPeople = peopleService.getPeopleInRangeWithEyeColor(FROM_ID, TO_ID, BLUE);
        //then
        assertThat(blueEyedPeople).extracting("name").containsOnly(LUKE_SKYWALKER);
    }

    @Test
    void getPeopleInRangeWithEyeColorReturnsAllMatchingPeople() {
        Person han = new Person();
        han.setName(HAN_SOLO);
        han.setEye_color(BLUE);
        han.setHomeworld(HOME_WORLD);

        Person luke = new Person();
        luke.setName(LUKE_SKYWALKER);
        luke.setHomeworld(HOME_WORLD);
        luke.setEye_color(BLUE);

        Planet planet = new Planet();
        PeopleService peopleService = new PeopleService(FAKE_URL_API, restTemplate, planetService);
        given(restTemplate.getForObject(anyString(), any())).willReturn(han, luke);
        given(planetService.getPlanetByUrl(HOME_WORLD)).willReturn(planet);
        // when
        List<Person> blueEyedPeople = peopleService.getPeopleInRangeWithEyeColor(FROM_ID, TO_ID, BLUE);
        //then
        assertThat(blueEyedPeople).extracting("name").containsOnly(LUKE_SKYWALKER, HAN_SOLO);
    }


}