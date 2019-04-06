package aurocosh.divinefavor.common.integrations.patchouli.generation.serialization;

import aurocosh.divinefavor.common.integrations.patchouli.generation.pages.PatchouliPage;
import aurocosh.divinefavor.common.integrations.patchouli.generation.pages.SpellTalismanPatchouliPage;
import aurocosh.divinefavor.common.integrations.patchouli.generation.pages.SpotlightPatchouliPage;
import aurocosh.divinefavor.common.integrations.patchouli.generation.pages.TextPatchouliPage;
import com.google.gson.*;

import java.lang.reflect.Type;

public class StateValidatorSerializer implements JsonDeserializer<PatchouliPage>, JsonSerializer<PatchouliPage> {
    @Override
    public PatchouliPage deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        if (type.equals("spotlight")) {
            String item = jsonObject.get("item").getAsString();
            String text = jsonObject.get("text").getAsString();
            return new SpotlightPatchouliPage(item,text);
        }
        else if (type.equals("spell_talisman")) {
            String talisman = jsonObject.get("talisman").getAsString();
            return new SpellTalismanPatchouliPage(talisman);
        }
        else if (type.equals("text")) {
            String text = jsonObject.get("text").getAsString();
            return new TextPatchouliPage(text);
        }
        return null;
    }

    @Override
    public JsonElement serialize(PatchouliPage src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();
        if (src instanceof SpotlightPatchouliPage) {
            json.addProperty("type", "spotlight");
            SpotlightPatchouliPage patchouliPage = (SpotlightPatchouliPage) src;
            json.addProperty("item", patchouliPage.item);
            json.addProperty("text", patchouliPage.text);
        }
        if (src instanceof SpellTalismanPatchouliPage) {
            json.addProperty("type", "spell_talisman");
            SpellTalismanPatchouliPage validator = (SpellTalismanPatchouliPage) src;
            json.addProperty("talisman", validator.talisman);
        }
        else if (src instanceof TextPatchouliPage) {
            json.addProperty("type", "text");
            TextPatchouliPage validator = (TextPatchouliPage) src;
            json.addProperty("text", validator.text);
        }
        return json;
    }
}

