package com.spacecowboys.codegames.dashboardapp.tools;

import com.google.common.base.Strings;
import org.jboss.logging.Logger;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Created by EDraser on 26.04.17.
 */
public class LocalDateTimeXmlAdapter extends XmlAdapter<String, LocalDateTime> {

    private final static Logger logger = Logger.getLogger(LocalDateTimeXmlAdapter.class);

    public static LocalDateTime convert(String v) {
        if (Strings.isNullOrEmpty(v)) {
            return null;
        } else {
            try {
                return LocalDateTime.parse(v, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException e) {
                logger.debug("Convert String to LocalDateTime ->" + v + " could not be parsed with Pattern: " + DateTimeFormatter.ISO_LOCAL_DATE_TIME.toString());
            }
        }

        return null;
    }

    public static String convert(LocalDateTime v) {
        return v.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }


    @Override
    public LocalDateTime unmarshal(String s) throws Exception {
        return convert(s);
    }

    @Override
    public String marshal(LocalDateTime localDateTime) throws Exception {
        return convert(localDateTime);
    }
}
