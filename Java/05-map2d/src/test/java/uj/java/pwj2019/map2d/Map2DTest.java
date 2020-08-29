package uj.java.pwj2019.map2d;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

class Map2DTest {

    private static final String rowKeyA = "A";
    private static final int colKey1 = 1;
    private static final String rowKeyB = "B";
    private static final int colKey2 = 2;
    private static final double valA1 = 2.3;
    private static final double valA2 = 2.4;
    private static final double valB1 = 2.5;
    private static final double valB2 = 2.6;

    @Test
    void putGet() {
        Map2D<String, Integer, Double> map = Map2D.createInstance();
        String rowKey1 = "A";
        int colKey1 = 1;
        double val1 = 2.3;
        assertNull(map.get(rowKey1, colKey1));
        Double oldVal = map.put(rowKey1, colKey1, val1);
        assertNull(oldVal);
        assertEquals(val1, map.get(rowKey1, colKey1));
    }

    @Test
    void putThrowsNPE() {
        Map2D<String, Integer, Double> map = Map2D.createInstance();
        try {
            map.put("X", null, 3.14);
            failBecauseExceptionWasNotThrown(NullPointerException.class);
        } catch(NullPointerException e) {} finally {}
        try {
            map.put(null, 17, 3.14);
            failBecauseExceptionWasNotThrown(NullPointerException.class);
        } catch(NullPointerException e) {} finally {}

    }

    @Test
    void getOrDefault() {
        Map2D<String, Integer, Double> map = Map2D.createInstance();
        String rowKey1 = "A";
        int colKey1 = 1;
        double val1 = 2.3;
        double defaultVal = 6.8;
        map.put(rowKey1, colKey1, val1);
        Double shouldBeFromMap = map.getOrDefault("A", 1, defaultVal);
        Double shouldBeDefault = map.getOrDefault("B", 1, defaultVal);
        assertEquals(val1, shouldBeFromMap);
        assertEquals(defaultVal, shouldBeDefault);
    }

    @Test
    void remove() {
        Map2D<String, Integer, Double> map = Map2D.createInstance();
        String rowKey1 = "A";
        int colKey1 = 1;
        double val1 = 2.3;
        Double result = map.remove(rowKey1, colKey1);
        assertNull(result);
        map.put(rowKey1, colKey1, val1);
        result = map.remove(rowKey1, colKey1);
        assertEquals(val1, result);
    }

    @Test
    void emptyClearSize() {
        Map2D<String, Integer, Double> map = Map2D.createInstance();
        assertTrue(map.isEmpty());
        assertFalse(map.nonEmpty());
        assertEquals(0, map.size());

        map.put("A", 1, 2.3);
        assertFalse(map.isEmpty());
        assertTrue(map.nonEmpty());
        assertEquals(1, map.size());

        IntStream.range(0, 100).forEach(i -> map.put("X", i, 2.3));
        assertFalse(map.isEmpty());
        assertTrue(map.nonEmpty());
        assertEquals(101, map.size());

        map.clear();
        assertTrue(map.isEmpty());
        assertFalse(map.nonEmpty());
        assertEquals(0, map.size());
    }

    @Test
    void rowView() {
        Map2D<String, Integer, Double> map = create2x2map();

        Map<Integer, Double> resultA = map.rowView("A");
        Map<Integer, Double> resultB = map.rowView("B");
        assertThat(resultA).size().isEqualTo(2);
        assertThat(resultB).size().isEqualTo(2);
        assertThat(resultA.get(colKey1)).isEqualTo(valA1);
        assertThat(resultA.get(colKey2)).isEqualTo(valA2);
        assertThat(resultB.get(colKey1)).isEqualTo(valB1);
        assertThat(resultB.get(colKey2)).isEqualTo(valB2);
    }

    @Test
    void columnView() {
        Map2D<String, Integer, Double> map = create2x2map();

        Map<String, Double> result1 = map.columnView(1);
        Map<String, Double> result2 = map.columnView(2);
        assertThat(result1).size().isEqualTo(2);
        assertThat(result2).size().isEqualTo(2);
        assertThat(result1.get(rowKeyA)).isEqualTo(valA1);
        assertThat(result2.get(rowKeyA)).isEqualTo(valA2);
        assertThat(result1.get(rowKeyB)).isEqualTo(valB1);
        assertThat(result2.get(rowKeyB)).isEqualTo(valB2);
    }

    @Test
    void hasValueRowColumnKey() {
        Map2D<String, Integer, Double> map = Map2D.createInstance();
        String rowKeyA = "A";
        int colKey1 = 1;
        String rowKeyB = "B";
        int colKey2 = 2;
        double valA1 = 2.3;

        assertFalse(map.hasValue(valA1));
        assertFalse(map.hasKey(rowKeyA, colKey1));
        assertFalse(map.hasRow(rowKeyA));
        assertFalse(map.hasColumn(colKey1));
        assertFalse(map.hasKey(rowKeyB, colKey2));
        assertFalse(map.hasRow(rowKeyB));
        assertFalse(map.hasColumn(colKey2));
        assertFalse(map.hasKey(rowKeyA, colKey2));
        assertFalse(map.hasKey(rowKeyB, colKey1));

        map.put(rowKeyA, colKey1, valA1);

        assertTrue(map.hasValue(valA1));
        assertTrue(map.hasKey(rowKeyA, colKey1));
        assertTrue(map.hasRow(rowKeyA));
        assertTrue(map.hasColumn(colKey1));
        assertFalse(map.hasKey(rowKeyB, colKey2));
        assertFalse(map.hasRow(rowKeyB));
        assertFalse(map.hasColumn(colKey2));
        assertFalse(map.hasKey(rowKeyA, colKey2));
        assertFalse(map.hasKey(rowKeyB, colKey1));
    }

    @Test
    void rowMapView() {
        Map2D<String, Integer, Double> map = create2x2map();

        Map<String, Map<Integer, Double>> result = map.rowMapView();
        assertThat(result.size()).isEqualTo(2);
        Map<Integer, Double> resultA = result.get(rowKeyA);
        assertThat(resultA.size()).isEqualTo(2);
        assertThat(resultA.get(colKey1)).isEqualTo(valA1);

        map.remove("A", 1);
        assertThat(result.size()).isEqualTo(2);
        resultA = result.get(rowKeyA);
        assertThat(resultA.size()).isEqualTo(2);
        assertThat(resultA.get(colKey1)).isEqualTo(valA1);
    }

    @Test
    void columnMapView() {
        Map2D<String, Integer, Double> map = create2x2map();

        Map<Integer, Map<String, Double>> result = map.columnMapView();
        assertThat(result.size()).isEqualTo(2);
        Map<String, Double> result1 = result.get(colKey1);
        assertThat(result1.size()).isEqualTo(2);
        assertThat(result1.get(rowKeyA)).isEqualTo(valA1);

        map.remove("A", 1);
        assertThat(result.size()).isEqualTo(2);
        result1 = result.get(colKey1);
        assertThat(result1.size()).isEqualTo(2);
        assertThat(result1.get(rowKeyA)).isEqualTo(valA1);
    }

    @Test
    void fillMapFromRow() {
        Map2D<String, Integer, Double> map = create2x2map();

        HashMap<Integer, Double> toFill = new HashMap<>();
        map.fillMapFromRow(toFill, "A");

        assertThat(toFill).size().isEqualTo(2);
        assertThat(toFill.get(colKey1)).isEqualTo(valA1);
        assertThat(toFill.get(colKey2)).isEqualTo(valA2);

        map.fillMapFromRow(toFill, "C");
        assertThat(toFill).size().isEqualTo(2);
    }

    @Test
    void fillMapFromColumn() {
        Map2D<String, Integer, Double> map = create2x2map();

        HashMap<String, Double> toFill = new HashMap<>();
        map.fillMapFromColumn(toFill, 1);

        assertThat(toFill).size().isEqualTo(2);
        assertThat(toFill.get(rowKeyA)).isEqualTo(valA1);
        assertThat(toFill.get(rowKeyB)).isEqualTo(valB1);

        map.fillMapFromColumn(toFill, 3);
        assertThat(toFill).size().isEqualTo(2);
    }

    @Test
    void putAll() {
        Map2D<String, Integer, Double> map = create2x2map();

        Map2D<String, Integer, Double> map2 = Map2D.createInstance();
        map2.putAll(map);
        assertEquals(4, map2.size());
        assertEquals(valA1, map2.get(rowKeyA, colKey1));
        assertEquals(valA2, map2.get(rowKeyA, colKey2));
        assertEquals(valB1, map2.get(rowKeyB, colKey1));
        assertEquals(valB2, map2.get(rowKeyB, colKey2));
    }

    @Test
    void putAllToRow() {
        Map2D<String, Integer, Double> map = create2x2map();

        double newVal1 = 2.7;
        double newVal3 = 2.8;
        HashMap<Integer, Double> source = new HashMap<>();
        source.put(1, newVal1);
        source.put(3, newVal3);
        map.putAllToRow(source, "A");

        assertEquals(5, map.size());
        assertEquals(newVal1, map.get("A", 1));
        assertEquals(valA2, map.get("A", 2));
        assertEquals(newVal3, map.get("A", 3));
    }

    @Test
    void putAllToColumn() {
        Map2D<String, Integer, Double> map = create2x2map();

        double newVal1 = 2.7;
        double newVal3 = 2.8;
        HashMap<String, Double> source = new HashMap<>();
        source.put("A", newVal1);
        source.put("C", newVal3);
        map.putAllToColumn(source, 1);

        assertEquals(5, map.size());
        assertEquals(newVal1, map.get("A", 1));
        assertEquals(valB1, map.get("B", 1));
        assertEquals(newVal3, map.get("C", 1));
    }

    @Test
    void copyWithConversion() {
        Map2D<String, Integer, Double> map = create2x2map();
        Map2D<String, Long, String> target = map.copyWithConversion(String::toLowerCase, Map2DTest::multiply, d -> d.toString());

        assertEquals(4, target.size());
        assertTrue(target.hasRow("a"));
        assertTrue(target.hasRow("b"));
        assertFalse(target.hasRow("A"));
        assertFalse(target.hasRow("B"));
        assertTrue(target.hasColumn(1L));
        assertTrue(target.hasColumn(4L));
        assertFalse(target.hasColumn(2L));
    }

    private static Long multiply(Integer x) {
        return (long) x * (long) x;
    }

    @Test
    void copyWithConversionMerge() {
        Map2D<String, Integer, Double> map = create2x2map();
        Map2D<String, Integer, Double> target = map.copyWithConversion(s -> "x", i -> 1, Function.identity());

        assertEquals(1, target.size());
        assertTrue(target.hasRow("x"));
        assertFalse(target.hasRow("A"));
        assertFalse(target.hasRow("B"));
        assertTrue(target.hasColumn(1));
        assertFalse(target.hasColumn(2));
    }

    static Map2D<String, Integer, Double> create2x2map() {
        Map2D<String, Integer, Double> map = Map2D.createInstance();
        map.put(rowKeyA, colKey1, valA1);
        map.put(rowKeyA, colKey2, valA2);
        map.put(rowKeyB, colKey1, valB1);
        map.put(rowKeyB, colKey2, valB2);
        return map;
    }

}
