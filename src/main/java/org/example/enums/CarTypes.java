package org.example.enums;

public enum CarTypes {
    TRUCK("Truck"),
    PASSENGER("Passenger"),
    EMPTY_TYPE("Empty Type");

    private final String type;

    CarTypes(String type) {
        this.type = type;
    }

    public String nameCar() {
        return type;
    }
}
