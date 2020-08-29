package uj.jwzp2019.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.model.Planet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class PeopleServiceTest {

    private static final String KORELIA = "Korelia";
    private static final String FAKE_URL_API = "http://fake.url/api/";
    private static final String HOME_WORLD = "http://fake.url/api/planets/1";
    private static final String HAN_SOLO = "Han Solo";
    private static final int ID = 1;

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getPersonByIdPersonNotNullTest() {
        // given
        Person person = new Person();
        Planet planet = new Planet();
        planet.setName(KORELIA);
        person.setName(HAN_SOLO);
        person.setHomeworld(HOME_WORLD);
        PeopleService peopleService = new PeopleService(FAKE_URL_API, restTemplate);
        given(restTemplate.getForObject(FAKE_URL_API + "people/" + ID, Person.class)).willReturn(person);
        given(restTemplate.getForObject(HOME_WORLD, Planet.class)).willReturn(planet);

        // when
        Person p = peopleService.getPersonById(ID);

        // then
        assertThat(p.getName()).isEqualTo(HAN_SOLO);
        assertThat(p.getPlanet().getName()).isEqualTo(KORELIA);
    }

    @Test
    void getPersonByIdPersonIsNullTest() {
        // given
        PeopleService peopleService = new PeopleService(FAKE_URL_API, restTemplate);
        given(restTemplate.getForObject(FAKE_URL_API + "people/" + ID, Person.class)).willReturn(null);

        // when
        Person p = peopleService.getPersonById(ID);

        // then
        assertThat(p).isEqualTo(null);
    }

    @Test
    void getPersonByIdRestClientExceptionHappenedTest() {
        // given
        PeopleService peopleService = new PeopleService(FAKE_URL_API, restTemplate);
        given(restTemplate.getForObject(FAKE_URL_API + "people/" + ID, Person.class)).willThrow(RestClientException.class);

        // when & then
        Assertions.assertThrows(RestClientException.class, () -> peopleService.getPersonById(ID));
    }
}
