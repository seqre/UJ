package uj.jwzp2020.veterinaryclinic.model.appointment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.stream.Stream;

public enum AppointmentLengthDTO {
    @JsonProperty("15") FIFTEEN_MINUTES(15),
    @JsonProperty("30") THIRTY_MINUTES(30),
    @JsonProperty("45") FORTY_FIVE_MINUTES(45),
    @JsonProperty("60") SIXTY_MINUTES(60);

    private final int minutes;

    AppointmentLengthDTO(int minutes) {
        this.minutes = minutes;
    }

    public static AppointmentLengthDTO of(int minutes) {
        return Stream.of(AppointmentLengthDTO.values())
                .filter(al -> al.minutes == minutes)
                .findFirst()
                .orElse(null);
    }

    public int getMinutes() {
        return minutes;
    }
}
