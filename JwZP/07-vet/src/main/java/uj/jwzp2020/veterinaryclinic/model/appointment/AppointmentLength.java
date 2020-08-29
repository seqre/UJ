package uj.jwzp2020.veterinaryclinic.model.appointment;

public enum AppointmentLength {
    FIFTEEN_MINUTES(15),
    THIRTY_MINUTES(30),
    FORTY_FIVE_MINUTES(45),
    SIXTY_MINUTES(60);

    private final int minutes;

    AppointmentLength(int minutes) {
        this.minutes = minutes;
    }

    public int getMinutes() {
        return minutes;
    }
}
