package com.project.pescueshop.util;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Util {

    public static List<String> getListStringFromString(String string){
        String[] stringArray = string.split("\\^\\|");
        return Arrays.asList(stringArray);
    }

    public static Date getCurrentDate(){
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        return Date.from(zonedDateTime.toInstant());
    }

    public static Date getCurrentDatePlusSeconds(long seconds){
        ZonedDateTime zonedDateTime = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        zonedDateTime.plusSeconds(seconds);
        return Date.from(zonedDateTime.toInstant());
    }
}
