package org.example.track;

import org.example.areas.*;
import org.example.areas.area.Area;
import org.example.checkpoints.Checkpoint;
import org.example.checkpoints.CheckpointMandatory;
import org.example.checkpoints.CheckpointOptional;
import org.example.enums.CarTypes;

import java.util.ArrayList;
import java.util.Random;

public class DefaultTrackRostovVolgograd implements Track {
    ArrayList<Checkpoint> track;

    public DefaultTrackRostovVolgograd() {
        generateTrack();
    }

    @Override
    public ArrayList<Checkpoint> getCheckpoints() {
        return track;
    }

    @Override
    public boolean isLastCheckpoint(Checkpoint checkpoint) {
        return track.getLast().getName().equals(checkpoint.getName());
    }

    @Override
    public void addCheckpoint(Checkpoint checkpoint) {
        track.add(checkpoint);
    }

    private void generateTrack() {
        track = new ArrayList<>();
        track.add(CheckpointMandatory.withAreas("Ростов", 47.222109, 39.718813, generateAreasParking(2), generateAreasRepair(2)));
        track.add(CheckpointOptional.onlyCoordinatesAndName("Степной", 47.344601, 39.880980, 3));
        track.add(CheckpointOptional.onlyCoordinatesAndName("Красный Колос", 47.407944, 39.952898, 2));
        track.add(CheckpointMandatory.withAreas("Аютинский", 47.778379, 40.144439, generateAreasParking(1), generateAreasRepair(2)));
        track.add(CheckpointOptional.onlyCoordinatesAndName("Молодежный", 48.048219, 40.267241, 3));
        track.add(CheckpointMandatory.onlyCoordinatesAndName("945км", 48.190604, 40.276426));
        track.add(CheckpointOptional.onlyCoordinatesAndName("Василевский", 48.182524, 40.557129, 3));
        track.add(CheckpointOptional.withAreas("Шолоховский", 48.282732, 41.046498, 3, generateAreasParking(3), generateAreasRepair(5)));
        track.add(CheckpointMandatory.withAreas("Морозовск", 48.351161, 41.830879, generateAreasParking(2), generateAreasRepair(2)));
        track.add(CheckpointOptional.onlyCoordinatesAndName("Суровикино", 48.605390, 42.844237, 2));
        track.add(CheckpointOptional.withAreas("Яблоневый", 48.675256, 43.209449, 1, generateAreasParking(6), generateAreasRepair(1)));
        track.add(CheckpointOptional.withAreas("КалачНаДону", 48.682001, 43.538200, 5, generateAreasParking(2), generateAreasRepair(2)));
        track.add(CheckpointMandatory.onlyCoordinatesAndName("Волгоград", 48.711431, 44.518799));
    }

    private ArrayList<AreaForCar> generateAreasParking(int maxCapacity){
        AreaCreator areaCreator = new ParkingCreator();
        return getAreas(maxCapacity, areaCreator);
    }

    private ArrayList<AreaForCar> generateAreasRepair(int maxCapacity){
        AreaCreator areaCreator = new RepairCreator();
        return getAreas(maxCapacity, areaCreator);
    }

    private ArrayList<AreaForCar> getAreas(int maxCapacity,  AreaCreator areaCreator) {
        ArrayList<AreaForCar> areasStart = new ArrayList<>();
        Random random = new Random();
        areasStart.add(areaCreator.createAreaForCar(CarTypes.PASSENGER, random.nextInt(maxCapacity)));
        areasStart.add(areaCreator.createAreaForCar(CarTypes.TRUCK, random.nextInt(maxCapacity)));

        ArrayList<AreaForCar> areasEnd = new ArrayList<>();
        for (AreaForCar area : areasStart) {
            if (random.nextBoolean()){
                areasEnd.add(area);
            }
        }
        return areasEnd;
    }
}
