package org.example.cars.utils;


import java.util.HashMap;
import java.util.Map;

public class GEO {

    //метод возвращает текущие координаты в зависимости от начальной и конечной точки, с учетом, что машина двигается по прямой
    public static Map<String, Double> getCoordinates(double initialLongitude, double initialLatitude, double finalLongitude, double finalLatitude, double distanceInMeters) {

        double differenceLongitudes = finalLongitude - initialLongitude;
        double differenceLatitudes = finalLatitude - initialLatitude;

        double distanceBetweenPoints = Math.sqrt(Math.pow(differenceLongitudes, 2) + Math.pow(differenceLatitudes, 2));

        double longitude = initialLongitude + differenceLongitudes / distanceBetweenPoints * distanceInMeters;
        double latitude = initialLatitude + differenceLatitudes / distanceBetweenPoints * distanceInMeters;
        double a = (int) (Math.round(longitude * 1000000.0)) / 1000000.0;
        double b = (int) (Math.round(latitude * 1000000.0)) / 1000000.0;

        double angleA = a;
        angleA = angleA % 360.0;
        if (angleA > 180.0) {
            angleA -= 360.0;
        }
        if (angleA <= -180.0) {
            angleA += 360.0;
        }

        double angleB = b;
        angleB = angleB % 360.0;
        if (angleB > 180.0) {
            angleB -= 360.0;
        }
        if (angleB <= -180.0) {
            angleB += 360.0;
        }

        Map<String, Double> coordinates = new HashMap<>();
        coordinates.put("longitude", angleA);
        coordinates.put("latitude", angleB);
        coordinates.put("crossing", -1.0);

        if ((latitude > finalLatitude || latitude < initialLatitude) && (longitude > finalLongitude || longitude < initialLongitude)) {
            coordinates.put("crossing", 1.0);
        }

        return coordinates;
//        System.out.println(getCoordinates(1, 2, 6, 3.5, 5.2));   пример вызова
    }

    public static void main(String[] args) {
        double longitude = 47.0;
        double latitude = 38.0;

        double tlongitude = 50.0;
        double tlatitude = 40.0;


        for (int i = 0; i < 20; i++) {
            Map<String, Double> s = getCoordinates(longitude, latitude, tlongitude, tlatitude, 0.100);
            latitude = s.get("latitude");
            longitude = s.get("longitude");
        }


    }
}
