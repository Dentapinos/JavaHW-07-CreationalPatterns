package org.example;

import org.example.areas.AreaForCar;
import org.example.areas.AreaCreator;
import org.example.areas.ParkingCreator;
import org.example.areas.RepairCreator;
import org.example.cars.Car;
import org.example.cars.CarFactory;
import org.example.cars.CarFactoryImpl;
import org.example.checkpoints.Checkpoint;
import org.example.checkpoints.CheckpointMandatory;
import org.example.checkpoints.CheckpointOptional;
import org.example.enums.CarTypes;
import org.example.enums.Colors;
import org.example.track.BaseTrack;
import org.example.track.DefaultTrackRostovVolgograd;
import org.example.track.Track;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Main {
    public static int timeBetweenLocationRequests = 4;
    public static int penaltyWaitingTime = 0;
    public static boolean isWinner = false;

    /**
     * Гонки. С ТЕКУЩИМИ НАСТРОЙКАМИ ИГРА БУДЕТ ИДТИ ПРИМЕРНО 30с.
     * <p>
     * {@code Трек}
     * Трек  можно создать самому используя {@code BaseTrack} либо взять уже существующий {@code DefaultTrackRostovVolgograd}
     * он содержит в себе контрольные точки {@code Checkpoint}
     * </p>
     * <p>
     * {@code Контрольная точка}
     * Контрольная точка {@code Checkpoint} это точка которую машина обязана посетить, либо, если точка
     * является не обязательно, то ее можно пропустить но на финише будет штраф и придется ожидать это время до оглашения результатов
     * давая возможность другим машинам {@code Car} ее обогнать
     * </p>
     * <p>
     * {@code Машина}
     * Машина {@code Car} это объект который соревнуется в гонке на треке, условно все машины ездят по прямой траектории
     * </p>
     * <p>
     * {@code СУТЬ ИГРЫ}
     * На старт становятся машины, им дается карта, трек, и после условного сигнала старт они начинают движение
     * Во время движения у них постоянно запрашиваются их координаты и их действия в данный момент
     * На консоль выводятся следующие данные: координаты- долгота, широта; где машина находиться, на стоянке, ремонте или заправляется
     * При прибытии на финиш считаются все штрафы за не посещенные контрольные точки, и машина ожидает это время на финише.
     * Если за время ожидания никто больше не финишировал то это чистая победа.
     * Если во время ожидания штрафных часов на финиш прибыла другая машина, то сравниваются штрафные часы и тот у кого их меньше,
     * тот и победил. Причем у первого штрафные часы берутся в зачет только те, которые осталось ожидать.
     * </p>
     */
    public static void main(String[] args) {
        Track track = new DefaultTrackRostovVolgograd();
//        Track track = getBaseTrack();

        ArrayList<Car> cars = createCar();
        printStart(cars, track);
        allowTrafficToPass(cars, track);

        ArrayList<Car> carsWin = new ArrayList<>();

        while (!isWinner) {
            printCurrentLocationHeader();
            waiting(timeBetweenLocationRequests * 100);  //скорость печати в консоль, для эффекта поступающих данных
            setActualPenaltyAndRequestTime();

            for (Car car : cars) {
                if (!carsWin.contains(car)) {
                    Map<String, Double> carLocation = car.getLocation(timeBetweenLocationRequests);
                    printCurrentLocation(car, carLocation);
                    if (carLocation.get("crossing") != null && carLocation.get("crossing") > 0 && track.isLastCheckpoint(car.getPassedCheckpoint().getLast())) {
                        carsWin.add(car);
                        int penaltyCurrentCar = getFuel(track, car);
                        if (penaltyWaitingTime <= 0 && penaltyCurrentCar <= 0) {
                            printWinner(car);
                            if (isWinner) break;
                        }
                        if (carsWin.size() < 2) penaltyWaitingTime = penaltyCurrentCar;
                        printFinishedMessage(car, penaltyWaitingTime);
                    }
                }
            }
            if (penaltyWaitingTime == 0 && !carsWin.isEmpty()) {
                printWinner(carsWin.getFirst());
            }
        }
    }

    private static ArrayList<Car> createCar() {
        CarFactory carFactory = new CarFactoryImpl();
        ArrayList<Car> cars = new ArrayList<>();

        Car car1 = carFactory.createRandomCar(CarTypes.TRUCK);
        Car car2 = carFactory.createRandomCar(CarTypes.PASSENGER);
        Car car3 = carFactory.createCar(CarTypes.PASSENGER, "LK24L", Colors.WHITE.getColor(), 170.0, 60);
//        Car car4 = ((SimpleCar)car3).clone();
//
//        System.out.println(car4);

        cars.add(car1);
        cars.add(car2);
        cars.add(car3);
        return cars;
    }

    private static Track getBaseTrack() {
        Checkpoint checkpoint1 = CheckpointMandatory.onlyCoordinatesAndName("Точка1", -90, -90);
        Checkpoint checkpoint2 = CheckpointOptional.onlyCoordinatesAndName("Точка2", -10.0, 0.0, 10);
        Checkpoint checkpoint3 = CheckpointMandatory.onlyCoordinatesAndName("Точка1", 10, 15);

        AreaCreator areaCreator = new RepairCreator();
        AreaForCar truckRepairArea = areaCreator.createAreaForCar(CarTypes.TRUCK, 1);
        AreaForCar passRepairArea = areaCreator.createAreaForCar(CarTypes.PASSENGER, 2);
        areaCreator = new ParkingCreator();
        AreaForCar passParkingArea = areaCreator.createAreaForCar(CarTypes.PASSENGER, 3);
        AreaForCar truckParkingArea = areaCreator.createAreaForCar(CarTypes.TRUCK, 1);

        checkpoint1.addParkingArea(truckParkingArea);
        checkpoint2.addParkingArea(passParkingArea);
        checkpoint2.addParkingArea(passRepairArea);
        checkpoint3.addParkingArea(passParkingArea);
        checkpoint2.addRepairArea(truckRepairArea);

        Track track = new BaseTrack();
        track.addCheckpoint(checkpoint1);
        track.addCheckpoint(checkpoint2);
        track.addCheckpoint(checkpoint3);
        return track;
    }

    static int getFuel(Track track, Car car) {
        int receivedFine = 0;
        List<Checkpoint> noPassedCheckpoint = track.getCheckpoints().stream().filter(c -> !car.getPassedCheckpoint().contains(c)).toList();
        if (!noPassedCheckpoint.isEmpty()) {
            receivedFine = noPassedCheckpoint.stream().map(Checkpoint::getTicket).mapToInt(Integer::intValue).sum();
        }
        return receivedFine;
    }

    static void setActualPenaltyAndRequestTime() {
        if (penaltyWaitingTime > 0) {
            penaltyWaitingTime -= timeBetweenLocationRequests;
            if (penaltyWaitingTime < 0) {
                timeBetweenLocationRequests = timeBetweenLocationRequests + penaltyWaitingTime;
                penaltyWaitingTime = 0;
            }
        }
    }

    static void allowTrafficToPass(ArrayList<Car> cars, Track track) {
        cars.forEach(car -> car.run(track));
    }

    static void printCurrentLocationHeader() {
        System.out.println("\u001b[33m~~~~~~~~~~~~~~~~~~~!~~~~~~~~~~~~~~~~~~~~~\u001b[0m");
        System.out.println("\u001b[33m~~~~~~~~~~~~ТЕКУЩАЯ ИНФОРМАЦИЯ~~~~~~~~~~~\u001b[0m");
        System.out.println("\u001b[33m~~~~~~~~~~~~~~~~~~~!~~~~~~~~~~~~~~~~~~~~~\u001b[0m");
    }

    static void waiting(int waitMillis) {
        try {
            Thread.sleep(waitMillis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static void printWinner(Car car) {
        System.out.println("\n\u001b[96m<===================ГОНКА ОКОНЧЕНА===================>");
        System.out.println("<=====================ПОБЕДИТЕЛЬ=====================>");
        System.out.println(car);
        printCup();
        System.out.println("<===================ГОНКА ОКОНЧЕНА===================>\u001b[0m");
        isWinner = true;
    }

    static void printCurrentLocation(Car car, Map<String, Double> carLocation) {
        if (carLocation.get("crossing") != null && carLocation.get("crossing") > 0 && carLocation.get("pass_checkpoint") == null) {
            System.out.println("\u001b[32m===> " + car.getNumber() + " - достигла точки " + car.getPassedCheckpoint().getLast().getName() + "\u001b[0m");
        } else if (carLocation.get("pass_checkpoint") != null) {
            System.out.println("\u001b[36m===> " + car.getNumber() + " - пропустил точку " + car.getNoPassedCheckpoint().getLast().getName() + "\u001b[0m");
        }
        System.out.println("---> " + car.getNumber() + " - Д:" + carLocation.get("longitude") + " / Ш:" + carLocation.get("latitude"));
        if (carLocation.get("refresh") != null && carLocation.get("refresh") > 0) {
            System.out.println("\u001b[34m---> " + car.getNumber() + " Заправляется\u001b[0m");
        }
        if (carLocation.get("repairs") != null && carLocation.get("repairs") == 1.0) {
            System.out.println("---> " + car.getNumber() + " Заехал на ремонт, но мест нет");
        } else if (carLocation.get("repairs") != null && carLocation.get("repairs") > 1.0) {
            System.out.println("\u001b[34m---> " + car.getNumber() + " Заехал на ремонт, ремонтируется\u001b[0m");
        }
        if (carLocation.get("parking") != null && carLocation.get("parking") == 1.0) {
            System.out.println("---> " + car.getNumber() + " Заехал на парковку, но мест нет");
        } else if (carLocation.get("parking") != null && carLocation.get("parking") > 1.0) {
            System.out.println("\u001b[34m---> " + car.getNumber() + " Заехал на парковку, отдыхает\u001b[0m");
        }
    }

    static void printFinishedMessage(Car car, int penaltyWaitingTime) {
        System.out.println("\u001b[36m " + car + " \u001b[0m");
        System.out.println("\u001b[36m+++Прибыл на финиш+++\u001b[0m");
        if (penaltyWaitingTime == 0) {
            System.out.println("\u001b[33m>>>штрафов - " + penaltyWaitingTime + "\u001b[0m");
            printWinner(car);
            System.exit(0);
        } else {
            System.out.println("\u001b[31m>>>штрафов - " + penaltyWaitingTime + "\u001b[0m");
            System.out.println("\u001b[33mЖдем других еще " + penaltyWaitingTime + " минут\u001b[0m");
        }
    }

    static void printStart(ArrayList<Car> cars, Track track) {
        System.out.println("\u001b[32m<<===" + track.getCheckpoints().getFirst().getName() + "===>>\u001b[0m");
        for (Car car : cars) {
            System.out.println("\u001b[33m:===> \u001b[0m" + car);
        }
        System.out.println("\u001b[33m<<===S-T-A-R-T===>>\u001b[0m");
    }

    static void printCup(){
        System.out.println("" +
                "░░░░░░░░░░░░░░░░░░░░░▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░▄▄▄▄████████████▀▀█▄▄▄▄░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░█▄░░████████████░░█░░░█░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░█░░████████████░▄█░░█░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░▀▄░██████████░░█░▄▀░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░▀▀████████▀░█▀▀░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░▄░░░░░░░░▀█████▀▄▀░░░░░▄█▄░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░▀█▀░░░░░░░░░▀███▀░░░░░░░░▀░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░▄░░░░░░█░░░░░░░░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░▀█▀░░░░░█░░░░░░░░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░░░░░░░▄█▄░░░░░░░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░░░░▄▄▄███▄▄▄░░░░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░███████████████░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░████▀▀▀▀▀▀▀████░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░███░░░░░░░░░███░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░████▄▄▄▄▄▄▄████░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░░░███████████████░░░░░░░░░░░░░░░░░░\n" +
                "░░░░░░░░░░░░░░░░░░░▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄▄░░░░░░░░░░░░░░░░");
    }
}