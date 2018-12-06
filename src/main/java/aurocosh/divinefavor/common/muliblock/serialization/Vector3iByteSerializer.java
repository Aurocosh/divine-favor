package aurocosh.divinefavor.common.muliblock.serialization;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.muliblock.parts.BlockStateValidator;
import com.google.gson.*;

import java.lang.reflect.Type;

public class Vector3iByteSerializer implements JsonDeserializer<Vector3i>, JsonSerializer<Vector3i> {
    @Override
    public Vector3i deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonObject = json.getAsJsonArray();

        byte x = jsonObject.get(0).getAsByte();
        byte y = jsonObject.get(1).getAsByte();
        byte z = jsonObject.get(2).getAsByte();
        return new Vector3i(x,y,z);
    }

    @Override
    public JsonElement serialize(Vector3i src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray json = new JsonArray();
        json.add((byte)src.x);
        json.add((byte)src.y);
        json.add((byte)src.z);
        return json;
    }
}

