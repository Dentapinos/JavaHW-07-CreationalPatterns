package org.example.areas.area;

import org.example.areas.AreaForCar;
import org.example.areas.repair.RepairAreaForPassengerCar;
import org.example.areas.repair.RepairAreaForTrack;
import org.example.enums.CarTypes;

public class RepairFactory implements Area {

    @Override
    public AreaForCar create(CarTypes carType, int capacity) {
        if (carType == CarTypes.TRUCK){
            return new RepairAreaForTrack(capacity);
        } else if (carType == CarTypes.PASSENGER) {
            new RepairAreaForPassengerCar(capacity);
        }
        return null;
    }
}
