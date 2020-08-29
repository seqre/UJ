package uj.jwzp.gradleplay;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        if (ArrayUtils.isEmpty(args)) {
            System.out.println(StringUtils.capitalize("hello world"));
        } else {
            Properties props = loadProperties();
            props.forEach(Main::print);
        }
    }

    private static Properties loadProperties() {
        Properties result = new Properties();
        try {
            result.load(Main.class.getResourceAsStream("/db.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private static void print(Object key, Object value) {
        System.out.println(key + "=" + value);
    }
}
