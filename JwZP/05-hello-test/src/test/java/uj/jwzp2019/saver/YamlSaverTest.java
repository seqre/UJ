package uj.jwzp2019.saver;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.yaml.snakeyaml.Yaml;
import uj.jwzp2019.model.Person;
import uj.jwzp2019.model.Planet;
import uj.jwzp2019.utils.SystemUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class YamlSaverTest {
    private final String FILE_NAME = "/result.json";
    private final Yaml yaml = new Yaml();

    private Person han;
    private Person leia;
    private Planet planet;

    private Path tempDir;
    private Path internalTempDir;

    @Mock
    private SystemUtils systemUtils;

    private YamlSaver yamlSaver;

    @BeforeEach
    void setUp() throws IOException {
        yamlSaver = new YamlSaver(systemUtils);

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

        var expected = yaml.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("expected.yaml")));

        // when
        yamlSaver.saveList(personList, FILE_NAME);

        // then
        File resultFile = new File(internalTempDir.toString() + "/" + FILE_NAME);
        resultFile.deleteOnExit();
        var result = yaml.load(new FileReader(resultFile));
        assertThat(result.toString()).isEqualTo(expected.toString());
    }

    @Test
    void nullInsteadOfValue() throws Exception {
        // given
        han.setName(null);
        han.setHomeworld("http://fake.url/api/planets/1");
        planet.setName("Korelia");
        han.setPlanet(planet);
        List<Person> personList = List.of(han);

        var expected = yaml.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("expected.yaml")));

        // when
        yamlSaver.saveList(personList, FILE_NAME);

        // then
        File resultFile = new File(internalTempDir.toString() + "/" + FILE_NAME);
        resultFile.deleteOnExit();
        var result = yaml.load(new FileReader(resultFile));
        assertThat(result.toString()).isNotEqualTo(expected.toString());
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

        var expected = yaml.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("expected.yaml")));
        var expectedList = yaml.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("expectedList.yaml")));

        // when
        yamlSaver.saveList(personList, FILE_NAME);

        // then
        File resultFile = new File(internalTempDir.toString() + "/" + FILE_NAME);
        resultFile.deleteOnExit();
        var result = yaml.load(new FileReader(resultFile));
        assertThat(result.toString()).isNotEqualTo(expected.toString());
        assertThat(result.toString()).isEqualTo(expectedList.toString());
    }
}
