package uj.java.pwj2019.w3;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonCreator implements JsonMapper {

    private final static String OBJECT_PARENTHESES = "{}";
    private final static String ARRAY_PARENTHESES = "[]";

    @Override
    public String toJson(Map<String, ?> map) {
        if (map == null || map.isEmpty()) {
            return OBJECT_PARENTHESES;
        } else {
            return parseData(map);
        }
    }

    private String parseData(Map<String, ?> map) {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<String, ?> entry : map.entrySet()) {
            result.append(createExpression(wrapWithQuotes(entry.getKey()), parseData(entry.getValue())));
            result.append(",");
        }

        if (result.length() > 0) result.deleteCharAt(result.length() - 1);

        return wrapWithTwoCharacters(result.toString(), OBJECT_PARENTHESES);
    }

    private String parseData(List<?> list) {
        StringBuilder result = new StringBuilder();
        Iterator<?> listIterator = list.iterator();

        if (listIterator.hasNext()) result.append(parseData(listIterator.next()));
        while (listIterator.hasNext()) {
            result.append(",").append(parseData(listIterator.next()));
        }

        return wrapWithTwoCharacters(result.toString(), ARRAY_PARENTHESES);
    }

    private String parseData(String string) {
        String result = checkInsideQuotes(string);
        return wrapWithQuotes(result);
    }

    private String checkInsideQuotes(String string) {
        StringBuilder result = new StringBuilder(string);

        for (int index = 0; index < result.length(); index++) {
            if (result.charAt(index) == '"') {
                result.insert(index++, '\\');
            }
        }

        return result.toString();
    }

    @SuppressWarnings("unchecked")
    private String parseData(Object object) {
        if (object instanceof List) {
            return parseData((List<?>) object);
        } else if (object instanceof Map) {
            return parseData((Map<String, ?>) object);
        } else if (object instanceof String) {
            return parseData((String) object);
        } else {
            return object.toString();
        }
    }

    private String wrapWithQuotes(String data) {
        return (new StringBuilder(data))
                .insert(0, "\"")
                .append("\"")
                .toString();
    }

    private String wrapWithTwoCharacters(String data, String characters) {
        if (characters.length() != 2) {
            return data;
        } else {
            return (new StringBuilder(data))
                    .insert(0, characters.charAt(0))
                    .append(characters.charAt(1))
                    .toString();
        }
    }

    private String createExpression(String key, String data) {
        return (new StringBuilder(key))
                .append(": ")
                .append(data)
                .toString();
    }

    private void prettyPrint(String json) {
        System.out.println("===========");
        int tab = 0;
        for (char c : json.toCharArray()) {
            if (c == '{') {
                System.out.println(c);
                System.out.print("\t".repeat(++tab));
            } else if (c == '}') {
                System.out.print(c);
                --tab;
            } else if (c == ',') {
                System.out.println(c);
                System.out.print("\t".repeat(tab));
            } else {
                System.out.print(c);
            }
        }
        System.out.println();
        System.out.println("===========");
    }
}
