package org.example.areas.parking;

import org.example.areas.AreaForCar;
import org.example.enums.CarTypes;

public class ParkingAreaForTrack extends SimpleParking implements AreaForCar {

    public ParkingAreaForTrack(int capacity) {
        super(capacity);
    }

    @Override
    void setSuitableAreaType() {
        suitableAreaType = CarTypes.TRUCK;
    }
}
