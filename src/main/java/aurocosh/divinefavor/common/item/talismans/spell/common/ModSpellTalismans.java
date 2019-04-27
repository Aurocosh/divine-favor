package aurocosh.divinefavor.common.item.talismans.spell.common;

import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.entity.minions.*;
import aurocosh.divinefavor.common.item.common.ModItems;
import aurocosh.divinefavor.common.item.talismans.spell.*;
import aurocosh.divinefavor.common.item.talismans.spell.base.*;
import aurocosh.divinefavor.common.item.talismans.spell.SpellTalismanReplacmentBubble;
import aurocosh.divinefavor.common.item.talismans.spell.sense.SenseBlockPredicate;
import aurocosh.divinefavor.common.item.talismans.spell.sense.SpellTalismanSenseFloodFill;
import aurocosh.divinefavor.common.item.talismans.spell.sense.SpellTalismanSenseInSphereHeld;
import aurocosh.divinefavor.common.item.talismans.spell.sense.SpellTalismanSenseInSphereType;
import aurocosh.divinefavor.common.potions.common.ModPotions;
import aurocosh.divinefavor.common.spirit.ModSpirits;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilPredicate;
import net.minecraft.init.Blocks;

import javax.vecmath.Color3f;

public final class ModSpellTalismans {
    public static ItemSpellTalisman armor_of_pacifist;
    public static ItemSpellTalisman arrow_deflection;
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
    public static ItemSpellTalisman ethereal_flash;
    public static ItemSpellTalisman ethereal_light;
    public static ItemSpellTalisman evil_eye;
    public static ItemSpellTalisman extreme_buoyancy;
    public static ItemSpellTalisman fall_negation;
    public static ItemSpellTalisman fell_tree;
    public static ItemSpellTalisman fins;
    public static ItemSpellTalisman flood;
    public static ItemSpellTalisman focused_fury;
    public static ItemSpellTalisman follow;
    public static ItemSpellTalisman gills;
    public static ItemSpellTalisman green_cycle;
    public static ItemSpellTalisman ground_flow;
    public static ItemSpellTalisman grudge;
    public static ItemSpellTalisman harvest;
    public static ItemSpellTalisman heat_wave;
    public static ItemSpellTalisman hellisphere;
    public static ItemSpellTalisman hovering;
    public static ItemSpellTalisman ice_bubble;
    public static ItemSpellTalisman ice_carving;
    public static ItemSpellTalisman ice_surface;
    public static ItemSpellTalisman ignition;
    public static ItemSpellTalisman infernal_touch;
    public static ItemSpellTalisman instant_dive;
    public static ItemSpellTalisman invite_gem;
    public static ItemSpellTalisman invite_pebble;
    public static ItemSpellTalisman lake_thawing;
    public static ItemSpellTalisman miners_focus;
    public static ItemSpellTalisman mist_blade;
    public static ItemSpellTalisman molten_skin;
    public static ItemSpellTalisman nether_surge;
    public static ItemSpellTalisman night_eye;
    public static ItemSpellTalisman obsidian_bubble;
    public static ItemSpellTalisman obsidian_road;
    public static ItemSpellTalisman overblink;
    public static ItemSpellTalisman overwarp;
    public static ItemSpellTalisman pearl_crumbs;
    public static ItemSpellTalisman piercing_inferno;
    public static ItemSpellTalisman ping;
    public static ItemSpellTalisman place_block;
    public static ItemSpellTalisman prismatic_eyes;
    public static ItemSpellTalisman red_pulse;
    public static ItemSpellTalisman red_signal;
    public static ItemSpellTalisman remote_chest;
    public static ItemSpellTalisman searing_pulse;
    public static ItemSpellTalisman sense_block_approximate;
    public static ItemSpellTalisman sense_block_close;
    public static ItemSpellTalisman sense_block_precise;
    public static ItemSpellTalisman sense_block_vague;
    public static ItemSpellTalisman sense_cave;
    public static ItemSpellTalisman sense_lava;
    public static ItemSpellTalisman sense_liquid;
    public static ItemSpellTalisman sense_ore_approximate;
    public static ItemSpellTalisman sense_ore_close;
    public static ItemSpellTalisman sense_ore_precise;
    public static ItemSpellTalisman sense_ore_vague;
    public static ItemSpellTalisman sense_water;
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
        arrow_throw_talisman = new SpellTalismanArrowThrow("arrow_throw", ModSpirits.arbow, ConfigSpells.arrowThrow.favorCost, SpellOptions.ALL_CAST);

        // blizzrabi
        armor_of_pacifist = new SpellTalismanModPotionToggle("armor_of_pacifist", ModSpirits.blizrabi, ConfigSpells.armorOfPacifist.favorCost, ModPotions.armor_of_pacifist);
        crystalline_road = new SpellTalismanModPotion("crystalline_road", ModSpirits.blizrabi, ConfigSpells.crystallineRoad.favorCost, ModPotions.crystalline_road, ConfigSpells.crystallineRoad.duration);
        extreme_buoyancy = new SpellTalismanModPotionToggle("extreme_buoyancy", ModSpirits.blizrabi, ConfigSpells.extremeBuoyancy.favorCost, ModPotions.extreme_buoyancy);
        fins = new SpellTalismanModPotion("fins", ModSpirits.blizrabi, ConfigSpells.fins.favorCost, ModPotions.fins, ConfigSpells.fins.duration);
        gills = new SpellTalismanModPotionToggle("gills", ModSpirits.blizrabi, ConfigSpells.gills.favorCost, ModPotions.gills);
        ice_bubble = new SpellTalismanReplacmentBubble("ice_bubble", ModSpirits.blizrabi, ConfigSpells.iceBubble, Blocks.AIR, Blocks.ICE, UtilPredicate.or(UtilBlock::isWater, UtilBlock::isIce));
        instant_dive = new SpellTalismanModPotionToggle("instant_dive", ModSpirits.blizrabi, ConfigSpells.instandDive.favorCost, ModPotions.instant_dive);
        obsidian_road = new SpellTalismanModPotion("obsidian_road", ModSpirits.blizrabi, ConfigSpells.obsidianRoad.favorCost, ModPotions.obsidian_road, ConfigSpells.obsidianRoad.duration);
        snowball_throw = new SpellTalismanSnowballThrow("snowball_throw", ModSpirits.blizrabi, ConfigSpells.snowballThrow.favorCost, SpellOptions.ALL_CAST);
        vitalize = new SpellTalismanVitalize("vitalize", ModSpirits.blizrabi, ConfigSpells.vitalize.favorCost, SpellOptions.ALL_CAST);
        winter_breath = new SpellTalismanWinterBreath("winter_breath", ModSpirits.blizrabi, ConfigSpells.winterBreath.favorCost, SpellOptions.ALL_CAST);
        flood = new SpellTalismanFlood("flood", ModSpirits.blizrabi, ConfigSpells.flood.favorCost, SpellOptions.ALL_CAST);
        ice_surface = new SpellTalismanIceSurface("ice_surface", ModSpirits.blizrabi, ConfigSpells.iceSurface.favorCost, SpellOptions.ALL_CAST);
        ice_carving = new SpellTalismanIceCarving("ice_carving", ModSpirits.blizrabi, ConfigSpells.iceCarving.favorCost, SpellOptions.USE_CAST);
        lake_thawing = new SpellTalismanLakeThawing("lake_thawing", ModSpirits.blizrabi, ConfigSpells.lakeThawing.favorCost, SpellOptions.ALL_CAST);

        // endererer
        blink = new SpellTalismanBlink("blink", ModSpirits.endererer, ConfigSpells.blink.favorCost, SpellOptions.ALL_CAST, true, ConfigSpells.blink.distance);
        earthen_dive = new SpellTalismanEarthenDive("earthen_dive", ModSpirits.endererer, ConfigSpells.earthenDive.favorCost, SpellOptions.USE_CAST);
        escape_plan = new SpellTalismanEscapePlan("escape_plan", ModSpirits.endererer, ConfigSpells.escapePlan.favorCost, SpellOptions.ALL_CAST);
        invite_gem = new SpellTalismanInviteMarker("invite_gem", ModSpirits.endererer, ConfigSpells.inviteGem.favorCost, SpellOptions.ALL_CAST, ModItems.invite_gem);
        invite_pebble = new SpellTalismanInviteMarker("invite_pebble", ModSpirits.endererer, ConfigSpells.invitePebble.favorCost, SpellOptions.ALL_CAST, ModItems.invite_pebble);
        overblink = new SpellTalismanBlink("overblink", ModSpirits.endererer, ConfigSpells.overblink.favorCost, SpellOptions.ALL_CAST, true, ConfigSpells.overblink.distance);
        overwarp = new SpellTalismanBlink("overwarp", ModSpirits.endererer, ConfigSpells.overwarp.favorCost, SpellOptions.ALL_CAST, false, ConfigSpells.overwarp.distance);
        pearl_crumbs = new SpellTalismanPearlCrumbs("pearl_crumbs", ModSpirits.endererer, ConfigSpells.pearlCrumbs.favorCost, SpellOptions.ALL_CAST);
        remote_chest = new SpellTalismanRemoteChest("remote_chest", ModSpirits.endererer, ConfigSpells.remoteChest.favorCost, SpellOptions.USE_CAST, ModItems.storage_gem);
        surface_blink = new SpellTalismanSurfaceBlink("surface_blink", ModSpirits.endererer, ConfigSpells.surfaceBlink.favorCost, SpellOptions.ALL_CAST_TRACE);
        surface_shift = new SpellTalismanSurfaceShift("surface_shift", ModSpirits.endererer, ConfigSpells.surfaceShift.favorCost, SpellOptions.ALL_CAST);
        wall_slip = new SpellTalismanWallSlip("wall_slip", ModSpirits.endererer, ConfigSpells.wallSlip.favorCost, SpellOptions.USE_CAST);
        warp = new SpellTalismanBlink("warp", ModSpirits.endererer, ConfigSpells.warp.favorCost, SpellOptions.ALL_CAST, false, ConfigSpells.warp.distance);
        warp_gem = new SpellTalismanWarpMarker("warp_gem", ModSpirits.endererer, ConfigSpells.warpGem.favorCost, SpellOptions.ALL_CAST, ModItems.warp_gem);
        warp_pebble = new SpellTalismanWarpMarker("warp_pebble", ModSpirits.endererer, ConfigSpells.warpPebble.favorCost, SpellOptions.ALL_CAST, ModItems.warp_pebble);

        // loon
        follow = new SpellTalismanFollow("follow", ModSpirits.loon, ConfigSpells.follow.favorCost, SpellOptions.RIGHT_CAST);
        night_eye = new SpellTalismanModPotionToggle("night_eye", ModSpirits.loon, ConfigSpells.nightEye.favorCost, ModPotions.night_eye);
        spider_might = new SpellTalismanModPotionToggle("spider_might", ModSpirits.loon, ConfigSpells.spider_might.favorCost, ModPotions.spider_might);
        summon_creeper = new SpellTalismanSummonMinion<>("summon_creeper", ModSpirits.loon, ConfigSpells.summonCreeper.favorCost, SpellOptions.USE_CAST, EntityMinionCreeper.class);
        summon_husk = new SpellTalismanSummonMinion<>("summon_husk", ModSpirits.loon, ConfigSpells.summonHusk.favorCost, SpellOptions.USE_CAST, EntityMinionHusk.class);
        summon_skeleton = new SpellTalismanSummonMinion<>("summon_skeleton", ModSpirits.loon, ConfigSpells.summonSkeleton.favorCost, SpellOptions.USE_CAST, EntityMinionSkeleton.class);
        summon_stray = new SpellTalismanSummonMinion<>("summon_stray", ModSpirits.loon, ConfigSpells.summonStray.favorCost, SpellOptions.USE_CAST, EntityMinionStray.class);
        summon_zombie = new SpellTalismanSummonMinion<>("summon_zombie", ModSpirits.loon, ConfigSpells.summonZombie.favorCost, SpellOptions.USE_CAST, EntityMinionZombie.class);
        target = new SpellTalismanTarget("target", ModSpirits.loon, 0, SpellOptions.ENTITY_CAST);

        // neblaze
        blazing_palm = new SpellTalismanBlazingPalm("blazing_palm", ModSpirits.neblaze, ConfigSpells.blazingPalm.favorCost, SpellOptions.ALL_CAST);
        combustion = new SpellTalismanCombustion("combustion", ModSpirits.neblaze, ConfigSpells.combustion.favorCost, SpellOptions.USE_CAST);
        distant_spark = new SpellTalismanIgnition("distant_spark", ModSpirits.neblaze, ConfigSpells.distantSpark.favorCost, SpellOptions.ALL_CAST_TRACE);
        evil_eye = new SpellTalismanEvilEye("evil_eye", ModSpirits.neblaze, ConfigSpells.evilEye.favorCost, SpellOptions.ENTITY_CAST);
        heat_wave = new SpellTalismanHeatWave("heat_wave", ModSpirits.neblaze, ConfigSpells.heatWave.favorCost, SpellOptions.ALL_CAST);
        hellisphere = new SpellTalismanHellisphere("hellisphere", ModSpirits.neblaze, ConfigSpells.hellisphere.favorCost, SpellOptions.USE_CAST);
        ignition = new SpellTalismanIgnition("ignition", ModSpirits.neblaze, ConfigSpells.ignition.favorCost, SpellOptions.USE_CAST);
        infernal_touch = new SpellTalismanInfernalTouch("infernal_touch", ModSpirits.neblaze, 1, SpellOptions.USE_CAST);
        molten_skin = new SpellTalismanMoltenSkin("molten_skin", ModSpirits.neblaze, ConfigSpells.moltenSkin.favorCost, SpellOptions.ALL_CAST);
        nether_surge = new SpellTalismanNetherSurge("nether_surge", ModSpirits.neblaze, ConfigSpells.netherSurge.favorCost, SpellOptions.USE_CAST);
        piercing_inferno = new SpellTalismanPiercingInferno("piercing_inferno", ModSpirits.neblaze, ConfigSpells.piercingInferno.favorCost, SpellOptions.USE_CAST);
        searing_pulse = new SpellTalismanSearingPulse("searing_pulse", ModSpirits.neblaze, ConfigSpells.searingPulse.favorCost, SpellOptions.USE_CAST);
        small_fireball_throw = new SpellTalismanSmallFireballThrow("small_fireball_throw", ModSpirits.neblaze, ConfigSpells.smallFireballThrow.favorCost, SpellOptions.ALL_CAST);
        obsidian_bubble = new SpellTalismanReplacmentBubble("obsidian_bubble", ModSpirits.blizrabi, ConfigSpells.obsidianBubble, Blocks.AIR, Blocks.OBSIDIAN, UtilPredicate.or(UtilBlock::isLava, block -> block == Blocks.OBSIDIAN));

        // redwind
        arrow_deflection = new SpellTalismanModPotion("arrow_deflection", ModSpirits.redwind, ConfigSpells.arrowDeflection.favorCost, ModPotions.arrow_deflection, ConfigSpells.arrowDeflection.duration);
        clock = new SpellTalismanClock("clock", ModSpirits.redwind, ConfigSpells.clock.favorCost, SpellOptions.ALL_CAST);
        ethereal_flash = new SpellTalismanAirReplace("ethereal_flash", ModSpirits.redwind, ConfigSpells.etherealFlash.favorCost, SpellOptions.ALL_CAST_TRACE, () -> ModBlocks.ethereal_flash);
        ethereal_light = new SpellTalismanAirReplace("ethereal_light", ModSpirits.redwind, ConfigSpells.etherealLight.favorCost, SpellOptions.ALL_CAST_TRACE, () -> ModBlocks.ethereal_light);
        fall_negation = new SpellTalismanModPotionCharge("fall_negation", ModSpirits.redwind, ConfigSpells.fallNegation.favorCost, ModPotions.fall_negation, ConfigSpells.fallNegation.charges);
        ground_flow = new SpellTalismanModPotionToggle("ground_flow", ModSpirits.redwind, ConfigSpells.groundFlow.favorCost, ModPotions.ground_flow);
        hovering = new SpellTalismanModPotionToggle("hovering", ModSpirits.redwind, ConfigSpells.groundFlow.favorCost, ModPotions.hovering);
        ping = new SpellTalismanPing("ping", ModSpirits.redwind, ConfigSpells.ping.favorCost, SpellOptions.TRACE_ONLY_CAST);
        prismatic_eyes = new SpellTalismanModPotionToggle("prismatic_eyes", ModSpirits.redwind, ConfigSpells.prismaticEyes.favorCost, ModPotions.prismatic_eyes);
        red_pulse = new SpellTalismanAirReplace("red_pulse", ModSpirits.redwind, ConfigSpells.redPulse.favorCost, SpellOptions.ALL_CAST_TRACE, () -> ModBlocks.red_pulse);
        red_signal = new SpellTalismanAirReplace("red_signal", ModSpirits.redwind, ConfigSpells.redSignal.favorCost, SpellOptions.ALL_CAST_TRACE, () -> ModBlocks.red_signal);
        toadic_jump = new SpellTalismanModPotionToggle("toadic_jump", ModSpirits.redwind, ConfigSpells.toadicJump.favorCost, ModPotions.toadic_jump);
        wild_sprint = new SpellTalismanModPotion("wild_sprint", ModSpirits.redwind, ConfigSpells.wildSprint.favorCost, ModPotions.wild_charge, ConfigSpells.wildSprint.activationDelay);
        wind_step = new SpellTalismanWindStep("wind_step", ModSpirits.redwind, ConfigSpells.windStep.favorCost, SpellOptions.ALL_CAST);

        // romol
        crushing_palm = new SpellTalismanModPotionToggle("crushing_palm", ModSpirits.romol, ConfigSpells.crushingPalm.favorCost, ModPotions.crushing_palm);
        empower_axe = new SpellTalismanModPotion("empower_axe", ModSpirits.romol, ConfigSpells.empowerAxe.favorCost, ModPotions.empower_axe, ConfigSpells.empowerAxe.duration);
        empower_pickaxe = new SpellTalismanModPotion("empower_pickaxe", ModSpirits.romol, ConfigSpells.empowerPickaxe.favorCost, ModPotions.empower_pickaxe, ConfigSpells.empowerPickaxe.duration);
        fell_tree = new SpellTalismanFellTree("fell_tree", ModSpirits.romol, ConfigSpells.fellTree.favorCost, SpellOptions.USE_CAST);
        green_cycle = new SpellTalismanGreenCycle("green_cycle", ModSpirits.romol, ConfigSpells.greenCycle.favorCost, SpellOptions.ALL_CAST);
        harvest = new SpellTalismanHarvest("harvest", ModSpirits.romol, ConfigSpells.harvest.favorCost, SpellOptions.ALL_CAST);
        miners_focus = new SpellTalismanMinersFocus("miners_focus", ModSpirits.romol, ConfigSpells.minersFocus.favorCost, SpellOptions.ALL_CAST);
        place_block = new SpellTalismanPlaceBlock("place_block", ModSpirits.romol, ConfigSpells.woodenPunch.favorCost, SpellOptions.ALL_CAST_TRACE);
        sense_block_approximate = new SpellTalismanSenseInSphereHeld("sense_block_approximate", ModSpirits.romol, SpellOptions.RIGHT_CAST, new Color3f(0.7f, 0.7f, 0), ConfigSpells.senseBlockApproximate);
        sense_block_close = new SpellTalismanSenseInSphereHeld("sense_block_close", ModSpirits.romol, SpellOptions.RIGHT_CAST, new Color3f(0, 0, 0.7f), ConfigSpells.senseBlockClose);
        sense_block_precise = new SpellTalismanSenseInSphereHeld("sense_block_precise", ModSpirits.romol, SpellOptions.RIGHT_CAST, new Color3f(0.7f, 0, 0), ConfigSpells.senseBlockPrecise);
        sense_block_vague = new SpellTalismanSenseInSphereHeld("sense_block_vague", ModSpirits.romol, SpellOptions.RIGHT_CAST, new Color3f(0, 0.7f, 0), ConfigSpells.senseBlockVague);
        sense_cave = new SpellTalismanSenseFloodFill("sense_cave", ModSpirits.romol, SpellOptions.USE_CAST, new Color3f(0, 0.2f, 0.9f), ConfigSpells.senseWater, SenseBlockPredicate.AIR, 500, 32);
        sense_lava = new SpellTalismanSenseInSphereType("sense_lava", ModSpirits.romol, SpellOptions.RIGHT_CAST, new Color3f(0.9f, 0.2f, 0f), ConfigSpells.senseLava, SenseBlockPredicate.LAVA);
        sense_liquid = new SpellTalismanSenseInSphereType("sense_liquid", ModSpirits.romol, SpellOptions.RIGHT_CAST, new Color3f(0.7f, 0.2f, 0.7f), ConfigSpells.senseLiquid, SenseBlockPredicate.LIQUID);
        sense_ore_approximate = new SpellTalismanSenseInSphereType("sense_ore_approximate", ModSpirits.romol, SpellOptions.RIGHT_CAST, new Color3f(0.7f, 0.7f, 0), ConfigSpells.senseOreApproximate, SenseBlockPredicate.ORE);
        sense_ore_close = new SpellTalismanSenseInSphereType("sense_ore_close", ModSpirits.romol, SpellOptions.RIGHT_CAST, new Color3f(0, 0, 0.7f), ConfigSpells.senseOreClose, SenseBlockPredicate.ORE);
        sense_ore_precise = new SpellTalismanSenseInSphereType("sense_ore_precise", ModSpirits.romol, SpellOptions.RIGHT_CAST, new Color3f(0.7f, 0, 0), ConfigSpells.senseOrePrecise, SenseBlockPredicate.ORE);
        sense_ore_vague = new SpellTalismanSenseInSphereType("sense_ore_vague", ModSpirits.romol, SpellOptions.RIGHT_CAST, new Color3f(0, 0.7f, 0), ConfigSpells.senseOreVague, SenseBlockPredicate.ORE);
        sense_water = new SpellTalismanSenseInSphereType("sense_water", ModSpirits.romol, SpellOptions.RIGHT_CAST, new Color3f(0, 0.2f, 0.9f), ConfigSpells.senseWater, SenseBlockPredicate.WATER);
        stone_fever = new SpellTalismanModPotion("stone_fever", ModSpirits.romol, ConfigSpells.stoneFever.favorCost, ModPotions.stone_fever, ConfigSpells.stoneFever.duration);
        stoneball_throw = new SpellTalismanStoneballThrow("stoneball_throw", ModSpirits.romol, ConfigSpells.stoneballThrow.favorCost, SpellOptions.ALL_CAST);
        wooden_punch = new SpellTalismanModPotionToggle("wooden_punch", ModSpirits.romol, ConfigSpells.woodenPunch.favorCost, ModPotions.wooden_punch);


        // squarefury
        butchering_strike = new SpellTalismanModPotionCharge("butchering_strike", ModSpirits.squarefury, ConfigSpells.butcheringStrike.favorCost, ModPotions.butchering_strike, ConfigSpells.butcheringStrike.charges);
        consuming_fury = new SpellTalismanModPotion("consuming_fury", ModSpirits.squarefury, ConfigSpells.consumingFury.favorCost, ModPotions.consuming_fury, ConfigSpells.consumingFury.duration);
        focused_fury = new SpellTalismanModPotion("focused_fury", ModSpirits.squarefury, ConfigSpells.focusedFury.favorCost, ModPotions.focused_fury, ConfigSpells.focusedFury.duration);
        grudge = new SpellTalismanGrudge("grudge", ModSpirits.squarefury, ConfigSpells.grudge.favorCost, SpellOptions.ALL_CAST);
        mist_blade = new SpellTalismanModPotionToggle("mist_blade", ModSpirits.squarefury, ConfigSpells.mistBlade.favorCost, ModPotions.mist_blade);

        // timber
        blade_of_grass = new SpellTalismanBladeOfGrass("blade_of_grass", ModSpirits.timber, ConfigSpells.bladeOfGrass.favorCost, SpellOptions.ALL_CAST);
        blood_of_grass = new SpellTalismanBloodOfGrass("blood_of_grass", ModSpirits.timber, ConfigSpells.bloodOfGrass.favorCost, SpellOptions.ALL_CAST);
        bonemeal = new SpellTalismanBonemeal("bonemeal", ModSpirits.timber, ConfigSpells.bonemeal.favorCost, SpellOptions.USE_CAST);
        starvation = new SpellTalismanModPotion("starvation", ModSpirits.timber, ConfigSpells.starvation.favorCost, ModPotions.starvation, ConfigSpells.starvation.duration);
    }

    public static void init() {
    }
}