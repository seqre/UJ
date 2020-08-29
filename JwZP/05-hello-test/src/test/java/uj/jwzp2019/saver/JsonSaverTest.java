package uj.jwzp2019.saver;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.model.Planet;
import uj.jwzp2019.utils.SystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class JsonSaverTest {
    private final String FILE_NAME = "/result.json";

    private Person han;
    private Person leia;
    private Planet planet;

    private ObjectMapper mapper;
    private Path tempDir;
    private Path internalTempDir;

    @Mock
    private SystemUtils systemUtils;

    private JsonSaver jsonSaver;

    @BeforeEach
    void setUp() throws IOException {
        mapper = new ObjectMapper();
        jsonSaver = new JsonSaver(mapper, systemUtils);

        tempDir = Files.createTempDirectory(Paths.get("").toAbsolutePath(), "temp");
        tempDir.toFile().deleteOnExit();

        internalTempDir = Files.createTempDirectory(tempDir.toAbsolutePath(), "json");
        internalTempDir.toFile().deleteOnExit();
        given(systemUtils.getUserDir()).willReturn(internalTempDir.toString());

        han = new Person();
        leia = new Person();
        planet = new Planet();
    }

    @Test
    void happyPath() throws Exception {
        // given
        han.setName("Han Solo");
        han.setHomeworld("http://fake.url/api/planets/1");
        planet.setName("Korelia");
        han.setPlanet(planet);
        List<Person> personList = List.of(han);

        JsonNode expected = mapper.readTree(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("expected.json")));

        // when
        jsonSaver.saveList(personList, FILE_NAME);

        // then
        File resultFile = new File(internalTempDir.toString() + "/" + FILE_NAME);
        resultFile.deleteOnExit();
        JsonNode result = mapper.readTree(resultFile);
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void nullInsteadOfValue() throws Exception {
        // given
        han.setName(null);
        han.setHomeworld("http://fake.url/api/planets/1");
        planet.setName("Korelia");
        han.setPlanet(planet);
        List<Person> personList = List.of(han);

        JsonNode expected = mapper.readTree(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("expected.json")));

        // when
        jsonSaver.saveList(personList, FILE_NAME);

        // then
        File resultFile = new File(internalTempDir.toString() + "/" + FILE_NAME);
        resultFile.deleteOnExit();
        JsonNode result = mapper.readTree(resultFile);
        assertThat(result).isNotEqualTo(expected);
    }

    @Test
    void twoPeopleSavingProperly() throws Exception {
        // given
        planet.setName("Korelia");
        han.setName("Han Solo");
        han.setHomeworld("http://fake.url/api/planets/1");
        han.setPlanet(planet);

        leia.setName("Leia");
        leia.setHomeworld("http://fake.url/api/planets/1");
        List<Person> personList = List.of(han, leia);

        JsonNode expected = mapper.readTree(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("expected.json")));
        JsonNode expectedList = mapper.readTree(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("expectedList.json")));

        // when
        jsonSaver.saveList(personList, FILE_NAME);

        // then
        File resultFile = new File(internalTempDir.toString() + "/" + FILE_NAME);
        resultFile.deleteOnExit();
        JsonNode result = mapper.readTree(resultFile);
        assertThat(result).isNotEqualTo(expected);
        assertThat(result).isEqualTo(expectedList);
    }
}
