package com.project.pescueshop.util.constant;

import java.util.HashMap;
import java.util.Map;

public enum EnumStatus {
    ACTIVE(1, "ACTIVE"),
    DELETED(2, "DELETED"),
    LOCKED(3, "LOCKED");

    private static final Map<Integer, EnumStatus> BY_ID = new HashMap<>();
    private static final Map<String, EnumStatus> BY_LABEL = new HashMap<>();

    static {
        for (EnumStatus e : values()) {
            BY_ID.put(e.id, e);
            BY_LABEL.put(e.value, e);
        }
    }

    public final Integer id;
    public final String value;

    private EnumStatus(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public static EnumStatus getById(Integer id) {
        return BY_ID.get(id);
    }

    public static EnumStatus getByValue(String value) {
        return BY_LABEL.get(value);
    }
}
