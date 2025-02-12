package org.example.enums;

import lombok.Getter;

@Getter
public enum Colors {
    BLACK("черный"),
    RED("красный"),
    GREEN("зеленый"),
    YELLOW("желтый"),
    BLUE("синий"),
    PURPLE("фиолетовый"),
    WHITE("белый");

    private final String color;

    Colors(String color) {
        this.color = color;
    }

}
