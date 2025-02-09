package org.example.checkpoints;

import org.example.areas.AreaForCar;
import org.example.areas.area.Area;

import java.util.ArrayList;

public class CheckpointMandatory extends SimpleCheckpoint {

    CheckpointMandatory(String name, double longitude, double latitude) {
        super(name, longitude, latitude);
    }

    CheckpointMandatory(String name, double longitude, double latitude, ArrayList<AreaForCar> parkingAreas, ArrayList<AreaForCar> repairAreas) {
        super(name, longitude, latitude, parkingAreas, repairAreas);
    }

    public static CheckpointMandatory withAreas(String name, double longitude, double latitude, ArrayList<AreaForCar> parkingAreas, ArrayList<AreaForCar> repairAreas){
        return new CheckpointMandatory(name, longitude, latitude, parkingAreas, repairAreas);
    }

    public static CheckpointMandatory onlyCoordinatesAndName(String name, double longitude, double latitude){
        return new CheckpointMandatory(name, longitude, latitude);
    }

    @Override
    public boolean isMandatory() {
        return true;
    }
}
