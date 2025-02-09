package org.example.enums;

public enum AreaType{
    PARKING("parking"),
    REPAIR_AREA("repair_area"),
    EMPTY_TYPE_AREA("empty_type");

    private final String type;

    AreaType(String type) {
        this.type = type;
    }

    public String areaType() {
        return type;
    }
}
