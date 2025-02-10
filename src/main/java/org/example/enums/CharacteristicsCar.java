package org.example.enums;

public enum CharacteristicsCar {
//    Passenger Car
    PASSENGER_MIN_POWER(100),
    PASSENGER_MAX_POWER(230),
    PASSENGER_MAX_FUEL(40),
    PASSENGER_MIN_FUEL(70),
//    Truck
    TRUCK_MIN_POWER(80),
    TRUCK_MAX_POWER(190),
    TRUCK_MAX_FUEL(500),
    TRUCK_MIN_FUEL(70);

    public final int value;
    CharacteristicsCar(int value) {
        this.value = value;
    }


}
