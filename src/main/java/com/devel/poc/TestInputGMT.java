package com.devel.poc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatterBuilder;
import java.util.TimeZone;


public class TestInputGMT {
    private static final Logger LOGGER = LogManager.getLogger(TestInputGMT.class);
    private static final String TIME_STAMP_STR ="2022-11-11 05:11:03.000";
    public static void main(String[] args) {
        setUTCTimeZone();
        Timestamp timestampOriginal =getTimestamp();

        printParsedActualTimeZone(timestampOriginal);

        changeTimeZone();

        printParsedWithnewTimeZone(timestampOriginal, ZoneId.systemDefault());

        printParsedWithnewTimeZone(timestampOriginal, ZoneOffset.UTC);
    }

    private static void printParsedWithnewTimeZone(Timestamp timestampOriginal, ZoneId zoneId) {
        LocalDateTime localDateTime;
        localDateTime =
                Instant.ofEpochMilli(timestampOriginal.getTime()).atZone(zoneId).toLocalDateTime();
        LOGGER.info(localDateTime);
    }

    private static void changeTimeZone() {
        TimeZone.setDefault(TimeZone.getTimeZone("Pacific/Guadalcanal"));
        LOGGER.info(ZoneId.systemDefault());
    }

    private static void printParsedActualTimeZone(Timestamp timestampOriginal) {
        LocalDateTime localDateTime =
                Instant.ofEpochMilli(timestampOriginal.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        LOGGER.info(localDateTime);
    }

    private static void setUTCTimeZone() {
        LOGGER.info(ZoneId.systemDefault());
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    private static Timestamp getTimestamp(){
        DateTimeFormatterBuilder builder = new DateTimeFormatterBuilder();
        builder.appendPattern("yyyy-MM-dd HH:mm:ss.SSS");

        LocalDateTime localDateTime = LocalDateTime.from(builder.toFormatter().parse(TIME_STAMP_STR));
        return Timestamp.valueOf(localDateTime);
    }
}
