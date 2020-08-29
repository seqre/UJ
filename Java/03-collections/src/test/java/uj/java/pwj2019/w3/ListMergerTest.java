package uj.java.pwj2019.w3;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static uj.java.pwj2019.w3.ListMerger.mergeLists;
import static org.assertj.core.api.Assertions.assertThat;

public class ListMergerTest {

    @Test
    void nullLists() {
        List<?> result = mergeLists(null, null);
        assertThat(result).isEmpty();
    }

    @Test
    void emptyLists() {
        List<?> result = mergeLists(Collections.emptyList(), new ArrayList<>());
        assertThat(result).isEmpty();
    }

    @Test
    void oneListOnly() {
        List<Object> result = mergeLists(Collections.emptyList(), List.of("a", "b", "c"));
        assertThat(result).containsExactly("a", "b", "c");
    }

    @Test
    void twoSimpleLists() {
        List<Object> result = mergeLists(List.of("x", "y", "z"), List.of("a", "b", "c"));
        assertThat(result).containsExactly("x", "a", "y", "b", "z", "c");
    }

    @Test
    void firstListLonger() {
        List<Object> result = mergeLists(List.of("x", "y", "z", "q", "w"), List.of("a", "b", "c"));
        assertThat(result).containsExactly("x", "a", "y", "b", "z", "c", "q", "w");
    }

    @Test
    void secondListLonger() {
        List<Object> result = mergeLists(List.of("x", "y", "z"), List.of("a", "b", "c", "d", "e", "f"));
        assertThat(result).containsExactly("x", "a", "y", "b", "z", "c", "d", "e", "f");
    }

    @Test
    void checkListNotModifable() {
        List<Object> result = mergeLists(List.of("x", "y", "z"), List.of("a", "b", "c", "d"));
        try {
            result.add("SHOULD NOT ADD ME");
            result.remove(2);
        } catch (Exception ex) {
            //do nothing
        }
        assertThat(result).containsExactly("x", "a", "y", "b", "z", "c", "d");
    }
}
