package aurocosh.divinefavor.common.item.talismans.spell.common;

import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.entity.minions.*;
import aurocosh.divinefavor.common.favor.ModFavors;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.item.talismans.spell.*;
import aurocosh.divinefavor.common.item.talismans.spell.base.*;
import aurocosh.divinefavor.common.potions.common.ModPotions;

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
    public static ItemSpellTalisman follow;
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
    public static ItemSpellTalisman summon_creeper;
    public static ItemSpellTalisman summon_husk;
    public static ItemSpellTalisman summon_skeleton;
    public static ItemSpellTalisman summon_stray;
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
        // arbow
        arrow_throw_talisman = new SpellTalismanArrowThrow("arrow_throw", ModFavors.arbow, ConfigSpells.arrowThrow.favorCost, SpellOptions.ALL_CAST);

        // blizzrabi
        armor_of_pacifist = new SpellTalismanModPotionToggle("armor_of_pacifist", ModFavors.blizrabi, ConfigSpells.armorOfPacifist.favorCost, ModPotions.armor_of_pacifist);
        crystalline_road = new SpellTalismanModPotion("crystalline_road", ModFavors.blizrabi, ConfigSpells.crystallineRoad.favorCost, ModPotions.crystalline_road, ConfigSpells.crystallineRoad.duration);
        fins = new SpellTalismanModPotion("fins", ModFavors.blizrabi, ConfigSpells.fins.favorCost, ModPotions.fins, ConfigSpells.fins.duration);
        gills = new SpellTalismanModPotionToggle("gills", ModFavors.blizrabi, ConfigSpells.gills.favorCost, ModPotions.gills);
        obsidian_road = new SpellTalismanModPotion("obsidian_road", ModFavors.blizrabi, ConfigSpells.obsidianRoad.favorCost, ModPotions.obsidian_road, ConfigSpells.obsidianRoad.duration);
        snowball_throw = new SpellTalismanSnowballThrow("snowball_throw", ModFavors.blizrabi, ConfigSpells.snowballThrow.favorCost, SpellOptions.ALL_CAST);
        vitalize = new SpellTalismanVitalize("vitalize", ModFavors.blizrabi, ConfigSpells.vitalize.favorCost, SpellOptions.ALL_CAST);
        winter_breath = new SpellTalismanWinterBreath("winter_breath", ModFavors.blizrabi, ConfigSpells.winterBreath.favorCost, SpellOptions.ALL_CAST);

        // endererer
        blink = new SpellTalismanBlink("blink", ModFavors.endererer, ConfigSpells.blink.favorCost, SpellOptions.ALL_CAST, true, ConfigSpells.blink.distance);
        earthen_dive = new SpellTalismanEarthenDive("earthen_dive", ModFavors.endererer, ConfigSpells.earthenDive.favorCost, SpellOptions.USE_CAST);
        escape_plan = new SpellTalismanEscapePlan("escape_plan", ModFavors.endererer, ConfigSpells.escapePlan.favorCost, SpellOptions.ALL_CAST);
        invite_gem = new SpellTalismanInviteMarker("invite_gem", ModFavors.endererer, ConfigSpells.inviteGem.favorCost, SpellOptions.ALL_CAST, ModItems.invite_gem);
        invite_pebble = new SpellTalismanInviteMarker("invite_pebble", ModFavors.endererer, ConfigSpells.invitePebble.favorCost, SpellOptions.ALL_CAST, ModItems.invite_pebble);
        overblink = new SpellTalismanBlink("overblink", ModFavors.endererer, ConfigSpells.overblink.favorCost, SpellOptions.ALL_CAST, true, ConfigSpells.overblink.distance);
        overwarp = new SpellTalismanBlink("overwarp", ModFavors.endererer, ConfigSpells.overwarp.favorCost, SpellOptions.ALL_CAST, false, ConfigSpells.overwarp.distance);
        pearl_crumbs = new SpellTalismanPearlCrumbs("pearl_crumbs", ModFavors.endererer, ConfigSpells.pearlCrumbs.favorCost, SpellOptions.ALL_CAST);
        remote_chest = new SpellTalismanRemoteChest("remote_chest", ModFavors.endererer, ConfigSpells.remoteChest.favorCost, SpellOptions.USE_CAST, ModItems.storage_gem);
        surface_blink = new SpellTalismanSurfaceBlink("surface_blink", ModFavors.endererer, ConfigSpells.surfaceBlink.favorCost, SpellOptions.ALL_CAST_TRACE);
        surface_shift = new SpellTalismanSurfaceShift("surface_shift", ModFavors.endererer, ConfigSpells.surfaceShift.favorCost, SpellOptions.ALL_CAST);
        wall_slip = new SpellTalismanWallSlip("wall_slip", ModFavors.endererer, ConfigSpells.wallSlip.favorCost, SpellOptions.USE_CAST);
        warp = new SpellTalismanBlink("warp", ModFavors.endererer, ConfigSpells.warp.favorCost, SpellOptions.ALL_CAST, false, ConfigSpells.warp.distance);
        warp_gem = new SpellTalismanWarpMarker("warp_gem", ModFavors.endererer, ConfigSpells.warpGem.favorCost, SpellOptions.ALL_CAST, ModItems.warp_gem);
        warp_pebble = new SpellTalismanWarpMarker("warp_pebble", ModFavors.endererer, ConfigSpells.warpPebble.favorCost, SpellOptions.ALL_CAST, ModItems.warp_pebble);

        // loon
        follow = new SpellTalismanFollow("follow", ModFavors.loon, ConfigSpells.follow.favorCost, SpellOptions.RIGHT_CAST);
        night_eye = new SpellTalismanModPotionToggle("night_eye", ModFavors.loon, ConfigSpells.nightEye.favorCost, ModPotions.night_eye);
        spider_might = new SpellTalismanModPotion("spider_might", ModFavors.loon, ConfigSpells.spider_might.favorCost, ModPotions.spider_might, ConfigSpells.spider_might.duration);
        summon_creeper = new SpellTalismanSummonMinion<>("summon_creeper", ModFavors.loon, ConfigSpells.summonCreeper.favorCost, SpellOptions.USE_CAST, MinionCreeper.class);
        summon_husk = new SpellTalismanSummonMinion<>("summon_husk", ModFavors.loon, ConfigSpells.summonHusk.favorCost, SpellOptions.USE_CAST, MinionHusk.class);
        summon_skeleton = new SpellTalismanSummonMinion<>("summon_skeleton", ModFavors.loon, ConfigSpells.summonSkeleton.favorCost, SpellOptions.USE_CAST, MinionSkeleton.class);
        summon_stray = new SpellTalismanSummonMinion<>("summon_stray", ModFavors.loon, ConfigSpells.summonStray.favorCost, SpellOptions.USE_CAST, MinionStray.class);
        summon_zombie = new SpellTalismanSummonMinion<>("summon_zombie", ModFavors.loon, ConfigSpells.summonZombie.favorCost, SpellOptions.USE_CAST, MinionZombie.class);
        target = new SpellTalismanTarget("target", ModFavors.loon, 0, SpellOptions.ENTITY_CAST);

        // neblaze
        blazing_palm = new SpellTalismanBlazingPalm("blazing_palm", ModFavors.neblaze, ConfigSpells.blazingPalm.favorCost, SpellOptions.ALL_CAST);
        combustion = new SpellTalismanCombustion("combustion", ModFavors.neblaze, ConfigSpells.combustion.favorCost, SpellOptions.USE_CAST);
        distant_spark = new SpellTalismanIgnition("distant_spark", ModFavors.neblaze, ConfigSpells.distantSpark.favorCost, SpellOptions.ALL_CAST_TRACE);
        evil_eye = new SpellTalismanEvilEye("evil_eye", ModFavors.neblaze, ConfigSpells.evilEye.favorCost, SpellOptions.ENTITY_CAST);
        heat_wave = new SpellTalismanHeatWave("heat_wave", ModFavors.neblaze, ConfigSpells.heatWave.favorCost, SpellOptions.ALL_CAST);
        hellisphere = new SpellTalismanHellisphere("hellisphere", ModFavors.neblaze, ConfigSpells.hellisphere.favorCost, SpellOptions.USE_CAST);
        ignition = new SpellTalismanIgnition("ignition", ModFavors.neblaze, ConfigSpells.ignition.favorCost, SpellOptions.USE_CAST);
        infernal_touch = new SpellTalismanInfernalTouch("infernal_touch", ModFavors.neblaze, 1, SpellOptions.USE_CAST);
        molten_skin = new SpellTalismanMoltenSkin("molten_skin", ModFavors.neblaze, ConfigSpells.moltenSkin.favorCost, SpellOptions.ALL_CAST);
        nether_surge = new SpellTalismanNetherSurge("nether_surge", ModFavors.neblaze, ConfigSpells.netherSurge.favorCost, SpellOptions.USE_CAST);
        piercing_inferno = new SpellTalismanPiercingInferno("piercing_inferno", ModFavors.neblaze, ConfigSpells.piercingInferno.favorCost, SpellOptions.USE_CAST);
        searing_pulse = new SpellTalismanSearingPulse("searing_pulse", ModFavors.neblaze, ConfigSpells.searingPulse.favorCost, SpellOptions.USE_CAST);
        small_fireball_throw = new SpellTalismanSmallFireballThrow("small_fireball_throw", ModFavors.neblaze, ConfigSpells.smallFireballThrow.favorCost, SpellOptions.ALL_CAST);

        // redwind
        fall_negation = new SpellTalismanModPotionCharge("fall_negation", ModFavors.redwind, ConfigSpells.fallNegation.favorCost, ModPotions.fall_negation, ConfigSpells.fallNegation.charges);
        toadic_jump = new SpellTalismanModPotionToggle("toadic_jump", ModFavors.redwind, ConfigSpells.toadicJump.favorCost, ModPotions.toadic_jump);
        wild_sprint = new SpellTalismanModPotion("wild_sprint", ModFavors.redwind, ConfigSpells.wildSprint.favorCost, ModPotions.wild_charge, ConfigSpells.wildSprint.activationDelay);
        wind_step = new SpellTalismanWindStep("wind_step", ModFavors.redwind, ConfigSpells.windStep.favorCost, SpellOptions.ALL_CAST);
        clock = new SpellTalismanClock("clock", ModFavors.redwind, ConfigSpells.clock.favorCost, SpellOptions.ALL_CAST);

        // romol
        crushing_palm = new SpellTalismanModPotionToggle("crushing_palm", ModFavors.romol, ConfigSpells.crushingPalm.favorCost, ModPotions.crushing_palm);
        empower_axe = new SpellTalismanModPotion("empower_axe", ModFavors.romol, ConfigSpells.empowerAxe.favorCost, ModPotions.empower_axe, ConfigSpells.empowerAxe.duration);
        empower_pickaxe = new SpellTalismanModPotion("empower_pickaxe", ModFavors.romol, ConfigSpells.empowerPickaxe.favorCost, ModPotions.empower_pickaxe, ConfigSpells.empowerPickaxe.duration);
        fell_tree = new SpellTalismanFellTree("fell_tree", ModFavors.romol, ConfigSpells.fellTree.favorCost, SpellOptions.USE_CAST);
        green_cycle = new SpellTalismanGreenCycle("green_cycle", ModFavors.romol, ConfigSpells.greenCycle.favorCost, SpellOptions.ALL_CAST);
        ground_flow = new SpellTalismanModPotionToggle("ground_flow", ModFavors.romol, ConfigSpells.groundFlow.favorCost, ModPotions.ground_flow);
        harvest = new SpellTalismanHarvest("harvest", ModFavors.romol, ConfigSpells.harvest.favorCost, SpellOptions.ALL_CAST);
        miners_focus = new SpellTalismanMinersFocus("miners_focus", ModFavors.romol, ConfigSpells.minersFocus.favorCost, SpellOptions.ALL_CAST);
        stone_fever = new SpellTalismanModPotion("stone_fever", ModFavors.romol, ConfigSpells.stoneFever.favorCost, ModPotions.stone_fever, ConfigSpells.stoneFever.duration);
        stoneball_throw = new SpellTalismanStoneballThrow("stoneball_throw", ModFavors.romol, ConfigSpells.stoneballThrow.favorCost, SpellOptions.ALL_CAST);
        wooden_punch = new SpellTalismanModPotionToggle("wooden_punch", ModFavors.romol, ConfigSpells.woodenPunch.favorCost, ModPotions.wooden_punch);

        // squarefury
        butchering_strike = new SpellTalismanModPotionCharge("butchering_strike", ModFavors.squarefury, ConfigSpells.butcheringStrike.favorCost, ModPotions.butchering_strike, ConfigSpells.butcheringStrike.charges);
        consuming_fury = new SpellTalismanModPotion("consuming_fury", ModFavors.squarefury, ConfigSpells.consumingFury.favorCost, ModPotions.consuming_fury, ConfigSpells.consumingFury.duration);
        focused_fury = new SpellTalismanModPotion("focused_fury", ModFavors.squarefury, ConfigSpells.focusedFury.favorCost, ModPotions.focused_fury, ConfigSpells.focusedFury.duration);
        grudge = new SpellTalismanGrudge("grudge", ModFavors.squarefury, ConfigSpells.grudge.favorCost, SpellOptions.ALL_CAST);
        mist_blade = new SpellTalismanModPotionToggle("mist_blade", ModFavors.squarefury, ConfigSpells.mistBlade.favorCost, ModPotions.mist_blade);

        // timber
        blade_of_grass = new SpellTalismanBladeOfGrass("blade_of_grass", ModFavors.timber, ConfigSpells.bladeOfGrass.favorCost, SpellOptions.ALL_CAST);
        blood_of_grass = new SpellTalismanBloodOfGrass("blood_of_grass", ModFavors.timber, ConfigSpells.bloodOfGrass.favorCost, SpellOptions.ALL_CAST);
        bonemeal = new SpellTalismanBonemeal("bonemeal", ModFavors.timber, ConfigSpells.bonemeal.favorCost, SpellOptions.USE_CAST);
        starvation = new SpellTalismanModPotion("starvation", ModFavors.timber, ConfigSpells.starvation.favorCost, ModPotions.starvation, ConfigSpells.starvation.duration);
    }

    public static void init() {
    }
}