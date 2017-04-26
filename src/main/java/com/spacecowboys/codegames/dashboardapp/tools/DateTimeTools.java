package com.spacecowboys.codegames.dashboardapp.tools;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Created by EDraser on 26.04.17.
 */
public interface DateTimeTools {

    static LocalDateTime toLocalDateTime(Date wert) {
        if (wert == null) {
            return null;
        }

        return LocalDateTime.ofInstant(wert.toInstant(), ZoneId.systemDefault());
    }
}
