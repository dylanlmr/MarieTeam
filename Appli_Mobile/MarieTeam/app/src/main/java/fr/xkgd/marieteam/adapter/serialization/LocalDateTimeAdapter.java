package fr.xkgd.marieteam.adapter.serialization;

import android.os.Build;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter implements JsonDeserializer<LocalDateTime>, JsonSerializer<LocalDateTime> {

    private static DateTimeFormatter formatter;

    static {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * Deserialize a {@link LocalDateTime} from a {@link JsonElement}.
     *
     * @param json    The json element to deserialize.
     * @param typeOfT The type of the Object to deserialize to.
     * @param context The context of the deserialization.
     * @return A deserialized {@link LocalDateTime}.
     * @throws JsonParseException If the json element is not a valid representation of a {@link LocalDateTime}.
     */
    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return LocalDateTime.parse(json.getAsString(), formatter);
        }
        return null;
    }

    /**
     * Serialize a {@link LocalDateTime} to a {@link JsonElement}.
     *
     * @param src       The {@link LocalDateTime} to serialize.
     * @param typeOfSrc The type of the Object to serialize.
     * @param context   The context of the serialization.
     * @return A serialized {@link JsonElement}.
     */
    @Override
    public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return context.serialize(src.format(formatter));
        }
        return null;
    }
}
