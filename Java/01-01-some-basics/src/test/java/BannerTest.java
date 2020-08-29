import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.of;

class BannerTest {

    @ParameterizedTest
    @MethodSource("toBannerInput")
    void toBanner(String input, List<String> expected) throws IOException {
        String[] result = new Banner().toBanner(input);
        for (int i = 0; i < expected.size(); i++) {
            System.out.println(expected.get(i));
            assertEquals(expected.get(i), result[i]);
        }
    }

    @Test
    void toBannerNull() throws IOException {
        String[] result = new Banner().toBanner(null);
        assertEquals(0, result.length);
    }


    static Stream<Arguments> toBannerInput() {
        return Stream.of(
            of("Ala", readBanner("ala")),
            of("ABCDEFGHIJKLMNOPQRSTUWVXYZ", readBanner("alfabet")),
            of("abcdefGhijklMnopqrstuwvxYz", readBanner("alfabet")),
            of("Ala ma kota", readBanner("ala-ma-kota"))
        );
    }

    private static List<String> readBanner(String name) {
        InputStream input = BannerTest.class.getResourceAsStream("banners/" + name + ".txt");
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        return br.lines().collect(Collectors.toList());
    }

}