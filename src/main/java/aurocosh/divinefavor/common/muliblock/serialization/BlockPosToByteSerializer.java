package aurocosh.divinefavor.common.muliblock.serialization;

import com.google.gson.*;
import net.minecraft.util.math.BlockPos;

import java.lang.reflect.Type;

public class BlockPosToByteSerializer implements JsonDeserializer<BlockPos>, JsonSerializer<BlockPos> {
    @Override
    public BlockPos deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonArray jsonObject = json.getAsJsonArray();

        byte x = jsonObject.get(0).getAsByte();
        byte y = jsonObject.get(1).getAsByte();
        byte z = jsonObject.get(2).getAsByte();
        return new BlockPos(x, y, z);
    }

    @Override
    public JsonElement serialize(BlockPos src, Type typeOfSrc, JsonSerializationContext context) {
        JsonArray json = new JsonArray();
        json.add((byte) src.getX());
        json.add((byte) src.getY());
        json.add((byte) src.getZ());
        return json;
    }
}

