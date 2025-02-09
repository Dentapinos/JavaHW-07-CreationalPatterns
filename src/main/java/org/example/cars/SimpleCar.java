package org.example.cars;

import org.example.areas.AreaForCar;
import org.example.cars.utils.GEO;
import org.example.cars.utils.GeneratorCharacteristicsCar;
import org.example.checkpoints.Checkpoint;
import org.example.enums.CarTypes;
import org.example.track.Track;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class SimpleCar implements Car, Cloneable {
    String number;
    String color;
    double power;
    int maxGasTank;
    int currentGasTank;
    CarTypes typeCar;

    double lastLatitude;
    double lastLongitude;
    Track track;
    ArrayList<Checkpoint> passedCheckpoint;
    ArrayList<Checkpoint> noPassedCheckpoint;

    int currentTimeStopped = 0;
    boolean isLocatedInTheParkingLot = false;
    boolean isLocatedInTheRepairArea = false;

    public SimpleCar(CarTypes typeCar, String number, String color, double power, int fuelReverse) {
        this.number = number;
        this.color = color;
        this.power = power;
        this.maxGasTank = fuelReverse;
        this.currentGasTank = fuelReverse;
        this.typeCar = typeCar;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public String getColor() {
        return color;
    }

    @Override
    public double getPower() {
        return power;
    }

    @Override
    public int getGasTank() {
        return maxGasTank;
    }

    @Override
    public CarTypes getTypeCar() {
        return typeCar;
    }

    @Override
    public void run(Track track) {
        this.track = track;
        lastLatitude = track.getCheckpoints().getFirst().getLatitude();
        lastLongitude = track.getCheckpoints().getFirst().getLongitude();
        passedCheckpoint = new ArrayList<>();
        noPassedCheckpoint = new ArrayList<>();
        passedCheckpoint.add(track.getCheckpoints().getFirst());
    }

    @Override
    public String toString() {
        return typeCar.nameCar() + "<" + getNumber() + "> - " + getColor() + ":power-" + getPower() + "/fuel supply-" + getGasTank();
    }

    @Override
    public ArrayList<Checkpoint> getPassedCheckpoint() {
        return passedCheckpoint;
    }

    @Override
    public ArrayList<Checkpoint> getNoPassedCheckpoint() {
        return noPassedCheckpoint;
    }

    protected Map<String, Double> getLocation(int elapsedTimeInSeconds, int minPower) {
        Map<String, Double> listInfo;
        Random random = new Random();

        int amendmentTime = amendmentOfTheTime(elapsedTimeInSeconds);
        if (amendmentTime == 0) {
            listInfo = new HashMap<>();
            listInfo.put("latitude", lastLatitude);
            listInfo.put("longitude", lastLongitude);
            return listInfo;
        } else {
            if (isLocatedInTheParkingLot && passedCheckpoint.getLast().getParkingAreas() != null) {
                for (AreaForCar area : passedCheckpoint.getLast().getParkingAreas()) {
                    if (area.isSuitableTypeOfMachine(getTypeCar())) {
                        area.removeCarFromPlace(this);
                        isLocatedInTheParkingLot = false;
                    }
                }
            }
            if (isLocatedInTheRepairArea && passedCheckpoint.getLast().getParkingAreas() != null) {
                for (AreaForCar area : passedCheckpoint.getLast().getRepairAreas()) {
                    if (area.isSuitableTypeOfMachine(getTypeCar())) {
                        area.removeCarFromPlace(this);
                        isLocatedInTheRepairArea = false;
                    }
                }
            }
        }

        recalculateFuelInTheTank();
        if (currentGasTank < 0) {
            refreshTank();
            listInfo = new HashMap<>();
            listInfo.put("refresh", 1.0);
            listInfo.put("longitude", lastLongitude);
            listInfo.put("latitude", lastLatitude);
            return listInfo;
        }

        double distance = getDistanceTraveled(elapsedTimeInSeconds, minPower);
        Checkpoint targetCheckpoint = getTargetCheckpoint();
        listInfo = GEO.getCoordinates(lastLongitude, lastLatitude, targetCheckpoint.getLongitude(), targetCheckpoint.getLatitude(), distance);

        if (listInfo.get("crossing") > 0) {
            if (!targetCheckpoint.isMandatory()) {
                //если не обязательно для посещения
                boolean isVisitCheckpoint = random.nextBoolean();
                if (isVisitCheckpoint) {
                    //если решил посетить
                    visitParkingLotOrRepairArea(listInfo, targetCheckpoint);
                    passedCheckpoint.add(targetCheckpoint);
                } else {
                    // если решил не посещать
                    noPassedCheckpoint.add(targetCheckpoint);
                    listInfo.put("pass_checkpoint", 1.0);
                    lastLatitude = listInfo.get("latitude");
                    lastLongitude = listInfo.get("longitude");
                }
            } else {
                //если обязательно для посещения
                visitParkingLotOrRepairArea(listInfo, targetCheckpoint);
                passedCheckpoint.add(targetCheckpoint);
            }
        } else {
            lastLongitude = listInfo.get("longitude");
            lastLatitude = listInfo.get("latitude");
        }
        return listInfo;
    }

    /**
     * Принимает решение посещения парковки и ремонтной площадки
     */
    private void visitParkingLotOrRepairArea(Map<String, Double> listInfo, Checkpoint targetCheckpoint) {
        Random random = new Random();
        boolean isVisitParkingLot = random.nextBoolean();
        boolean isVisitRepairArea = random.nextBoolean();

        if (isVisitParkingLot || isVisitRepairArea) {
            if (isVisitParkingLot) {
                visitParkingLot(targetCheckpoint, listInfo);
            }
            if (isVisitRepairArea) {
                stopForRepairs(targetCheckpoint, listInfo);
            }
            // если есть парковка или ремонт
            lastLatitude = passedCheckpoint.getLast().getLatitude();
            lastLongitude = passedCheckpoint.getLast().getLongitude();
            listInfo.put("longitude", lastLongitude);
            listInfo.put("latitude", lastLatitude);
        } else {
            // если парковок нет
            lastLatitude = listInfo.get("latitude");
            lastLongitude = listInfo.get("longitude");
        }
    }

    /**
     * Возвращает следующую от текущей точки на карте
     */
    private Checkpoint getTargetCheckpoint() {
        Checkpoint targetCheckpoint = passedCheckpoint.getLast();
        for (Checkpoint checkpoint : track.getCheckpoints()) {
            if (!passedCheckpoint.contains(checkpoint) && !noPassedCheckpoint.contains(checkpoint)) {
                targetCheckpoint = checkpoint;
                break;
            }
        }
        return targetCheckpoint;
    }

    /**
     * Возвращает пройденное расстояние за указанный период времени
     */
    private double getDistanceTraveled(int elapsedTimeInSeconds, int minPower) {
        final int SPEED_ANIMATION = 1000;
        Random random = new Random();
        double randomSpeed = random.nextDouble(minPower, power);
        double speed = Math.round(randomSpeed * 100.0) / 100.0;
        return Math.round(speed / 3600 * 1000 * elapsedTimeInSeconds * 100) / 100.0 / SPEED_ANIMATION;
    }

    /**
     * Уменьшает топливо в баке
     */
    private void recalculateFuelInTheTank() {
        currentGasTank -= (int) power / maxGasTank + 5;
    }

    /**
     * Заправляет топливный бак
     */
    private void refreshTank() {
        currentTimeStopped += 2;
        currentGasTank = maxGasTank;
    }

    /**
     * Симулирует процесс заезда на парковку
     */
    private void visitParkingLot(Checkpoint checkpoint, Map<String, Double> listInfo) {
        if (checkpoint.getParkingAreas() != null) {
            for (AreaForCar area : checkpoint.getParkingAreas()) {
                if (area != null && area.isSuitableTypeOfMachine(getTypeCar()) && area.isFreePlace(this)) {
                    currentTimeStopped += 2;
                    listInfo.put("parking", 2.0);
                    area.addCarToPlace(this);
                    isLocatedInTheParkingLot = true;
                    return;
                }
            }
        }
        currentTimeStopped += 1;
        listInfo.put("parking", 1.0);
    }

    /**
     * Симулирует процесс заезда на ремонтную площадку
     */
    private void stopForRepairs(Checkpoint checkpoint, Map<String, Double> listInfo) {
        if (checkpoint.getRepairAreas() != null) {
            for (AreaForCar area : checkpoint.getRepairAreas()) {
                if (area != null && area.isSuitableTypeOfMachine(getTypeCar()) && area.isFreePlace(this)) {
                    currentTimeStopped += 4;
                    listInfo.put("repairs", 4.0);
                    area.addCarToPlace(this);
                    isLocatedInTheRepairArea = true;
                    return;
                }
            }
        }
        currentTimeStopped += 1;
        listInfo.put("repairs", 1.0);
    }

    /**
     * Симуляция простаивания на какой-либо площадке
     */
    private int amendmentOfTheTime(int elapsedTimeInSeconds) {
        if (elapsedTimeInSeconds <= currentTimeStopped) {
            currentTimeStopped -= elapsedTimeInSeconds;
            return 0;
        } else {
            elapsedTimeInSeconds -= currentTimeStopped;
            currentTimeStopped = 0;
            return elapsedTimeInSeconds;
        }
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public SimpleCar clone() {
        try {
            SimpleCar clone = (SimpleCar) super.clone();
            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(clone.getNumber());
            int number = 0;
            if (matcher.find()) {
                number = Integer.parseInt(matcher.group(1));
            }
            clone.setNumber(GeneratorCharacteristicsCar.generateNumber(number + 1));
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
