package aurocosh.divinefavor.common.muliblock.serialization;

import aurocosh.divinefavor.common.muliblock.validators.AirStateValidator;
import aurocosh.divinefavor.common.muliblock.validators.BlockStateValidator;
import aurocosh.divinefavor.common.muliblock.validators.MultiBlockStateValidator;
import aurocosh.divinefavor.common.muliblock.validators.StateValidator;
import com.google.gson.*;
import net.minecraft.util.ResourceLocation;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StateValidatorSerializer implements JsonDeserializer<StateValidator>, JsonSerializer<StateValidator> {
    @Override
    public StateValidator deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        if (type.equals("block")) {
            String blockName = jsonObject.get("name").getAsString();
            return new BlockStateValidator(new ResourceLocation(blockName));
        }
        else if (type.equals("blocks")) {
            List<ResourceLocation> names = new ArrayList<>();
            JsonArray blocks = jsonObject.getAsJsonArray("names");
            for (JsonElement block : blocks)
                names.add(new ResourceLocation(block.toString()));
            return new MultiBlockStateValidator(names);

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
        if (src instanceof MultiBlockStateValidator) {
            json.addProperty("type", "blocks");
            MultiBlockStateValidator validator = (MultiBlockStateValidator) src;
            JsonArray blocks = new JsonArray();
            for (ResourceLocation name : validator.names)
                blocks.add(name.toString());
            json.add("names", blocks);
        }
        else if (src instanceof AirStateValidator) {
            json.addProperty("type", "air");
        }
        return json;
    }
}

