package com.project.pescueshop.util.constant;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum EnumResponseCode {
    //<editor-fold desc="0-System">
    SYSTEM_ERROR("0_1_f", "System Error"),
    SUCCESS("0_2_s", "Succeed"),
    FAILED("0_3_f", "Failed"),
    //</editor-fold>

    //<editor-fold desc="1-Authentication">
    CREATED_ACCOUNT_SUCCESSFUL("1_1_s", "Account has been created"),
    ACCOUNT_EXISTED("1_2_f", "Email existed"),
    ACCOUNT_LOCKED("1_3_f", "Account has been locked"),
    ACCOUNT_INACTIVE("1_4_f", "You must active your email before log in"),
    ACCOUNT_NOT_FOUND("1_5_f", "Account not found"),
    BAD_CREDENTIAL("1_6_f", "Credential error"),
    AUTHENTICATE_SUCCESSFUL("1_7_s", "Log in successfully"),
    NOT_LOGGED_IN("1_8_f", "Please log in"),
    //</editor-fold>

    //<editor-fold desc="2-PRODUCT">
    CREATED_CATEGORY_SUCCESSFUL("2_1_s", "New category has been added"),
    CREATED_SUBCATEGORY_SUCCESSFUL("2_2_f", "New subcategory has been added"),
    PRODUCT_NOT_FOUND("2_3_f", "Product not found"),
    ATTRIBUTE_EXISTED("2_4_f", "Attribute existed"),
    VARIETY_NOT_FOUND("2_5_f", "Variety not found"),
    CART_NOT_FOUND("2_6_f", "Cart not found"),
    B("1_7_s", "Log in successfully");
    //</editor-fold>

    private static final Map<String, EnumResponseCode> BY_STATUS_CODE = new HashMap<>();
    private static final Map<String, EnumResponseCode> BY_MESSAGE = new HashMap<>();

    static {
        for (EnumResponseCode e : values()) {
            BY_STATUS_CODE.put(e.statusCode, e);
            BY_MESSAGE.put(e.message, e);
        }
    }

    private final String statusCode;
    private final String message;

    private EnumResponseCode(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public static EnumResponseCode getByStatusCode(String id) {
        return BY_STATUS_CODE.get(id);
    }

    public static EnumResponseCode getByMessage(String value) {
        return BY_MESSAGE.get(value);
    }
}
