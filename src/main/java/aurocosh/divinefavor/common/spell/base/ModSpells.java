package aurocosh.divinefavor.common.spell.base;

import aurocosh.divinefavor.common.registry.base.CommonRegistry;
import aurocosh.divinefavor.common.spell.*;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class ModSpells {
    private static Map<ResourceLocation, ModSpell> spells = new HashMap<>();

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
    public static ModSpell tell_time;

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
        tell_time = register(new SpellTellTime());
        waterwalking = register(new SpellWaterwalking());
    }

    public static ModSpell register(ModSpell spell) {
        spells.put(spell.getRegistryName(), spell);
        CommonRegistry.register(spell);
        return spell;
    }
}