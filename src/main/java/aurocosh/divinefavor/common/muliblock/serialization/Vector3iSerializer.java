package aurocosh.divinefavor.common.muliblock.serialization;
import com.google.gson.*;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Type;

public class Vector3iSerializer implements JsonDeserializer<BlockPos>, JsonSerializer<BlockPos> {
    @Override
    public BlockPos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        long value = json.getAsLong();
        return BlockPos.fromLong(value);
    }

    @Override
    public JsonElement serialize(BlockPos src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src.toLong());
    }
}

