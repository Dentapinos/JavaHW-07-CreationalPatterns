package org.example.areas.parking;

import org.example.areas.AreaForCar;
import org.example.enums.CarTypes;

public class ParkingAreaForPassengerCar extends SimpleParking implements AreaForCar {
    public ParkingAreaForPassengerCar(int capacity) {
        super(capacity);
    }

    @Override
    void setSuitableAreaType() {
        suitableAreaType = CarTypes.PASSENGER;
    }
}
