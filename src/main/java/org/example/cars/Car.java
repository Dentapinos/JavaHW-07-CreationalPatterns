package org.example.cars;

import org.example.checkpoints.Checkpoint;
import org.example.enums.CarTypes;
import org.example.track.Track;

import java.util.ArrayList;
import java.util.Map;

public interface Car {
    /**
     * Получает государственный номер типа AA0
     * @return {@code String} государственный номер
     */
    String getNumber();

    /**
     * Получает цвет машины
     * @return {@code String} цвет машины
     */
    String getColor();

    /**
     * Получает мощность машины, измеряется в л.с.
     * @return {@code double} число лошадиных сил
     */
    double getPower();

    /**
     * Получает объем бензобака
     * @return {@code int} объем бензобака
     */
    int getMaxGasTank();

    /**
     * Получает тип машины из образцов {@code CarTypes}
     * @return {@code CarTypes} тип машины
     */
    CarTypes getTypeCar();

    /**
     * Получает данные по местонахождению и состоянию машины
     * @param elapsedTimeInSeconds условное время которое прошло с последнего запроса местонахождения
     * @return {@code Map<String, Double>} карту c параметрами местонахождения и состояния
     */
    Map<String, Double> getLocation(int elapsedTimeInSeconds);

    /**
     * Получает список посещенных контрольных точек машиной
     * @return {@code ArrayList<Checkpoint>} список посещенных контрольных точек
     */
    ArrayList<Checkpoint> getPassedCheckpoint();

    /**
     * Получает список не посещенных контрольных точек машиной
     * @return {@code ArrayList<Checkpoint>} список не посещенных контрольных точек
     */
    ArrayList<Checkpoint> getNoPassedCheckpoint();

    /**
     * Устанавливает начальные координаты машины
     * @param track маршрут, по которому будет производиться движение
     */
    void run(Track track);
}
