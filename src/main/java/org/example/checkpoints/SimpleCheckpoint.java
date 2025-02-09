package org.example.checkpoints;

import org.example.areas.AreaForCar;

import java.util.ArrayList;

public abstract class SimpleCheckpoint implements Checkpoint {
    String name;
    double longitude;
    double latitude;

    ArrayList<AreaForCar> parkingAreas;
    ArrayList<AreaForCar> repairAreas;

    SimpleCheckpoint(String name, double longitude, double latitude, ArrayList<AreaForCar> parkingAreas, ArrayList<AreaForCar> repairAreas) {
        this.name = name;
        checkCoordinates(longitude, latitude);
        this.longitude = longitude;
        this.latitude = latitude;
        this.parkingAreas = parkingAreas;
        this.repairAreas = repairAreas;
    }

    SimpleCheckpoint(String name, double longitude, double latitude) {
        this.name = name;
        checkCoordinates(longitude, latitude);
        this.longitude = longitude;
        this.latitude = latitude;
        parkingAreas = new ArrayList<>();
        repairAreas = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getLongitude() {
        return longitude;
    }

    @Override
    public double getLatitude() {
        return latitude;
    }

    public ArrayList<AreaForCar> getParkingAreas() {
        if ( parkingAreas != null && !parkingAreas.isEmpty()) {
            return parkingAreas;
        }
        return null;
    }

    public ArrayList<AreaForCar> getRepairAreas() {
        if (!repairAreas.isEmpty()) {
            return repairAreas;
        }
        return null;
    }

    public void addParkingArea(AreaForCar parkingArea) {
        parkingAreas.add(parkingArea);
    }

    public void addRepairArea(AreaForCar repairArea) {
        repairAreas.add(repairArea);
    }

    private void checkCoordinates(double verifiedLongitude, double verifiedLatitude) throws IllegalArgumentException {
        if (verifiedLongitude < -180.0 || verifiedLongitude > 180.0) {
            throw new IllegalArgumentException("Долгота должна быть в пределах от -180.0 до +180.0");
        }
        if (verifiedLatitude < -90.0 || verifiedLatitude > 90.0) {
            throw new IllegalArgumentException("Широта должна быть в пределах от -90.0 до +90.0");
        }
    }
}
