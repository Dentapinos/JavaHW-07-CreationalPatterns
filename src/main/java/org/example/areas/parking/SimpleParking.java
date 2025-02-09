package org.example.areas.parking;

import org.example.areas.AreaForCar;
import org.example.cars.Car;
import org.example.enums.AreaType;
import org.example.enums.CarTypes;

import java.util.ArrayList;

public abstract class SimpleParking implements AreaForCar {
    private int capacity;
    private final ArrayList<Car> carsInArea;
    private AreaType areaType;
    protected CarTypes suitableAreaType;

    public SimpleParking(int capacity) {
        this.capacity = capacity;
        this.carsInArea = new ArrayList<>();
        areaType = AreaType.PARKING;
        setSuitableAreaType();
    }

    abstract void setSuitableAreaType();

    @Override
    public void addCarToPlace(Car car) {
        if (carsInArea.size() < capacity) {
            carsInArea.add(car);
        }
    }

    @Override
    public void removeCarFromPlace(Car car) {
        carsInArea.remove(car);
    }

    @Override
    public AreaType getAreaType() {
        return areaType;
    }

    @Override
    public boolean isFreePlace(Car car) {
        int currentCapacity = capacity - carsInArea.size();
        if (currentCapacity > 0) {
            addCarToPlace(car);
            return true;
        }
        return false;
    }

    @Override
    public boolean isSuitableTypeOfMachine(CarTypes carType) {
        return suitableAreaType == carType;
    }
}
