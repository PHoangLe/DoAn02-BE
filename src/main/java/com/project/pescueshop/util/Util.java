package com.project.pescueshop.util;

import java.util.Arrays;
import java.util.List;

public class Util {

    public static List<String> getListStringFromString(String string){
        String[] stringArray = string.split("\\\\^\\\\|");
        return Arrays.asList(stringArray);
    }
}
