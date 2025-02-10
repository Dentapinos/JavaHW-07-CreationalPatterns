package org.example.cars;

import org.example.cars.utils.GeneratorCharacteristicsCar;
import org.example.enums.CarTypes;
import org.example.enums.CharacteristicsCar;

import java.util.Map;

public class PassengerCar extends SimpleCar {
    static int numberCar = 0;
    static final int MIN_POWER = CharacteristicsCar.PASSENGER_MIN_POWER.value;
    static final int MAX_POWER = CharacteristicsCar.PASSENGER_MAX_POWER.value;
    static final int MIN_FUEL = CharacteristicsCar.PASSENGER_MIN_FUEL.value;
    static final int MAX_FUEL = CharacteristicsCar.PASSENGER_MAX_FUEL.value;

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
