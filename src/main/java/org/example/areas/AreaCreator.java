package org.example.areas;

import org.example.areas.area.Area;
import org.example.enums.CarTypes;

public abstract class AreaCreator {
    Area area;

    public AreaCreator() {
        this.area = createArea();
    }

    abstract Area createArea();

    public AreaForCar createAreaForCar(CarTypes carType, int capacity) {
        return area.create(carType, capacity);
    }
}
