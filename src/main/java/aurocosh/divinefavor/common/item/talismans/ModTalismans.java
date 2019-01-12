package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.spell.*;

import java.util.List;

public final class ModTalismans {
    public static ItemTalisman arrow_throw_talisman;
    public static ItemTalisman blade_of_grass;
    public static ItemTalisman blink;
    public static ItemTalisman blood_of_grass;
    public static ItemTalisman bonemeal;
    public static ItemTalisman butchering_strike;
    public static ItemTalisman clock;
    public static ItemTalisman combustion;
    public static ItemTalisman consuming_fury;
    public static ItemTalisman crushing_palm;
    public static ItemTalisman crystalline_road;
    public static ItemTalisman distant_spark;
    public static ItemTalisman earthen_dive;
    public static ItemTalisman empower_axe;
    public static ItemTalisman empower_pickaxe;
    public static ItemTalisman escape_plan;
    public static ItemTalisman fall_negation;
    public static ItemTalisman fell_tree;
    public static ItemTalisman focused_fury;
    public static ItemTalisman green_cycle;
    public static ItemTalisman ground_flow;
    public static ItemTalisman grudge;
    public static ItemTalisman harvest;
    public static ItemTalisman heat_wave;
    public static ItemTalisman hellisphere;
    public static ItemTalisman ignition;
    public static ItemTalisman infernal_touch;
    public static ItemTalisman miners_focus;
    public static ItemTalisman mist_blade;
    public static ItemTalisman molten_skin;
    public static ItemTalisman nether_surge;
    public static ItemTalisman night_eye;
    public static ItemTalisman obsidian_road;
    public static ItemTalisman overblink;
    public static ItemTalisman overwarp;
    public static ItemTalisman pearl_crumbs;
    public static ItemTalisman piercing_inferno;
    public static ItemTalisman searing_pulse;
    public static ItemTalisman small_fireball_throw;
    public static ItemTalisman snowball_throw;
    public static ItemTalisman stone_fever;
    public static ItemTalisman stoneball_throw;
    public static ItemTalisman surface_blink;
    public static ItemTalisman surface_shift;
    public static ItemTalisman toadic_jump;
    public static ItemTalisman vitalize;
    public static ItemTalisman wall_slip;
    public static ItemTalisman warp;
    public static ItemTalisman wild_sprint;
    public static ItemTalisman wind_step;
    public static ItemTalisman winter_breath;
    public static ItemTalisman wooden_punch;

    public static void preInit() {
        arrow_throw_talisman = ModRegistries.items.register(new TalismanBuilder("arrow_throw",20)
                .setSpell(new SpellArrowThrow())
                .castOnUse()
                .castOnRighClick()
                .create());
        blade_of_grass = ModRegistries.items.register(new TalismanBuilder("blade_of_grass",5)
                .setSpell(new SpellBladeOfGrass())
                .castOnUse()
                .castOnRighClick()
                .create());
        blink = ModRegistries.items.register(new TalismanBuilder("blink",10)
                .setSpell(new SpellBlink(5,true))
                .castOnUse()
                .castOnRighClick()
                .create());
        blood_of_grass = ModRegistries.items.register(new TalismanBuilder("blood_of_grass",2)
                .setSpell(new SpellBloodOfGrass())
                .castOnUse()
                .castOnRighClick()
                .create());
        bonemeal = ModRegistries.items.register(new TalismanBuilder("bonemeal",10)
                .setSpell(new SpellBonemeal())
                .castOnUse()
                .create());
        butchering_strike = ModRegistries.items.register(new TalismanBuilder("butchering_strike",15)
                .setSpell(new SpellButcheringStrike())
                .castOnUse()
                .castOnRighClick()
                .create());
        combustion = ModRegistries.items.register(new TalismanBuilder("combustion",3)
                .setSpell(new SpellCombustion())
                .castOnUse()
                .create());
        consuming_fury = ModRegistries.items.register(new TalismanBuilder("consuming_fury",2)
                .setSpell(new SpellConsumingFury())
                .castOnUse()
                .castOnRighClick()
                .create());
        crushing_palm = ModRegistries.items.register(new TalismanBuilder("crushing_palm",20)
                .setSpell(new SpellCrushingPalm())
                .castOnUse()
                .castOnRighClick()
                .create());
        crystalline_road = ModRegistries.items.register(new TalismanBuilder("crystalline_road",5)
                .setSpell(new SpellCrystallineRoad())
                .castOnUse()
                .castOnRighClick()
                .create());
        distant_spark = ModRegistries.items.register(new TalismanBuilder("distant_spark",10)
                .setSpell(new SpellIgnition())
                .castOnUse()
                .castOnRighClick()
                .create());
        earthen_dive = ModRegistries.items.register(new TalismanBuilder("earthen_dive",10)
                .setSpell(new SpellEarthenDive())
                .castOnUse()
                .create());
        empower_axe = ModRegistries.items.register(new TalismanBuilder("empower_axe",3)
                .setSpell(new SpellEmpowerAxe())
                .castOnUse()
                .castOnRighClick()
                .create());
        empower_pickaxe = ModRegistries.items.register(new TalismanBuilder("empower_pickaxe",3)
                .setSpell(new SpellEmpowerPickaxe())
                .castOnUse()
                .castOnRighClick()
                .create());
        escape_plan = ModRegistries.items.register(new TalismanBuilder("escape_plan",3)
                .setSpell(new SpellEscapePlan())
                .castOnUse()
                .castOnRighClick()
                .create());
        fall_negation = ModRegistries.items.register(new TalismanBuilder("fall_negation",5)
                .setSpell(new SpellFallNegation())
                .castOnUse()
                .castOnRighClick()
                .create());
        fell_tree = ModRegistries.items.register(new TalismanBuilder("fell_tree",3)
                .setSpell(new SpellFellTree())
                .castOnUse()
                .create());
        focused_fury = ModRegistries.items.register(new TalismanBuilder("focused_fury",2)
                .setSpell(new SpellFocusedFury())
                .castOnUse()
                .castOnRighClick()
                .create());
        green_cycle = ModRegistries.items.register(new TalismanBuilder("green_cycle",5)
                .setSpell(new SpellGreenCycle())
                .castOnUse()
                .castOnRighClick()
                .create());
        ground_flow = ModRegistries.items.register(new TalismanBuilder("ground_flow",6)
                .setSpell(new SpellGroundFlow())
                .castOnUse()
                .castOnRighClick()
                .create());
        grudge = ModRegistries.items.register(new TalismanBuilder("grudge",2)
                .setSpell(new SpellGrudge())
                .castOnUse()
                .castOnRighClick()
                .create());
        harvest = ModRegistries.items.register(new TalismanBuilder("harvest",3)
                .setSpell(new SpellHarvest())
                .castOnUse()
                .castOnRighClick()
                .create());
        heat_wave = ModRegistries.items.register(new TalismanBuilder("heat_wave",10)
                .setSpell(new SpellHeatWave())
                .castOnUse()
                .castOnRighClick()
                .create());
        hellisphere = ModRegistries.items.register(new TalismanBuilder("hellisphere",10)
                .setSpell(new SpellHellisphere())
                .castOnUse()
                .create());
        ignition = ModRegistries.items.register(new TalismanBuilder("ignition",30)
                .setSpell(new SpellIgnition())
                .castOnUse()
                .create());
        infernal_touch = ModRegistries.items.register(new TalismanBuilder("infernal_touch",30)
                .setSpell(new SpellInfernalTouch())
                .castOnUse()
                .create());
        miners_focus = ModRegistries.items.register(new TalismanBuilder("miners_focus",2)
                .setSpell(new SpellMinersFocus())
                .castOnUse()
                .castOnRighClick()
                .create());
        mist_blade = ModRegistries.items.register(new TalismanBuilder("mist_blade",2)
                .setSpell(new SpellMistBlade())
                .castOnUse()
                .castOnRighClick()
                .create());
        molten_skin = ModRegistries.items.register(new TalismanBuilder("molten_skin",6)
                .setSpell(new SpellMoltenSkin())
                .castOnUse()
                .castOnRighClick()
                .create());
        nether_surge = ModRegistries.items.register(new TalismanBuilder("nether_surge",6)
                .setSpell(new SpellNetherSurge())
                .castOnUse()
                .create());
        night_eye = ModRegistries.items.register(new TalismanBuilder("night_eye",4)
                .setSpell(new SpellNightEye())
                .castOnUse()
                .castOnRighClick()
                .create());
        obsidian_road = ModRegistries.items.register(new TalismanBuilder("obsidian_road",5)
                .setSpell(new SpellObsidianRoad())
                .castOnUse()
                .castOnRighClick()
                .create());
        overblink = ModRegistries.items.register(new TalismanBuilder("overblink",10)
                .setSpell(new SpellBlink(50,true))
                .castOnUse()
                .castOnRighClick()
                .create());
        overwarp = ModRegistries.items.register(new TalismanBuilder("overwarp",20)
                .setSpell(new SpellBlink(80,false))
                .castOnUse()
                .castOnRighClick()
                .create());
        pearl_crumbs = ModRegistries.items.register(new TalismanBuilder("pearl_crumbs",10)
                .setSpell(new SpellPearlCrumbs())
                .castOnUse()
                .castOnRighClick()
                .create());
        piercing_inferno = ModRegistries.items.register(new TalismanBuilder("piercing_inferno",12)
                .setSpell(new SpellPiercingInferno())
                .castOnUse()
                .create());
        searing_pulse = ModRegistries.items.register(new TalismanBuilder("searing_pulse",6)
                .setSpell(new SpellSearingPulse())
                .castOnUse()
                .create());
        small_fireball_throw = ModRegistries.items.register(new TalismanBuilder("small_fireball_throw",10)
                .setSpell(new SpellSmallFireballThrow())
                .castOnRighClick()
                .create());
        snowball_throw = ModRegistries.items.register(new TalismanBuilder("snowball_throw",30)
                .setSpell(new SpellSnowballThrow())
                .castOnUse()
                .castOnRighClick()
                .create());
        stone_fever = ModRegistries.items.register(new TalismanBuilder("stone_fever",2)
                .setSpell(new SpellStoneFever())
                .castOnUse()
                .castOnRighClick()
                .create());
        stoneball_throw = ModRegistries.items.register(new TalismanBuilder("stoneball_throw",30)
                .setSpell(new SpellStoneballThrow())
                .castOnUse()
                .castOnRighClick()
                .create());
        surface_blink = ModRegistries.items.register(new TalismanBuilder("surface_blink",20)
                .setSpell(new SpellSurfaceBlink(20))
                .castOnUse()
                .castOnRighClick()
                .create());
        surface_shift = ModRegistries.items.register(new TalismanBuilder("surface_shift",10)
                .setSpell(new SpellSurfaceShift())
                .castOnUse()
                .create());
        toadic_jump = ModRegistries.items.register(new TalismanBuilder("toadic_jump",15)
                .setSpell(new SpellToadicJump())
                .castOnUse()
                .castOnRighClick()
                .create());
        vitalize = ModRegistries.items.register(new TalismanBuilder("vitalize",10)
                .setSpell(new SpellVitalize())
                .castOnUse()
                .castOnRighClick()
                .create());
        wall_slip = ModRegistries.items.register(new TalismanBuilder("wall_slip",20)
                .setSpell(new SpellWallSlip())
                .castOnUse()
                .create());
        warp = ModRegistries.items.register(new TalismanBuilder("warp",30)
                .setSpell(new SpellBlink(8,false))
                .castOnUse()
                .castOnRighClick()
                .create());
        wild_sprint = ModRegistries.items.register(new TalismanBuilder("wild_sprint",3)
                .setSpell(new SpellWildSprint())
                .castOnUse()
                .castOnRighClick()
                .create());
        winter_breath = ModRegistries.items.register(new TalismanBuilder("winter_breath",10)
                .setSpell(new SpellWinterBreath())
                .castOnUse()
                .castOnRighClick()
                .create());
        wind_step = ModRegistries.items.register(new TalismanBuilder("wind_step", 20)
                .setSpell(new SpellWindStep())
                .castOnUse()
                .castOnRighClick()
                .create());
        wooden_punch = ModRegistries.items.register(new TalismanBuilder("wooden_punch",30)
                .setSpell(new SpellWoodenPunch())
                .castOnUse()
                .castOnRighClick()
                .setIsFree()
                .create());
        clock = ModRegistries.items.register(new TalismanBuilder("clock",300)
                .setSpell(new SpellTellTime())
                .castOnUse()
                .castOnRighClick()
                .create());

        List<ItemTalisman> talismanList = ModRegistries.items.getValues(ItemTalisman.class);
        ModMappers.talismans.register(talismanList);
    }

    public static void init() {
    }
}