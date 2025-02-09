package org.example.areas;

import org.example.areas.area.Area;
import org.example.areas.area.ParkingFactory;

public class ParkingCreator extends AreaCreator{
    @Override
    Area createArea() {
        return new ParkingFactory();
    }
}
