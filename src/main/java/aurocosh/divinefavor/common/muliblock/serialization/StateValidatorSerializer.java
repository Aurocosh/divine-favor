package aurocosh.divinefavor.common.muliblock.serialization;

import aurocosh.divinefavor.common.muliblock.parts.AirStateValidator;
import aurocosh.divinefavor.common.muliblock.parts.BlockStateValidator;
import aurocosh.divinefavor.common.muliblock.parts.CenterStateValidator;
import aurocosh.divinefavor.common.muliblock.parts.StateValidator;
import com.google.gson.*;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;

public class StateValidatorSerializer implements JsonDeserializer<StateValidator>, JsonSerializer<StateValidator> {
    @Override
    public StateValidator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        if (type.equals("block")) {
            String blockName = jsonObject.get("name").getAsString();
            return new BlockStateValidator(new ResourceLocation(blockName));
        }
        else if (type.equals("center")) {
            String blockName = jsonObject.get("name").getAsString();
            return new CenterStateValidator(new ResourceLocation(blockName));
        }
        else if (type.equals("air"))
            return new AirStateValidator();
        return null;
    }

    @Override
    public JsonElement serialize(StateValidator src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        if (src instanceof BlockStateValidator) {
            json.addProperty("type", "block");
            BlockStateValidator validator = (BlockStateValidator) src;
            json.addProperty("name", validator.name.toString());
        }
        if (src instanceof CenterStateValidator) {
            json.addProperty("type", "center");
            CenterStateValidator validator = (CenterStateValidator) src;
            json.addProperty("name", validator.name.toString());
        }
        else if (src instanceof AirStateValidator) {
            json.addProperty("type", "air");
        }
        return json;
    }
}

