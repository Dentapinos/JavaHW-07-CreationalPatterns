package org.example.areas.repair;

import org.example.areas.AreaForCar;
import org.example.enums.CarTypes;

public class RepairAreaForTrack extends SimpleRepair implements AreaForCar {
    public RepairAreaForTrack(int capacity) {
        super(capacity);
    }

    @Override
    void setSuitableAreaType() {
        suitableAreaType = CarTypes.TRUCK;
    }
}
