package aurocosh.divinefavor.common.muliblock.serialization;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import com.google.gson.*;

import java.lang.reflect.Type;

public class Vector3iSerializer implements JsonDeserializer<Vector3i>, JsonSerializer<Vector3i> {
    @Override
    public Vector3i deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        long value = json.getAsLong();
        return Vector3i.fromLong(value);
    }

    @Override
    public JsonElement serialize(Vector3i src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toLong());
    }
}

