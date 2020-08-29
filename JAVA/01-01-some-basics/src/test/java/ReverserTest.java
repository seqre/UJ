import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static org.junit.jupiter.api.Assertions.*;

class ReverserTest {

    @ParameterizedTest
    @CsvFileSource(resources = "reverse.csv", numLinesToSkip = 1)
    void reverse(String input, String expected) {
        String result = new Reverser().reverse(input);
        assertEquals(expected, result);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "reverse-words.csv", numLinesToSkip = 1)
    void reverseWords(String input, String expected) {
        String result = new Reverser().reverseWords(input);
        assertEquals(expected, result);
    }

}
