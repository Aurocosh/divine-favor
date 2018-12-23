package aurocosh.divinefavor.common.spell.common;

import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spell.*;
import aurocosh.divinefavor.common.spell.base.ModSpell;

public final class ModSpells {
    public static ModSpell arrow_throw;
    public static ModSpell bonemeal;
    public static ModSpell butchering_strike;
    public static ModSpell combustion;
    public static ModSpell consuming_fury;
    public static ModSpell crushing_palm;
    public static ModSpell crystalline_road;
    public static ModSpell earthen_dive;
    public static ModSpell empower_axe;
    public static ModSpell empower_pickaxe;
    public static ModSpell fall_negation;
    public static ModSpell fell_tree;
    public static ModSpell focused_fury;
    public static ModSpell grudge;
    public static ModSpell heat_wave;
    public static ModSpell ignition;
    public static ModSpell miners_focus;
    public static ModSpell obsidian_road;
    public static ModSpell searing_pulse;
    public static ModSpell small_fireball_throw;
    public static ModSpell snowball_throw;
    public static ModSpell stone_fever;
    public static ModSpell stoneball_throw;
    public static ModSpell surface_shift;
    public static ModSpell tell_time;
    public static ModSpell toadic_jump;
    public static ModSpell wild_sprint;
    public static ModSpell wind_step;
    public static ModSpell winter_breath;
    public static ModSpell wooden_punch;

    public static void preInit() {
        arrow_throw = ModRegistries.spells.register(new SpellArrowThrow());
        bonemeal = ModRegistries.spells.register(new SpellBonemeal());
        butchering_strike = ModRegistries.spells.register(new SpellButcheringStrike());
        combustion = ModRegistries.spells.register(new SpellCombustion());
        consuming_fury = ModRegistries.spells.register(new SpellConsumingFury());
        crushing_palm = ModRegistries.spells.register(new SpellCrushingPalm());
        crystalline_road = ModRegistries.spells.register(new SpellCrystallineRoad());
        earthen_dive = ModRegistries.spells.register(new SpellEarthenDive());
        empower_axe = ModRegistries.spells.register(new SpellEmpowerAxe());
        empower_pickaxe = ModRegistries.spells.register(new SpellEmpowerPickaxe());
        fall_negation = ModRegistries.spells.register(new SpellFallNegation());
        fell_tree = ModRegistries.spells.register(new SpellFellTree());
        focused_fury = ModRegistries.spells.register(new SpellFocusedFury());
        grudge = ModRegistries.spells.register(new SpellGrudge());
        heat_wave = ModRegistries.spells.register(new SpellHeatWave());
        ignition = ModRegistries.spells.register(new SpellIgnition());
        miners_focus = ModRegistries.spells.register(new SpellMinersFocus());
        obsidian_road = ModRegistries.spells.register(new SpellObsidianRoad());
        searing_pulse = ModRegistries.spells.register(new SpellSearingPulse());
        small_fireball_throw = ModRegistries.spells.register(new SpellSmallFireballThrow());
        snowball_throw = ModRegistries.spells.register(new SpellSnowballThrow());
        stone_fever = ModRegistries.spells.register(new SpellStoneFever());
        stoneball_throw = ModRegistries.spells.register(new SpellStoneballThrow());
        surface_shift = ModRegistries.spells.register(new SpellSurfaceShift());
        tell_time = ModRegistries.spells.register(new SpellTellTime());
        toadic_jump = ModRegistries.spells.register(new SpellToadicJump());
        wild_sprint = ModRegistries.spells.register(new SpellWildSprint());
        wind_step = ModRegistries.spells.register(new SpellWindStep());
        winter_breath = ModRegistries.spells.register(new SpellWinterBreath());
        wooden_punch = ModRegistries.spells.register(new SpellWoodenPunch());
    }
}