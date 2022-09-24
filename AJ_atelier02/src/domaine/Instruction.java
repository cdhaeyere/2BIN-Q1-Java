package domaine;

import util.Util;

import java.time.Duration;

public class Instruction {

    private String   description;
    private Duration dureeEnMinutes;

    public Instruction(String description, int dureeEnMinutes) {
        Util.checkString(description);
        Util.checkPositiveOrNul(dureeEnMinutes);
        this.description = description;
        this.dureeEnMinutes = Duration.ofMinutes(dureeEnMinutes);
    }

    public String getDescription() {
        return description;
    }

    public Duration getDureeEnMinutes() {
        return dureeEnMinutes;
    }

    public void setDureeEnMinutes(int dureeEnMinutes) {
        Util.checkStrictlyPositive(dureeEnMinutes);
        this.dureeEnMinutes = Duration.ofMinutes(dureeEnMinutes);
    }

    @Override
    public String toString() {
        String hms = String.format("%d:%02d", dureeEnMinutes.toHours(), dureeEnMinutes.toMinutes()%60);
        return "(" + hms + ") " + description;
    }
}
