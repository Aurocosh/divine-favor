package aurocosh.divinefavor.common.requirements.base;

import aurocosh.divinefavor.common.requirements.SpellRequrementEmpowerAxe;
import aurocosh.divinefavor.common.requirements.SpellRequrementFree;

import java.util.HashMap;
import java.util.Map;

public final class ModSpellRequirements {
    public static SpellRequirement free;
    public static SpellRequirement empower_axe;

    private static Map<String, SpellRequirement> requirements = new HashMap<>();

    public static void init() {
        free = register(new SpellRequrementFree());
        empower_axe = register(new SpellRequrementEmpowerAxe());
    }

    public static SpellRequirement getRequirement(String name){
        return requirements.get(name);
    }

    public static SpellRequirement register(SpellRequirement spellRequirement) {
        requirements.put(spellRequirement.name,spellRequirement);
        return spellRequirement;
    }
}