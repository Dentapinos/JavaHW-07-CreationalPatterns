package org.example.cars.utils;

import org.example.enums.Colors;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GeneratorCharacteristicsCar {
    private static final Random random = new Random();

    public static String generateNumber(int quantityCars) {
        char firstLetter = (char) (random.nextInt(26) + 'A');
        char secondLetter = (char) (random.nextInt(26) + 'A');
        return "" + firstLetter + secondLetter + quantityCars;
    }

    public static String generateColor() {
        List<String> colorsTruck = Arrays.stream(Colors.values()).map(Colors::getColor).toList();
        return colorsTruck.get(random.nextInt(colorsTruck.size() - 1));
    }

    public static double generatePower(int minPower, int maxPower) {
        double randomPower = random.nextDouble(minPower, maxPower);
        return Math.round(randomPower * 100.0) / 100.0;
    }

    public static int generateFuel(int minFuel, int maxFuel) {
        return random.nextInt(minFuel, maxFuel);
    }
}
