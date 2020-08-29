package uj.jwzp2020.veterinaryclinic.utils;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class TimeUtils {
    public LocalDate getNow() {
        return LocalDate.now();
    }
}
