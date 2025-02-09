package org.example.areas;

import org.example.cars.Car;
import org.example.enums.AreaType;
import org.example.enums.CarTypes;

public interface AreaForCar {
    /**
     * Добавляет машину площадку
     * @param car - объект машины
     */
    void addCarToPlace(Car car);

    /**
     * Удаляет машину с площадки
     * @param car - объект машины
     */
    void removeCarFromPlace(Car car);

    /**
     * Возвращает тип площадки, для каких машин она подходит
     * @return {@code String} тип парковки
     */
    AreaType getAreaType();

    /**
     * Проверяет, подходит ли тип машины для площадки
     * @param carType машина для сравнения
     * @return {@code true} если тип машины подходит
     */
    boolean isSuitableTypeOfMachine(CarTypes carType);

    /**
     * Проверяет, есть ли свободное место для машины на площадке
     * @param car машина, для какой нужно место
     * @return {@code true} если есть места для указанной машины, иначе {@code false}
     */
    boolean isFreePlace(Car car);
}
