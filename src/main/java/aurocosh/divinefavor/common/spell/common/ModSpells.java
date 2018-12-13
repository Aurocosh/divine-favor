package aurocosh.divinefavor.common.spell.common;

import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spell.*;
import aurocosh.divinefavor.common.spell.base.ModSpell;

public final class ModSpells {
    public static ModSpell arrow_throw;
    public static ModSpell bonemeal;
    public static ModSpell combustion;
    public static ModSpell crushing_palm;
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

    public static void preInit() {
        arrow_throw = ModRegistries.spells.register(new SpellArrowThrow());
        bonemeal = ModRegistries.spells.register(new SpellBonemeal());
        combustion = ModRegistries.spells.register(new SpellCombustion());
        crushing_palm = ModRegistries.spells.register(new SpellCrushingPalm());
        empower_axe = ModRegistries.spells.register(new SpellEmpowerAxe());
        empower_pickaxe = ModRegistries.spells.register(new SpellEmpowerPickaxe());
        fell_tree = ModRegistries.spells.register(new SpellFellTree());
        ignition = ModRegistries.spells.register(new SpellIgnition());
        lavawalking = ModRegistries.spells.register(new SpellLavawalking());
        small_fireball_throw = ModRegistries.spells.register(new SpellSmallFireballThrow());
        snowball_throw = ModRegistries.spells.register(new SpellSnowballThrow());
        stone_fever = ModRegistries.spells.register(new SpellStoneFever());
        stoneball_throw = ModRegistries.spells.register(new SpellStoneballThrow());
        tell_time = ModRegistries.spells.register(new SpellTellTime());
        waterwalking = ModRegistries.spells.register(new SpellWaterwalking());
        wooden_punch = ModRegistries.spells.register(new SpellWoodenPunch());
    }
}