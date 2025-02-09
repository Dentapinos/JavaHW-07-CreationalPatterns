package org.example.areas.repair;

import org.example.areas.AreaForCar;
import org.example.enums.CarTypes;

public class RepairAreaForPassengerCar extends SimpleRepair implements AreaForCar {

    public RepairAreaForPassengerCar(int capacity) {
        super(capacity);
    }

    @Override
    void setSuitableAreaType() {
        suitableAreaType = CarTypes.PASSENGER;
    }
}
