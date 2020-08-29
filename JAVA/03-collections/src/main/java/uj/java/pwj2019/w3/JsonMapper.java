package uj.java.pwj2019.w3;

import java.util.Map;

public interface JsonMapper {

    String toJson(Map<String, ?> map);

    static JsonMapper defaultInstance() {
        return new JsonCreator();
    }

}
