package aurocosh.divinefavor.common.item.talismans.spell.common;

import aurocosh.divinefavor.common.config.common.ConfigTalismans;
import aurocosh.divinefavor.common.entity.minions.MinionZombie;
import aurocosh.divinefavor.common.favor.ModFavors;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.item.talismans.spell.*;
import aurocosh.divinefavor.common.item.talismans.spell.base.*;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.util.UtilTick;

public final class ModSpellTalismans {
    public static ItemSpellTalisman armor_of_pacifist;
    public static ItemSpellTalisman arrow_throw_talisman;
    public static ItemSpellTalisman blade_of_grass;
    public static ItemSpellTalisman blazing_palm;
    public static ItemSpellTalisman blink;
    public static ItemSpellTalisman blood_of_grass;
    public static ItemSpellTalisman bonemeal;
    public static ItemSpellTalisman butchering_strike;
    public static ItemSpellTalisman clock;
    public static ItemSpellTalisman combustion;
    public static ItemSpellTalisman consuming_fury;
    public static ItemSpellTalisman crushing_palm;
    public static ItemSpellTalisman crystalline_road;
    public static ItemSpellTalisman distant_spark;
    public static ItemSpellTalisman earthen_dive;
    public static ItemSpellTalisman empower_axe;
    public static ItemSpellTalisman empower_pickaxe;
    public static ItemSpellTalisman escape_plan;
    public static ItemSpellTalisman evil_eye;
    public static ItemSpellTalisman fall_negation;
    public static ItemSpellTalisman fell_tree;
    public static ItemSpellTalisman fins;
    public static ItemSpellTalisman focused_fury;
    public static ItemSpellTalisman gills;
    public static ItemSpellTalisman green_cycle;
    public static ItemSpellTalisman ground_flow;
    public static ItemSpellTalisman grudge;
    public static ItemSpellTalisman harvest;
    public static ItemSpellTalisman heat_wave;
    public static ItemSpellTalisman hellisphere;
    public static ItemSpellTalisman ignition;
    public static ItemSpellTalisman infernal_touch;
    public static ItemSpellTalisman invite_gem;
    public static ItemSpellTalisman invite_pebble;
    public static ItemSpellTalisman miners_focus;
    public static ItemSpellTalisman mist_blade;
    public static ItemSpellTalisman molten_skin;
    public static ItemSpellTalisman nether_surge;
    public static ItemSpellTalisman night_eye;
    public static ItemSpellTalisman obsidian_road;
    public static ItemSpellTalisman overblink;
    public static ItemSpellTalisman overwarp;
    public static ItemSpellTalisman pearl_crumbs;
    public static ItemSpellTalisman piercing_inferno;
    public static ItemSpellTalisman remote_chest;
    public static ItemSpellTalisman searing_pulse;
    public static ItemSpellTalisman small_fireball_throw;
    public static ItemSpellTalisman snowball_throw;
    public static ItemSpellTalisman spider_might;
    public static ItemSpellTalisman starvation;
    public static ItemSpellTalisman stone_fever;
    public static ItemSpellTalisman stoneball_throw;
    public static ItemSpellTalisman summon_zombie;
    public static ItemSpellTalisman surface_blink;
    public static ItemSpellTalisman surface_shift;
    public static ItemSpellTalisman target;
    public static ItemSpellTalisman toadic_jump;
    public static ItemSpellTalisman vitalize;
    public static ItemSpellTalisman wall_slip;
    public static ItemSpellTalisman warp;
    public static ItemSpellTalisman warp_gem;
    public static ItemSpellTalisman warp_pebble;
    public static ItemSpellTalisman wild_sprint;
    public static ItemSpellTalisman wind_step;
    public static ItemSpellTalisman winter_breath;
    public static ItemSpellTalisman wooden_punch;

    public static void preInit() {
        // allfire
        blazing_palm = new SpellTalismanBlazingPalm("blazing_palm", ModFavors.allfire, 1, SpellOptions.ALL_CAST);
        combustion = new SpellTalismanCombustion("combustion", ModFavors.allfire, 1, SpellOptions.USE_CAST);
        distant_spark = new SpellTalismanIgnition("distant_spark", ModFavors.allfire, 2, SpellOptions.ALL_CAST_TRACE);
        heat_wave = new SpellTalismanHeatWave("heat_wave", ModFavors.allfire, 1, SpellOptions.ALL_CAST);
        ignition = new SpellTalismanIgnition("ignition", ModFavors.allfire, 1, SpellOptions.USE_CAST);
        small_fireball_throw = new SpellTalismanSmallFireballThrow("small_fireball_throw", ModFavors.allfire, 1, SpellOptions.ALL_CAST);

        // arbow
        arrow_throw_talisman = new SpellTalismanArrowThrow("arrow_throw", ModFavors.arbow, 1, SpellOptions.ALL_CAST);

        // blizzrabi
        armor_of_pacifist = new SpellTalismanModPotionToggle("armor_of_pacifist", ModFavors.blizrabi, 10, ModPotions.armor_of_pacifist);
        crystalline_road = new SpellTalismanModPotion("crystalline_road", ModFavors.blizrabi, 10, ModPotions.crystalline_road, UtilTick.minutesToTicks(1));
        obsidian_road = new SpellTalismanModPotion("obsidian_road", ModFavors.blizrabi, 5, ModPotions.obsidian_road, UtilTick.minutesToTicks(1));
        snowball_throw = new SpellTalismanSnowballThrow("snowball_throw", ModFavors.blizrabi, 1, SpellOptions.ALL_CAST);
        winter_breath = new SpellTalismanWinterBreath("winter_breath", ModFavors.blizrabi, 1, SpellOptions.ALL_CAST);

        // endererer
        blink = new SpellTalismanBlink("blink", ModFavors.endererer, 10, SpellOptions.ALL_CAST, true, 5);
        earthen_dive = new SpellTalismanEarthenDive("earthen_dive", ModFavors.endererer, 1, SpellOptions.USE_CAST);
        escape_plan = new SpellTalismanEscapePlan("escape_plan", ModFavors.endererer, 1, SpellOptions.ALL_CAST);
        invite_gem = new SpellTalismanInviteMarker("invite_gem", ModFavors.endererer, 1, SpellOptions.ALL_CAST, ModItems.invite_gem);
        invite_pebble = new SpellTalismanInviteMarker("invite_pebble", ModFavors.endererer, 5, SpellOptions.ALL_CAST, ModItems.invite_pebble);
        overblink = new SpellTalismanBlink("overblink", ModFavors.endererer, 10, SpellOptions.ALL_CAST, true, 50);
        overwarp = new SpellTalismanBlink("overwarp", ModFavors.endererer, 20, SpellOptions.ALL_CAST, false, 80);
        pearl_crumbs = new SpellTalismanPearlCrumbs("pearl_crumbs", ModFavors.endererer, 1, SpellOptions.ALL_CAST);
        remote_chest = new SpellTalismanRemoteChest("remote_chest", ModFavors.endererer, 10, SpellOptions.USE_CAST, ModItems.storage_gem);
        surface_blink = new SpellTalismanSurfaceBlink("surface_blink", ModFavors.endererer, 1, SpellOptions.ALL_CAST_TRACE);
        surface_shift = new SpellTalismanSurfaceShift("surface_shift", ModFavors.endererer, 1, SpellOptions.ALL_CAST);
        wall_slip = new SpellTalismanWallSlip("wall_slip", ModFavors.endererer, 1, SpellOptions.USE_CAST);
        warp = new SpellTalismanBlink("warp", ModFavors.endererer, 30, SpellOptions.ALL_CAST, false, 8);
        warp_gem = new SpellTalismanWarpMarker("warp_gem", ModFavors.endererer, 1, SpellOptions.ALL_CAST, ModItems.warp_gem);
        warp_pebble = new SpellTalismanWarpMarker("warp_pebble", ModFavors.endererer, 5, SpellOptions.ALL_CAST, ModItems.warp_pebble);

        // greehay
        blade_of_grass = new SpellTalismanBladeOfGrass("blade_of_grass", ModFavors.allfire, 1, SpellOptions.ALL_CAST);
        blood_of_grass = new SpellTalismanBloodOfGrass("blood_of_grass", ModFavors.allfire, 1, SpellOptions.ALL_CAST);
        bonemeal = new SpellTalismanBonemeal("bonemeal", ModFavors.allfire, 1, SpellOptions.USE_CAST);
        clock = new SpellTalismanClock("clock", ModFavors.allfire, 1, SpellOptions.ALL_CAST);
        green_cycle = new SpellTalismanGreenCycle("green_cycle", ModFavors.allfire, 1, SpellOptions.ALL_CAST);
        harvest = new SpellTalismanHarvest("harvest", ModFavors.allfire, 1, SpellOptions.ALL_CAST);
        starvation = new SpellTalismanModPotion("starvation", ModFavors.allfire, 3, ModPotions.starvation, UtilTick.minutesToTicks(2.5f));

        // moonoom
        night_eye = new SpellTalismanModPotionToggle("night_eye", ModFavors.allfire, 4, ModPotions.night_eye);
        spider_might = new SpellTalismanModPotion("spider_might", ModFavors.allfire, ConfigTalismans.spider_might.uses, ModPotions.spider_might, ConfigTalismans.spider_might.duration);
        summon_zombie = new SpellTalismanSummonMinion<>("summon_zombie", ModFavors.allfire, 1, SpellOptions.USE_CAST, MinionZombie.class);
        target = new SpellTalismanTarget("target", ModFavors.allfire,0, SpellOptions.ENTITY_CAST);

        // nefir
        evil_eye = new SpellTalismanTarget("evil_eye", ModFavors.nefir,10, SpellOptions.ENTITY_CAST);
        hellisphere = new SpellTalismanHellisphere("hellisphere", ModFavors.nefir, 1, SpellOptions.USE_CAST);
        infernal_touch = new SpellTalismanInfernalTouch("infernal_touch", ModFavors.nefir, 1, SpellOptions.USE_CAST);
        molten_skin = new SpellTalismanMoltenSkin("molten_skin", ModFavors.nefir, 1, SpellOptions.ALL_CAST);
        nether_surge = new SpellTalismanNetherSurge("nether_surge", ModFavors.nefir, 1, SpellOptions.USE_CAST);
        piercing_inferno = new SpellTalismanPiercingInferno("piercing_inferno", ModFavors.nefir, 1, SpellOptions.USE_CAST);
        searing_pulse = new SpellTalismanSearingPulse("searing_pulse", ModFavors.nefir, 1, SpellOptions.USE_CAST);

        // redwind
        fall_negation = new SpellTalismanModPotionCharge("fall_negation", ModFavors.redwind, 1, ModPotions.fall_negation, 3);
        toadic_jump = new SpellTalismanModPotionToggle("toadic_jump", ModFavors.redwind, 1, ModPotions.toadic_jump);
        wild_sprint = new SpellTalismanModPotion("wild_sprint", ModFavors.redwind, 3, ModPotions.wild_charge, UtilTick.secondsToTicks(10));
        wind_step = new SpellTalismanWindStep("wind_step", ModFavors.redwind, 1, SpellOptions.ALL_CAST);

        // romol
        crushing_palm = new SpellTalismanModPotionToggle("crushing_palm", ModFavors.romol, 10, ModPotions.crushing_palm);
        empower_pickaxe = new SpellTalismanModPotion("empower_pickaxe", ModFavors.romol, 10, ModPotions.empower_pickaxe, UtilTick.minutesToTicks(1));
        ground_flow = new SpellTalismanModPotionToggle("ground_flow", ModFavors.romol, 10, ModPotions.ground_flow);
        miners_focus = new SpellTalismanMinersFocus("miners_focus", ModFavors.romol, 1, SpellOptions.ALL_CAST);
        stone_fever = new SpellTalismanModPotion("stone_fever", ModFavors.romol, 2, ModPotions.stone_fever, UtilTick.minutesToTicks(5));
        stoneball_throw = new SpellTalismanStoneballThrow("stoneball_throw", ModFavors.romol, 1, SpellOptions.ALL_CAST);

        // squarefury
        butchering_strike = new SpellTalismanModPotionCharge("butchering_strike", ModFavors.squarefury, 10, ModPotions.butchering_strike, 6);
        consuming_fury = new SpellTalismanModPotion("consuming_fury", ModFavors.squarefury, 10, ModPotions.consuming_fury, UtilTick.minutesToTicks(1));
        focused_fury = new SpellTalismanModPotion("focused_fury", ModFavors.squarefury, 2, ModPotions.focused_fury, UtilTick.minutesToTicks(1));
        grudge = new SpellTalismanGrudge("grudge", ModFavors.squarefury, 1, SpellOptions.ALL_CAST);
        mist_blade = new SpellTalismanModPotionToggle("mist_blade", ModFavors.squarefury, 3, ModPotions.mist_blade);

        // timber
        empower_axe = new SpellTalismanModPotion("empower_axe", ModFavors.timber, 3, ModPotions.empower_axe, UtilTick.minutesToTicks(1));
        fell_tree = new SpellTalismanFellTree("fell_tree", ModFavors.timber, 1, SpellOptions.USE_CAST);
        wooden_punch = new SpellTalismanModPotionToggle("wooden_punch", ModFavors.timber, 2, ModPotions.wooden_punch);

        // voda
        fins = new SpellTalismanModPotion("fins", ModFavors.timber, ConfigTalismans.fins.uses, ModPotions.fins, ConfigTalismans.fins.duration);
        gills = new SpellTalismanModPotionToggle("gills", ModFavors.timber, 5, ModPotions.gills);
        vitalize = new SpellTalismanVitalize("vitalize", ModFavors.timber, 1, SpellOptions.ALL_CAST);
    }

    public static void init() {
    }
}