package aurocosh.divinefavor.common.muliblock.serialization;

import aurocosh.divinefavor.common.muliblock.parts.BlockStateValidator;
import aurocosh.divinefavor.common.muliblock.parts.StateValidator;
import com.google.gson.*;
import net.minecraft.block.Block;

import java.lang.reflect.Type;

public class StateValidatorSerializer implements JsonDeserializer<StateValidator>, JsonSerializer<StateValidator> {
    @Override
    public StateValidator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        if (type.equals("block")) {
            String blockName = jsonObject.get("name").getAsString();
            Block block = Block.getBlockFromName(blockName);
            return new BlockStateValidator(block);
        }
        return null;
    }

    @Override
    public JsonElement serialize(StateValidator src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        if (src instanceof BlockStateValidator) {
            json.addProperty("type", "block");
            BlockStateValidator validator = (BlockStateValidator) src;
            json.addProperty("name", validator.block.getRegistryName().toString());
            return json;
        }
        return json;
    }
}

