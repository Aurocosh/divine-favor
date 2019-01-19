package aurocosh.divinefavor.common.item.talismans.base;

import aurocosh.divinefavor.common.config.common.ConfigTalismans;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.item.talismans.*;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.util.UtilTick;

public final class ModTalismans {
    public static ItemTalisman armor_of_pacifist;
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
    public static ItemTalisman fins;
    public static ItemTalisman focused_fury;
    public static ItemTalisman gills;
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
    public static ItemTalisman spider_might;
    public static ItemTalisman starvation;
    public static ItemTalisman stone_fever;
    public static ItemTalisman stoneball_throw;
    public static ItemTalisman surface_blink;
    public static ItemTalisman surface_shift;
    public static ItemTalisman toadic_jump;
    public static ItemTalisman vitalize;
    public static ItemTalisman wall_slip;
    public static ItemTalisman warp;
    public static ItemTalisman warp_gem;
    public static ItemTalisman warp_pebble;
    public static ItemTalisman wild_sprint;
    public static ItemTalisman wind_step;
    public static ItemTalisman winter_breath;
    public static ItemTalisman wooden_punch;

    public static void preInit() {
        armor_of_pacifist = ModRegistries.items.register(new TalismanModPotionToggle("armor_of_pacifist", 10, ModPotions.armor_of_pacifist));
        arrow_throw_talisman = ModRegistries.items.register(new TalismanArrowThrow());
        blade_of_grass = ModRegistries.items.register(new TalismanBladeOfGrass());
        blink = ModRegistries.items.register(new TalismanBlink("blink", 10, true, 10));
        blood_of_grass = ModRegistries.items.register(new TalismanBloodOfGrass());
        bonemeal = ModRegistries.items.register(new TalismanBonemeal());
        butchering_strike = ModRegistries.items.register(new TalismanModPotionCharge("butchering_strike", 10, ModPotions.butchering_strike, 6));
        clock = ModRegistries.items.register(new TalismanClock());
        combustion = ModRegistries.items.register(new TalismanCombustion());
        consuming_fury = ModRegistries.items.register(new TalismanModPotionTrigger("consuming_fury", 10, ModPotions.consuming_fury, UtilTick.minutesToTicks(1)));
        crushing_palm = ModRegistries.items.register(new TalismanModPotionToggle("crushing_palm", 10, ModPotions.crushing_palm));
        crystalline_road = ModRegistries.items.register(new TalismanModPotion("crystalline_road", 10, ModPotions.crystalline_road, UtilTick.minutesToTicks(1)));
        distant_spark = ModRegistries.items.register(new TalismanIgnition("distant_spark", 20, true));
        earthen_dive = ModRegistries.items.register(new TalismanEarthenDive());
        empower_axe = ModRegistries.items.register(new TalismanModPotion("empower_axe", 10, ModPotions.empower_axe, UtilTick.minutesToTicks(1)));
        empower_pickaxe = ModRegistries.items.register(new TalismanModPotion("empower_pickaxe", 10, ModPotions.empower_pickaxe, UtilTick.minutesToTicks(1)));
        escape_plan = ModRegistries.items.register(new TalismanEscapePlan());
        fall_negation = ModRegistries.items.register(new TalismanModPotionCharge("fall_negation", 10, ModPotions.fall_negation, 3));
        fell_tree = ModRegistries.items.register(new TalismanFellTree());
        fins = ModRegistries.items.register(new TalismanModPotion("fins", ConfigTalismans.fins.uses, ModPotions.fins, ConfigTalismans.fins.duration));
        focused_fury = ModRegistries.items.register(new TalismanModPotionTrigger("focused_fury", 2, ModPotions.focused_fury, UtilTick.minutesToTicks(1)));
        gills = ModRegistries.items.register(new TalismanModPotionToggle("gills", 5, ModPotions.gills));
        green_cycle = ModRegistries.items.register(new TalismanGreenCycle());
        ground_flow = ModRegistries.items.register(new TalismanModPotionToggle("ground_flow", 10, ModPotions.ground_flow));
        grudge = ModRegistries.items.register(new TalismanGrudge());
        harvest = ModRegistries.items.register(new TalismanHarvest());
        heat_wave = ModRegistries.items.register(new TalismanHeatWave());
        hellisphere = ModRegistries.items.register(new TalismanHellisphere());
        ignition = ModRegistries.items.register(new TalismanIgnition("ignition", 30, false));
        infernal_touch = ModRegistries.items.register(new TalismanInfernalTouch());
        miners_focus = ModRegistries.items.register(new TalismanMinersFocus());
        mist_blade = ModRegistries.items.register(new TalismanModPotionToggle("mist_blade", 3, ModPotions.mist_blade));
        molten_skin = ModRegistries.items.register(new TalismanMoltenSkin());
        nether_surge = ModRegistries.items.register(new TalismanNetherSurge());
        night_eye = ModRegistries.items.register(new TalismanModPotionToggle("night_eye", 4, ModPotions.night_eye));
        obsidian_road = ModRegistries.items.register(new TalismanModPotion("obsidian_road", 5, ModPotions.obsidian_road, UtilTick.minutesToTicks(1)));
        overblink = ModRegistries.items.register(new TalismanBlink("overblink", 10, true, 50));
        overwarp = ModRegistries.items.register(new TalismanBlink("overwarp", 20, false, 80));
        pearl_crumbs = ModRegistries.items.register(new TalismanPearlCrumbs());
        piercing_inferno = ModRegistries.items.register(new TalismanPiercingInferno());
        searing_pulse = ModRegistries.items.register(new TalismanSearingPulse());
        small_fireball_throw = ModRegistries.items.register(new TalismanSmallFireballThrow());
        snowball_throw = ModRegistries.items.register(new TalismanSnowballThrow());
        spider_might = ModRegistries.items.register(new TalismanModPotion("spider_might", ConfigTalismans.spider_might.uses, ModPotions.spider_might, ConfigTalismans.spider_might.duration));
        starvation = ModRegistries.items.register(new TalismanModPotion("starvation", 3, ModPotions.starvation, UtilTick.minutesToTicks(2.5f)));
        stone_fever = ModRegistries.items.register(new TalismanModPotion("stone_fever", 2, ModPotions.stone_fever, UtilTick.minutesToTicks(5)));
        stoneball_throw = ModRegistries.items.register(new TalismanStoneballThrow());
        surface_blink = ModRegistries.items.register(new TalismanSurfaceBlink());
        surface_shift = ModRegistries.items.register(new TalismanSurfaceShift());
        toadic_jump = ModRegistries.items.register(new TalismanModPotionToggle("toadic_jump", 10, ModPotions.toadic_jump));
        vitalize = ModRegistries.items.register(new TalismanVitalize());
        wall_slip = ModRegistries.items.register(new TalismanWallSlip());
        warp = ModRegistries.items.register(new TalismanBlink("warp", 30, false, 8));
        warp_gem = ModRegistries.items.register(new TalismanWarpMarker("warp_gem", 1, ModItems.warp_gem));
        warp_pebble = ModRegistries.items.register(new TalismanWarpMarker("warp_pebble", 5, ModItems.warp_pebble));
        wild_sprint = ModRegistries.items.register(new TalismanModPotionTrigger("wild_sprint", 3, ModPotions.wild_charge, UtilTick.secondsToTicks(10)));
        wind_step = ModRegistries.items.register(new TalismanWindStep());
        winter_breath = ModRegistries.items.register(new TalismanWinterBreath());
        wooden_punch = ModRegistries.items.register(new TalismanModPotionToggle("wooden_punch", 30, ModPotions.wooden_punch));

        ModMappers.talismans.register(ModRegistries.items.getValues(ItemTalisman.class));
    }

    public static void init() {
    }
}