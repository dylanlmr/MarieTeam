package fr.xkgd.marieteam.adapter.serialization;

import android.os.Build;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter implements JsonDeserializer<LocalTime>, JsonSerializer<LocalTime> {

    private static DateTimeFormatter formatter;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        }
    }

    /**
     * Deserialize a {@link LocalTime} from a {@link JsonElement}.
     *
     * @param json    The json element to deserialize.
     * @param typeOfT The type of the Object to deserialize to.
     * @param context The context of the deserialization.
     * @return A deserialized {@link LocalTime}.
     * @throws JsonParseException If the json element is not a valid representation of a {@link LocalTime}.
     */
    @Override
    public LocalTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalTime.parse(json.getAsString(), formatter);
        }
        return null;
    }

    /**
     * Serialize a {@link LocalTime} to a {@link JsonElement}.
     *
     * @param src       The {@link LocalTime} to serialize.
     * @param typeOfSrc The type of the {@link LocalTime} to serialize.
     * @param context   The context of the serialization.
     * @return A serialized {@link JsonElement}.
     */
    @Override
    public JsonElement serialize(LocalTime src, Type typeOfSrc, JsonSerializationContext context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return context.serialize(src.format(formatter));
        }
        return null;
    }
}
