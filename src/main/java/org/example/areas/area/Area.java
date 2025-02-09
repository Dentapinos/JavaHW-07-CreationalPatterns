package org.example.areas.area;

import org.example.areas.AreaForCar;
import org.example.enums.CarTypes;

public interface Area {
    /**
     * Создает площадку для указанного типа машин с указанным количеством мест
     * @param carType - тип машины, под который создать площадку
     * @param capacity - вместительность площадки
     */
    AreaForCar create(CarTypes carType, int capacity);
}
