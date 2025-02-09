package org.example.checkpoints;

import org.example.areas.AreaForCar;
import org.example.areas.area.Area;

import java.util.ArrayList;

public interface Checkpoint{
    /**
     * Получает название контрольной точки
     * @return {@code String} название контрольной точки
     */
    String getName();

    /**
     * Получает долготу контрольной точки
     * @return число в {@code double}
     */
    double getLongitude();

    /**
     * Получает широту контрольной точки
     * @return число в {@code double}
     */
    double getLatitude();

    /**
     * Проверяет, является ли точка обязательной для прохождения
     * @return {@code true} - если контрольная точка обязательна для прохождения, иначе {@code false}
     */
    boolean isMandatory();

    /**
     * Получает парковочные места для всех типов машин
     * @return {@code ArrayList<ParkingArea>} список парковочных мест
     */
    ArrayList<AreaForCar> getParkingAreas();

    /**
     * Получает ремонтные места для всех типов машин
     * @return {@code ArrayList<ParkingArea>} список ремонтных мест
     */
    ArrayList<AreaForCar> getRepairAreas();

    /**
     * Добавляет парковку определенного типа в общий список
     * @param parkingArea - определенный вид парковки
     */
    void addParkingArea(AreaForCar parkingArea);

    /**
     * Добавляет ремонтное место для определенного типа машин
     * @param repairArea - определенный вид ремонтной площадки
     */
    void addRepairArea(AreaForCar repairArea);

    /**
     * Получает значение штрафа за объезд контрольной точки
     * @return  штраф в виде целого числа {@code int}, если точка не предполагает штрафов то метод вернет {@code 0}
     */
    default int getTicket(){return 0;}
}
