package fr.xkgd.marieteam.adapter.serialization;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializer;

public class BooleanAdapter implements JsonDeserializer<Boolean>, JsonSerializer<Boolean> {

    /**
     * Deserialize a JsonElement into a Boolean.
     * @param json The Json data being deserialized
     * @param typeOfT The type of the Object to deserialize to
     * @param context JsonDeserializationContext
     * @return A Boolean represented by the JsonElement
     * @throws JsonParseException if json is not a valid representation for an object of typeOfT
     */
    @Override
    public Boolean deserialize(com.google.gson.JsonElement json, java.lang.reflect.Type typeOfT, com.google.gson.JsonDeserializationContext context) throws com.google.gson.JsonParseException {
        String value = json.getAsString();
        return "1".equals(value) || "true".equals(value);
    }

    /**
     * Serialize a Boolean into a JsonElement.
     * @param src The Boolean value to serialize
     * @param typeOfSrc The actual type (fully generalized version) of the source object
     * @param context JsonSerializationContext
     * @return JsonElement corresponding to the Boolean
     */
    @Override
    public com.google.gson.JsonElement serialize(Boolean src, java.lang.reflect.Type typeOfSrc, com.google.gson.JsonSerializationContext context) {
        return context.serialize(src ? "1" : "0");
    }
}
