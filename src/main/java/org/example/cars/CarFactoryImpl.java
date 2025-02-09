package org.example.cars;

import org.example.enums.CarTypes;

public class CarFactoryImpl implements CarFactory {
    @Override
    public Car createRandomCar(CarTypes carType) {
        if (carType.equals(CarTypes.TRUCK)) {
            return new Truck(carType);
        } else if (carType.equals(CarTypes.PASSENGER)) {
            return new PassengerCar(carType);
        } else {
            throw new IllegalArgumentException("Этот вид машин не может участвовать в гонках");
        }
    }

    @Override
    public Car createCar(CarTypes type, String number, String color, double power, int fuel) {
        if (type.equals(CarTypes.TRUCK)) {
            return new Truck(number, color, power, fuel);
        } else if (type.equals(CarTypes.PASSENGER)) {
            return new PassengerCar(number, color, power, fuel);
        } else {
            throw new IllegalArgumentException("Этот вид машин не может участвовать в гонках");
        }
    }
}
