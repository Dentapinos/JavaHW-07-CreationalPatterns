package org.example.areas;

import org.example.areas.area.Area;
import org.example.areas.area.RepairFactory;

public class RepairCreator extends AreaCreator{
    @Override
    Area createArea() {
        return new RepairFactory();
    }
}
