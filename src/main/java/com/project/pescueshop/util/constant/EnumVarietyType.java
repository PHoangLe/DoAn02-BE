package com.project.pescueshop.util.constant;

import java.util.HashMap;
import java.util.Map;

public enum EnumVarietyType {
    SIZE(1, "SIZE"),
    COLOR(2, "COLOR");

    private static final Map<Integer, EnumVarietyType> BY_ID = new HashMap<>();
    private static final Map<String, EnumVarietyType> BY_LABEL = new HashMap<>();

    static {
        for (EnumVarietyType e : values()) {
            BY_ID.put(e.id, e);
            BY_LABEL.put(e.value, e);
        }
    }

    private final Integer id;
    private final String value;

    private EnumVarietyType(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public static EnumVarietyType getById(Integer id) {
        return BY_ID.get(id);
    }

    public static EnumVarietyType getByValue(String value) {
        return BY_LABEL.get(value);
    }
}
