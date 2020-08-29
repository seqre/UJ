package uj.java.pwj2019.w3;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONCompareMode;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static uj.java.pwj2019.w3.JsonMapper.defaultInstance;

class JsonMapperTest {

    @Test
    void emptyJsonFromNull() throws JSONException {
        String result = defaultInstance().toJson(null);
        assertEquals("{}", result, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    void emptyJsonFromEmptyMap() throws JSONException {
        String result = defaultInstance().toJson(Collections.emptyMap());
        assertEquals("{}", result, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    void singleStringField() throws JSONException {
        String result = defaultInstance().toJson(Map.of("key", "value"));
        assertEquals(getJson("single-string-field.json"), result, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    void simpleArray() throws JSONException {
        String result = defaultInstance().toJson(Map.of("lista", List.of("a", "b", "c", "d", "e")));
        assertEquals(getJson("simple-array.json"), result, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    void colors() throws JSONException {
        final var m = Map.of("colors", List.of(
            Map.of("color", "black", "category", "hue", "type", "primary", "code", Map.of("rgba", List.of(255,255,255,1), "hex", "#000")),
            Map.of("color", "white", "category", "value", "code", Map.of("rgba", List.of(0,0,0,1), "hex", "#FFF")),
            Map.of("color", "red", "category", "hue", "type", "primary", "code", Map.of("rgba", List.of(255,0,0,1), "hex", "#FF0")),
            Map.of("color", "blue", "category", "hue", "type", "primary", "code", Map.of("rgba", List.of(0,0,255,1), "hex", "#00F")),
            Map.of("color", "yellow", "category", "hue", "type", "primary", "code", Map.of("rgba", List.of(255,255,0,1), "hex", "#FF0")),
            Map.of("color", "green", "category", "hue", "type", "secondary", "code", Map.of("rgba", List.of(0,255,0,1), "hex", "#0F0"))
        ));
        String result = defaultInstance().toJson(m);
        assertEquals(getJson("colors.json"), result, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    void googleMap() throws JSONException {
        final var m = Map.of("markers", List.of(
            Map.of("name", "Rixos The Palm Dubai", "position", List.of(25.1212, 55.1535)),
            Map.of("name", "Shangri-La Hotel", "location", List.of(25.2084, 55.2719)),
            Map.of("name", "Grand Hyatt", "location", List.of(25.2285, 55.3273))
        ));
        String result = defaultInstance().toJson(m);
        assertEquals(getJson("gmap.json"), result, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    void twitter() throws JSONException {
        final var m = Map.of("tweet", Map.of(
            "created_at", "Tue Oct 22 19:18:00 +0000 2019",
            "id", 372994604561387500L,
            "id_str", "877994604561387500",
            "text", "Java lessons at UJ are the best!",
            "truncated", false,
            "source", "<a href=\"http://uj.edu.pl\" rel=\"nofollow\">UJ</a>",
            "entities", Map.of(
                "hashtags", List.of(Map.of("text", "Java", "indices", List.of(12,35)), Map.of("text", "UJ")),
                "symbols", Collections.emptyList(),
                "user_mentions", Collections.emptyList(),
                "urls", List.of(Map.of(
                        "url", "https://uj.edu.pl",
                        "expanded_url", "https://uj.edu.pl",
                        "display_url", "uj.edu.pl",
                        "indices", List.of(79, 102)))
            ),
            "user", Map.of(
                "id", 1726829649L,
                "id_str", "1726829649",
                "name", "Arek Sokołowski",
                "screen_name", "Arek Sokołowski",
                "location", "Kraków, Polska",
                "protected", false,
                "followers_count", 2145,
                "friends_count", 18,
                "listed_count", 328,
                "favourites_count", 57
            )
        ));
        String result = defaultInstance().toJson(m);
        assertEquals(getJson("twitter.json"), result, JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    private String getJson(String fileName) throws JSONException {
        final var br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
        final var builder = new StringBuilder();
        try (br) {
            br.lines().forEach(s -> builder.append(s).append('\n'));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        System.out.println(builder);
        return builder.toString();
    }

}
