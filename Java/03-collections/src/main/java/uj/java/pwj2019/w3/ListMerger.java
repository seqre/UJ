package uj.java.pwj2019.w3;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ListMerger {
    public static List<Object> mergeLists(List<?> l1, List<?> l2) {
        if (l1 == null && l2 == null) {
            return new LinkedList<>();
        } else if (l1 != null && l2 != null) {
            LinkedList<Object> result = new LinkedList<>();

            int i = 0;
            int j = 0;

            while (Math.min(i, j) < Math.min(l1.size(), l2.size())) {
                if (i == j) {
                    result.add(l1.get(i++));
                } else {
                    result.add(l2.get(j++));
                }
            }

            while (i < l1.size()) {
                result.add(l1.get(i++));
            }

            while (j < l2.size()) {
                result.add(l2.get(j++));
            }

            return Collections.unmodifiableList(result);
        } else {
            if (l1 == null) {
                return Collections.unmodifiableList(l2);
            } else {
                return Collections.unmodifiableList(l1);
            }
        }


        /*return IntStream.range(0, Math.min(l1.size(), l2.size()))
                .boxed()
                .collect(Collectors.toMap(l1::get, l2::get))
                .entrySet()
                .stream()
                .map(x -> List.of(x.getKey(), x.getValue()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    */
    }
}
