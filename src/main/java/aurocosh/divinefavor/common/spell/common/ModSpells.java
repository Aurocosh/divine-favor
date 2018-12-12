package aurocosh.divinefavor.common.spell.common;

import aurocosh.divinefavor.common.registry.RegistryMap;
import aurocosh.divinefavor.common.registry.common.CommonRegistry;
import aurocosh.divinefavor.common.spell.*;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public final class ModSpells {
    private static RegistryMap<ModSpell> spells = new RegistryMap<>();

    public static ModSpell arrow_throw;
    public static ModSpell bonemeal;
    public static ModSpell empower_axe;
    public static ModSpell empower_pickaxe;
    public static ModSpell fell_tree;
    public static ModSpell ignition;
    public static ModSpell lavawalking;
    public static ModSpell small_fireball_throw;
    public static ModSpell snowball_throw;
    public static ModSpell stone_fever;
    public static ModSpell stoneball_throw;
    public static ModSpell tell_time;
    public static ModSpell waterwalking;
    public static ModSpell wooden_punch;
    public static ModSpell crushing_palm;

    public static void preInit() {
        arrow_throw = spells.register(new SpellArrowThrow());
        bonemeal = spells.register(new SpellBonemeal());
        empower_axe = spells.register(new SpellEmpowerAxe());
        empower_pickaxe = spells.register(new SpellEmpowerPickaxe());
        fell_tree = spells.register(new SpellFellTree());
        ignition = spells.register(new SpellIgnition());
        lavawalking = spells.register(new SpellLavawalking());
        small_fireball_throw = spells.register(new SpellSmallFireballThrow());
        snowball_throw = spells.register(new SpellSnowballThrow());
        stone_fever = spells.register(new SpellStoneFever());
        stoneball_throw = spells.register(new SpellStoneballThrow());
        tell_time = spells.register(new SpellTellTime());
        waterwalking = spells.register(new SpellWaterwalking());
        wooden_punch = spells.register(new SpellWoodenPunch());
        crushing_palm = spells.register(new SpellCrushingPalm());
    }
}