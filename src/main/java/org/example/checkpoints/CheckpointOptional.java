package org.example.checkpoints;

import org.example.areas.AreaForCar;
import org.example.areas.area.Area;

import java.util.ArrayList;

public class CheckpointOptional extends SimpleCheckpoint {
    int ticket;

    CheckpointOptional(String name, double longitude, double latitude, int ticket) {
        super(name, longitude, latitude);
        this.ticket = ticket;
    }

    CheckpointOptional(String name, double longitude, double latitude, int ticket, ArrayList<AreaForCar> parkingAreas, ArrayList<AreaForCar> repairAreas) {
        super(name, longitude, latitude, parkingAreas, repairAreas);
        this.ticket = ticket;
    }

    public static CheckpointOptional withAreas(String name, double longitude, double latitude,int ticket, ArrayList<AreaForCar> parkingAreas, ArrayList<AreaForCar> repairAreas){
        return new CheckpointOptional(name, longitude, latitude, ticket, parkingAreas, repairAreas);
    }

    public static CheckpointOptional onlyCoordinatesAndName(String name, double longitude, double latitude, int ticket){
        return new CheckpointOptional(name, longitude, latitude, ticket);
    }

    public int getTicket() {
        return ticket;
    }

    @Override
    public boolean isMandatory() {
        return false;
    }
}
