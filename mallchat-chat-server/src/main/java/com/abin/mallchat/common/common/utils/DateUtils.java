package com.abin.mallchat.common.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static Long getEndTimeByToday() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atStartOfDay();
        return ChronoUnit.MILLIS.between(now, endOfDay);
    }
}
