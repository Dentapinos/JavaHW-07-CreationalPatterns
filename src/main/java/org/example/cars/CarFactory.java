package org.example.cars;

import org.example.enums.CarTypes;

public interface CarFactory {
    /**
     * Создает объект машины с генерируемыми параметрами
     * @param type тип необходимой машины
     * @return {@code Car} новый сгенерированный объект машины
     */
    Car createRandomCar(CarTypes type);

    /**
     * Создает объект машины с указанными параметрами
     * @param type тип необходимой машины
     * @param number государственный номер
     * @param color цвет машины
     * @param power мощность машины в лошадиных силах
     * @param fuel объем бензобака в л.
     * @return {@code Car} новый сгенерированный объект машины
     */
    Car createCar(CarTypes type, String number, String color, double power, int fuel);
}
