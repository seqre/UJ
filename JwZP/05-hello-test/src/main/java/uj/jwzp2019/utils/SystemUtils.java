package uj.jwzp2019.utils;

import org.springframework.stereotype.Component;

@Component
public class SystemUtils {

    public long getTime() {
        return System.currentTimeMillis();
    }

    public String getSystemPrefix() {
        return System.getProperty("PREFIX");
    }

    public String getUserDir() {
        return System.getProperty("user.dir");
    }
}