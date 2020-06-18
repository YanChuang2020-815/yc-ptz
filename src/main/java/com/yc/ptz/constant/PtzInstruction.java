package com.yc.ptz.constant;

import lombok.Data;

/**
 * Created By ChengHao On 2020/6/14
 */
public enum PtzInstruction {
    UP("UP",              "FF010008002029FF010000000001"),
    DOWN("DOWN",          "FF010010002031FF010000000001"),
    LEFT("LEFT",          "FF010004200025FF010000000001"),
    RIGHT("RIGHT",        "FF010002200023FF010000000001"),
    UPLEFT("UPLEFT",      "FF01000C20204DFF010000000001"),
    DOWNLEFT("DOWNLEFT",  "FF010014202055FF010000000001"),
    UPRIGHT("UPRIGHT",    "FF01000A20204BFF010000000001"),
    DOWNRIGHT("DOWNRIGHT","FF010012202053FF010000000001"),
    ;

    private String name;
    private String value;

    PtzInstruction(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
