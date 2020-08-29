package uj.java.pwj2019.map2d;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ImplMap2D<R, C, V> implements Map2D<R, C, V> {
    private Map<Coords<R, C>, V> map;

    public ImplMap2D() {
        this.map = new HashMap<>();
    }

    @Override
    public V put(R rowKey, C columnKey, V value) {
        V result = map.get(new Coords<>(rowKey, columnKey));
        map.put(new Coords<>(rowKey, columnKey), value);
        return result;
    }

    @Override
    public V get(R rowKey, C columnKey) {
        return map.get(new Coords<>(rowKey, columnKey));
    }

    @Override
    public V getOrDefault(R rowKey, C columnKey, V defaultValue) {
        return map.getOrDefault(new Coords<>(rowKey, columnKey), defaultValue);
    }

    @Override
    public V remove(R rowKey, C columnKey) {
        return map.remove(new Coords<>(rowKey, columnKey));
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean nonEmpty() {
        return !map.isEmpty();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Map<C, V> rowView(R rowKey) {
        Map<C, V> result = new HashMap<>();
        map.keySet()
                .stream()
                .filter((key) -> key.row.equals(rowKey))
                .forEach((key) -> result.put(key.col, map.get(key)));

        return Collections.unmodifiableMap(result);
    }

    @Override
    public Map<R, V> columnView(C columnKey) {
        Map<R, V> result = new HashMap<>();
        map.keySet()
                .stream()
                .filter((key) -> key.col.equals(columnKey))
                .forEach((key) -> result.put(key.row, map.get(key)));

        return Collections.unmodifiableMap(result);
    }

    @Override
    public boolean hasValue(V value) {
        return map.containsValue(value);
    }

    @Override
    public boolean hasKey(R rowKey, C columnKey) {
        return map.containsKey(new Coords<>(rowKey, columnKey));
    }

    @Override
    public boolean hasRow(R rowKey) {
        return map.keySet()
                .stream()
                .anyMatch(rcCoords -> rcCoords.row.equals(rowKey));
    }

    @Override
    public boolean hasColumn(C columnKey) {
        return map.keySet()
                .stream()
                .anyMatch(rcCoords -> rcCoords.col.equals(columnKey));
    }

    @Override
    public Map<R, Map<C, V>> rowMapView() {
        Map<R, Map<C, V>> result = new HashMap<>();
        map.keySet()
                .stream()
                .filter((rcCoords -> !result.containsKey(rcCoords.row)))
                .forEach(rcCoords -> result.compute(rcCoords.row, (key, value) -> new HashMap<>()));

        map.forEach((key, value) -> result.get(key.row).put(key.col, value));

        return result;
    }

    @Override
    public Map<C, Map<R, V>> columnMapView() {
        Map<C, Map<R, V>> result = new HashMap<>();
        map.keySet()
                .stream()
                .filter((rcCoords -> !result.containsKey(rcCoords.col)))
                .forEach(rcCoords -> result.compute(rcCoords.col, (key, value) -> new HashMap<>()));

        map.forEach((key, value) -> result.get(key.col).put(key.row, value));

        return result;
    }

    @Override
    public Map2D<R, C, V> fillMapFromRow(Map<? super C, ? super V> target, R rowKey) {
        map.keySet()
                .stream()
                .filter((key) -> key.row.equals(rowKey))
                .forEach((key) -> target.put(key.col, map.get(key)));

        return this;
    }

    @Override
    public Map2D<R, C, V> fillMapFromColumn(Map<? super R, ? super V> target, C columnKey) {
        map.keySet()
                .stream()
                .filter((key) -> key.col.equals(columnKey))
                .forEach((key) -> target.put(key.row, map.get(key)));

        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Map2D<R, C, V> putAll(Map2D<? extends R, ? extends C, ? extends V> source) {
        ((ImplMap2D<? extends R, ? extends C, ? extends V>) source)
                .map
                .forEach((key, value) -> map.put((Coords<R, C>) key, value));
        return this;
    }

    @Override
    public Map2D<R, C, V> putAllToRow(Map<? extends C, ? extends V> source, R rowKey) {
        source.forEach((key, value) -> map.put(new Coords<>(rowKey, key), value));
        return this;
    }

    @Override
    public Map2D<R, C, V> putAllToColumn(Map<? extends R, ? extends V> source, C columnKey) {
        source.forEach((key, value) -> map.put(new Coords<>(key, columnKey), value));
        return this;
    }

    @Override
    public <R2, C2, V2> Map2D<R2, C2, V2> copyWithConversion(
            Function<? super R, ? extends R2> rowFunction,
            Function<? super C, ? extends C2> columnFunction,
            Function<? super V, ? extends V2> valueFunction) {
        Map2D<R2, C2, V2> copy = new ImplMap2D<>();
        map.forEach((key, value) -> copy.put(rowFunction.apply(key.row), columnFunction.apply(key.col), valueFunction.apply(value)));

        return copy;
    }

    private class Coords<X, Y> {
        private X row;
        private Y col;

        public Coords(X row, Y col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public int hashCode() {
            int result = row.hashCode();
            result = 31 * result + col.hashCode();
            return result;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object obj) {
            return obj instanceof Coords
                    && ((Coords) obj).row.equals(this.row)
                    && ((Coords) obj).col.equals(this.col);
        }
    }
}
