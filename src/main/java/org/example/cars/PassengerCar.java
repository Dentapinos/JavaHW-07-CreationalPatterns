package org.example.cars;

import org.example.cars.utils.GeneratorCharacteristicsCar;
import org.example.enums.CarTypes;

import java.util.Map;

public class PassengerCar extends SimpleCar {
    static int numberCar = 0;
    static final int MIN_POWER = 100;
    static final int MAX_POWER = 230;
    static final int MIN_FUEL = 40;
    static final int MAX_FUEL = 70;

    public PassengerCar(String number, String color, double power, int fuelReverse) {
        super(CarTypes.PASSENGER, number, color, power, fuelReverse);
        numberCar++;
    }

    public PassengerCar(CarTypes typeCar) {
        super(typeCar, "", "", 0, 0);
        numberCar++;
        number = GeneratorCharacteristicsCar.generateNumber(numberCar);
        color = GeneratorCharacteristicsCar.generateColor();
        power = GeneratorCharacteristicsCar.generatePower(MIN_POWER, MAX_POWER);
        maxGasTank = GeneratorCharacteristicsCar.generateFuel(MIN_FUEL, MAX_FUEL);
        currentGasTank = maxGasTank;
    }

    @Override
    public Map<String, Double> getLocation(int elapsedTimeInSeconds) {
        return super.getLocation(elapsedTimeInSeconds, MIN_POWER);
    }
}
