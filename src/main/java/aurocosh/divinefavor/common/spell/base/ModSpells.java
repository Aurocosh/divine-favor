package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.constants.LibSpellNames;
import aurocosh.divinefavor.common.registry.SpellRequirementRegestry;
import aurocosh.divinefavor.common.requirements.base.SpellRequirement;
import aurocosh.divinefavor.common.spell.*;

import java.util.HashMap;
import java.util.Map;

public final class ModSpells {
    private static Map<SpellType, Spell> spellMap = new HashMap<>();

    public static Spell arrow_throw;
    public static Spell bonemeal;
    public static Spell empower_axe;
    public static Spell fell_tree;
    public static Spell ignition;
    public static Spell lavawalking;
    public static Spell snowball_throw;
    public static Spell stoneball_throw;
    public static Spell small_fireball_throw;
    public static Spell waterwalking;

    public static void init() {
        arrow_throw = register(new SpellArrowThrow());
        bonemeal = register(new SpellBonemeal());
        empower_axe = register(new SpellEmpowerAxe());
        fell_tree = register(new SpellFellTree());
        ignition = register(new SpellIgnition());
        lavawalking = register(new SpellLavawalking());
        snowball_throw = register(new SpellSnowballThrow());
        stoneball_throw = register(new SpellStoneballThrow());
        small_fireball_throw = register(new SpellSmallFireballThrow());
        waterwalking = register(new SpellWaterwalking());
    }

    public static Spell get(SpellType name) {
        return spellMap.get(name);
    }

    public static Spell register(Spell spell) {
        spellMap.put(spell.type, spell);
//        SpellRequirementRegestry.register(spell);
        return spell;
    }
}