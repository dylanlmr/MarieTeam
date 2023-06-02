package fr.xkgd.marieteam.data.local.database;

import android.os.Build;

import androidx.room.ProvidedTypeConverter;
import androidx.room.TypeConverter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;

@ProvidedTypeConverter
public class Converters {

    /**
     * Convert a value to a {@link LocalDate}
     * @param value the value to convert
     * @return the {@link LocalDate} or null if value is null
     */
    @TypeConverter
    public LocalDate fromTimestamp(Long value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return value == null ? null : LocalDate.ofEpochDay(value);
        }
        return null;
    }

    /**
     * Convert a {@link LocalDate} to a value
     * @param localDate the {@link LocalDate} to convert
     * @return the value or null if localDate is null
     */
    @TypeConverter
    public Long localDateToTimestamp(LocalDate localDate) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return localDate == null ? null : localDate.toEpochDay();
        }
        return null;
    }

    /**
     * Convert a value to a {@link LocalTime}
     * @param value the value to convert
     * @return the {@link LocalTime} or null if value is null
     */
    @TypeConverter
    public LocalTime fromTimestamp2(Long value) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return value == null ? null : LocalTime.ofNanoOfDay(value);
        }
        return null;
    }

    /**
     * Convert a {@link LocalTime} to a value
     * @param localTime the {@link LocalTime} to convert
     * @return the value or null if localTime is null
     */
    @TypeConverter
    public Long localTimeToTimestamp(LocalTime localTime) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return localTime == null ? null : localTime.toNanoOfDay();
        }
        return null;
    }

    /**
     * Convert a value to a {@link LocalDateTime}
     * @param value the value to convert
     * @return the {@link LocalDateTime} or null if value is null
     */
    @TypeConverter
    public static LocalDateTime fromTimestamp3(Long value) {
        if (value != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault());
        }
        return null;
    }

    /**
     * Convert a {@link LocalDateTime} to a value
     * @param localDateTime the {@link LocalDateTime} to convert
     * @return the value or null if localDateTime is null
     */
    @TypeConverter
    public static Long localDateTimeToTimestamp(LocalDateTime localDateTime) {
        if (localDateTime != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        return null;
    }
}
