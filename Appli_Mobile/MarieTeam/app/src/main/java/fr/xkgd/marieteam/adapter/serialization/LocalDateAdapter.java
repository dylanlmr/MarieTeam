package fr.xkgd.marieteam.adapter.serialization;

import android.os.Build;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter implements JsonDeserializer<LocalDate>, JsonSerializer<LocalDate> {

    private static DateTimeFormatter formatter;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        }
    }

    /**
     * Deserialize a {@link LocalDate} from a {@link JsonElement}
     *
     * @param json    The json element to deserialize
     * @param typeOfT The type of the object to deserialize
     * @param context The context
     * @return The deserialized object
     * @throws JsonParseException If the json element is not a valid {@link LocalDate}
     */
    @Override
    public LocalDate deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDate.parse(json.getAsString(), formatter);
        }
        return null;
    }

    /**
     * Serialize a {@link LocalDate} to a {@link JsonElement}
     *
     * @param src       The {@link LocalDate} to serialize
     * @param typeOfSrc The type of the object to serialize
     * @param context   The context
     * @return The serialized object
     */
    @Override
    public JsonElement serialize(LocalDate src, Type typeOfSrc, JsonSerializationContext context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return context.serialize(src.format(formatter));
        }
        return null;
    }
}
