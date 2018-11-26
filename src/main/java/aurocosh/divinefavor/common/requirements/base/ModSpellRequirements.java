package aurocosh.divinefavor.common.requirements.base;

import aurocosh.divinefavor.common.lib.LibMisc;
import aurocosh.divinefavor.common.registry.SpellRequirementRegestry;
import aurocosh.divinefavor.common.util.UtilAssets;
import com.google.gson.JsonObject;
import net.minecraft.util.ResourceLocation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class ModSpellRequirements {
    public static SpellRequirement free;

    private static Map<String, SpellRequirement> requirements = new HashMap<>();

    public static void init() {
        ResourceLocation resourceLocation = new ResourceLocation(LibMisc.MOD_ID,"requirements");

//        ArrayList<JsonObject> jsonObjects = UtilAssets.getAllJsonfiles(LibMisc.MOD_ID + "/requirements");
//        for (JsonObject jsonObject : jsonObjects) {
//            SpellRequirement requirement = SpellRequirement.deserialize(jsonObject);
//            register(requirement);
//        }
    }

    public static SpellRequirement getRequirement(String name) {
        return requirements.get(name);
    }

    public static SpellRequirement register(SpellRequirement spellRequirement) {
        requirements.put(spellRequirement.name, spellRequirement);
        SpellRequirementRegestry.register(spellRequirement);
        return spellRequirement;
    }
}