package org.example.areas.area;

import org.example.areas.AreaForCar;
import org.example.areas.parking.ParkingAreaForPassengerCar;
import org.example.areas.parking.ParkingAreaForTrack;
import org.example.enums.CarTypes;

public class ParkingFactory implements Area {

    @Override
    public AreaForCar create(CarTypes carType, int capacity) {
        if (carType == CarTypes.TRUCK){
            return new ParkingAreaForTrack(capacity);
        } else if (carType == CarTypes.PASSENGER) {
            return new ParkingAreaForPassengerCar(capacity);
        }
        return null;
    }
}
