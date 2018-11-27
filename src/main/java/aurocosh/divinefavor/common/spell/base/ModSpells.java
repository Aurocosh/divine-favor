package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.registry.SpellRegestry;
import aurocosh.divinefavor.common.spell.*;

import java.util.HashMap;
import java.util.Map;

public final class ModSpells {
    private static Map<SpellType, ModSpell> spellMap = new HashMap<>();

    public static ModSpell arrow_throw;
    public static ModSpell bonemeal;
    public static ModSpell empower_axe;
    public static ModSpell fell_tree;
    public static ModSpell ignition;
    public static ModSpell lavawalking;
    public static ModSpell snowball_throw;
    public static ModSpell stoneball_throw;
    public static ModSpell small_fireball_throw;
    public static ModSpell waterwalking;

    public static void preInit() {
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

    public static ModSpell get(SpellType name) {
        return spellMap.get(name);
    }

    public static ModSpell register(ModSpell modSpell) {
        spellMap.put(modSpell.type, modSpell);
        SpellRegestry.register(modSpell);
        return modSpell;
    }
}