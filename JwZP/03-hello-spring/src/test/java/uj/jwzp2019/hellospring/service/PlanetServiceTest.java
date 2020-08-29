package uj.jwzp2019.hellospring.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import uj.jwzp2019.hellospring.model.Person;
import uj.jwzp2019.hellospring.model.Planet;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class PlanetServiceTest {

    private static final String FAKE_URL_API = "http://fake.url/api/";
    private static final int FROM_ID = 1;
    private static final int TO_ID = 2;
    private static final String TATOOINE = "Tatooine";
    private static final String KORELIA = "Korelia";

    @Mock
    private RestTemplate restTemplate;

    @Test
    void getPlanetByIdReturnsPlanet() {
        // given
        Planet planet = new Planet();
        planet.setName(TATOOINE);
        PlanetService planetService = new PlanetService(FAKE_URL_API, restTemplate);
        given(restTemplate.getForObject(FAKE_URL_API + "planets/" + FROM_ID, Planet.class)).willReturn(planet);
        // when
        Planet testPlanet = planetService.getPlanetById(FROM_ID);
        // then
        assertThat(testPlanet.getName()).isEqualTo(TATOOINE);
    }

    @Test
    void getPlanetByUrlReturnsPlanet() {
        // given
        Planet planet = new Planet();
        planet.setName(TATOOINE);
        PlanetService planetService = new PlanetService(FAKE_URL_API, restTemplate);
        given(restTemplate.getForObject(FAKE_URL_API + "planets/" + FROM_ID, Planet.class)).willReturn(planet);
        // when
        Planet testPlanet = planetService.getPlanetByUrl(FAKE_URL_API  + "planets/" + FROM_ID);
        // then
        assertThat(testPlanet.getName()).isEqualTo(TATOOINE);

    }

    @Test
    void getSmallestPlanetInRangeReturnsSmallestPlanet() {
        // given
        Planet tatooine = new Planet();
        tatooine.setName(TATOOINE);
        tatooine.setDiameter(1000);
        Planet korelia = new Planet();
        korelia.setName(KORELIA);
        korelia.setDiameter(100);
        PlanetService planetService = new PlanetService(FAKE_URL_API, restTemplate);
        given(restTemplate.getForObject(anyString(), any())).willReturn(tatooine, korelia);
        // when
        Planet smallest = planetService.getSmallestPlanetInRange(FROM_ID, TO_ID);
        // then
        assertThat(smallest.getName()).isEqualTo(KORELIA);
    }

    @Test
    void getSmallestPlanetInRangeReturnsFirstPlanetIfEqual() {
        // given
        Planet tatooine = new Planet();
        tatooine.setName(TATOOINE);
        tatooine.setDiameter(100);
        Planet korelia = new Planet();
        korelia.setName(KORELIA);
        korelia.setDiameter(100);
        PlanetService planetService = new PlanetService(FAKE_URL_API, restTemplate);
        given(restTemplate.getForObject(anyString(), any())).willReturn(tatooine, korelia);
        // when
        Planet smallest = planetService.getSmallestPlanetInRange(FROM_ID, TO_ID);
        // then
        assertThat(smallest.getName()).isEqualTo(TATOOINE);
    }

}