package aurocosh.divinefavor.common.requirements.base;

import aurocosh.divinefavor.common.lib.LibFavorType;
import aurocosh.divinefavor.common.lib.LibSpellRequirementNames;
import aurocosh.divinefavor.common.requirements.SpellRequrementFree;

import java.util.HashMap;
import java.util.Map;

public final class ModSpellRequirements {
    public static SpellRequirement free;

    private static Map<String, SpellRequirement> requirements = new HashMap<>();

    public static void init() {
        free = register(new SpellRequrementFree());
        register(new SpellRequirement(LibSpellRequirementNames.ARROW_THROW, LibFavorType.ARROW_THROW, false));
        register(new SpellRequirement(LibSpellRequirementNames.BONEMEAL, LibFavorType.BONEMEAL, false));
        register(new SpellRequirement(LibSpellRequirementNames.EMPOWER_AXE, LibFavorType.EMPOWER_AXE, false));
        register(new SpellRequirement(LibSpellRequirementNames.FELL_TREE, LibFavorType.FELL_TREE, false));
        register(new SpellRequirement(LibSpellRequirementNames.IGNITION, LibFavorType.IGNITION, false));
        register(new SpellRequirement(LibSpellRequirementNames.LAVAWALKING, LibFavorType.LAVAWALKING, false));
        register(new SpellRequirement(LibSpellRequirementNames.SNOWBALL_THROW, LibFavorType.SNOWBALL_THROW, false));
        register(new SpellRequirement(LibSpellRequirementNames.STONEBALL_THROW, LibFavorType.STONEBALL_THROW, false));
        register(new SpellRequirement(LibSpellRequirementNames.SMALL_FIREBALL_THROW, LibFavorType.SMALL_FIREBALL_THROW, false));
        register(new SpellRequirement(LibSpellRequirementNames.WATERWALKING, LibFavorType.WATERWALKING, false));
    }

    public static SpellRequirement getRequirement(String name){
        return requirements.get(name);
    }

    public static SpellRequirement register(SpellRequirement spellRequirement) {
        requirements.put(spellRequirement.name,spellRequirement);
        return spellRequirement;
    }
}