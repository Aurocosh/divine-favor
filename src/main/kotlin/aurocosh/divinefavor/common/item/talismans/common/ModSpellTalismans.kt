package aurocosh.divinefavor.common.item.talismans.common

import aurocosh.divinefavor.common.block.common.ModBlocks
import aurocosh.divinefavor.common.config.common.ConfigSpells
import aurocosh.divinefavor.common.entity.minions.*
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.talismans.spell.*
import aurocosh.divinefavor.common.item.talismans.spell.base.*
import aurocosh.divinefavor.common.item.talismans.spell.sense.*
import aurocosh.divinefavor.common.lib.extensions.isIce
import aurocosh.divinefavor.common.lib.extensions.isLava
import aurocosh.divinefavor.common.lib.extensions.isWater
import aurocosh.divinefavor.common.potions.common.ModPotions
import aurocosh.divinefavor.common.spirit.ModSpirits
import aurocosh.divinefavor.common.util.UtilPredicate
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import javax.vecmath.Color3f

object ModSpellTalismans {
    lateinit var armor_of_pacifist: ItemSpellTalisman
    lateinit var arrow_deflection: ItemSpellTalisman
    lateinit var arrow_throw_talisman: ItemSpellTalisman
    lateinit var blade_of_grass: ItemSpellTalisman
    lateinit var blazing_palm: ItemSpellTalisman
    lateinit var blink: ItemSpellTalisman
    lateinit var blood_of_grass: ItemSpellTalisman
    lateinit var bonemeal: ItemSpellTalisman
    lateinit var clock: ItemSpellTalisman
    lateinit var combustion: ItemSpellTalisman
    lateinit var consuming_fury: ItemSpellTalisman
    lateinit var crushing_palm: ItemSpellTalisman
    lateinit var crystalline_road: ItemSpellTalisman
    lateinit var distant_spark: ItemSpellTalisman
    lateinit var earthen_dive: ItemSpellTalisman
    lateinit var empower_axe: ItemSpellTalisman
    lateinit var empower_pickaxe: ItemSpellTalisman
    lateinit var escape_plan: ItemSpellTalisman
    lateinit var ethereal_flash: ItemSpellTalisman
    lateinit var ethereal_light: ItemSpellTalisman
    lateinit var evil_eye: ItemSpellTalisman
    lateinit var extreme_buoyancy: ItemSpellTalisman
    lateinit var fall_negation: ItemSpellTalisman
    lateinit var fell_tree: ItemSpellTalisman
    lateinit var fins: ItemSpellTalisman
    lateinit var flood: ItemSpellTalisman
    lateinit var focused_fury: ItemSpellTalisman
    lateinit var follow: ItemSpellTalisman
    lateinit var frost_wave: ItemSpellTalisman
    lateinit var gills: ItemSpellTalisman
    lateinit var green_cycle: ItemSpellTalisman
    lateinit var ground_flow: ItemSpellTalisman
    lateinit var grudge: ItemSpellTalisman
    lateinit var harvest: ItemSpellTalisman
    lateinit var heat_wave: ItemSpellTalisman
    lateinit var hellisphere: ItemSpellTalisman
    lateinit var hovering: ItemSpellTalisman
    lateinit var ice_bubble: ItemSpellTalisman
    lateinit var ice_carving: ItemSpellTalisman
    lateinit var ice_surface: ItemSpellTalisman
    lateinit var ignition: ItemSpellTalisman
    lateinit var infernal_touch: ItemSpellTalisman
    lateinit var instant_dive: ItemSpellTalisman
    lateinit var invite_gem: ItemSpellTalisman
    lateinit var invite_pebble: ItemSpellTalisman
    lateinit var lake_thawing: ItemSpellTalisman
    lateinit var miners_focus: ItemSpellTalisman
    lateinit var mist_blade: ItemSpellTalisman
    lateinit var molten_skin: ItemSpellTalisman
    lateinit var nether_surge: ItemSpellTalisman
    lateinit var night_eye: ItemSpellTalisman
    lateinit var obsidian_bubble: ItemSpellTalisman
    lateinit var obsidian_carving: ItemSpellTalisman
    lateinit var obsidian_road: ItemSpellTalisman
    lateinit var overblink: ItemSpellTalisman
    lateinit var overwarp: ItemSpellTalisman
    lateinit var pearl_crumbs: ItemSpellTalisman
    lateinit var piercing_inferno: ItemSpellTalisman
    lateinit var ping: ItemSpellTalisman
    lateinit var place_block: ItemSpellTalisman
    lateinit var place_torch: ItemSpellTalisman
    lateinit var prismatic_eyes: ItemSpellTalisman
    lateinit var red_pulse: ItemSpellTalisman
    lateinit var red_signal: ItemSpellTalisman
    lateinit var remote_chest: ItemSpellTalisman
    lateinit var searing_pulse: ItemSpellTalisman
    lateinit var sense_block_approximate: ItemSpellTalisman
    lateinit var sense_block_close: ItemSpellTalisman
    lateinit var sense_block_precise: ItemSpellTalisman
    lateinit var sense_block_vague: ItemSpellTalisman
    lateinit var sense_cave: ItemSpellTalisman
    lateinit var sense_entity_all: ItemSpellTalisman
    lateinit var sense_entity_hostile: ItemSpellTalisman
    lateinit var sense_entity_passive: ItemSpellTalisman
    lateinit var sense_entity_player: ItemSpellTalisman
    lateinit var sense_lava: ItemSpellTalisman
    lateinit var sense_liquid: ItemSpellTalisman
    lateinit var sense_ore_approximate: ItemSpellTalisman
    lateinit var sense_ore_close: ItemSpellTalisman
    lateinit var sense_ore_precise: ItemSpellTalisman
    lateinit var sense_ore_vague: ItemSpellTalisman
    lateinit var sense_water: ItemSpellTalisman
    lateinit var small_fireball_throw: ItemSpellTalisman
    lateinit var snowball_throw: ItemSpellTalisman
    lateinit var spider_might: ItemSpellTalisman
    lateinit var starvation: ItemSpellTalisman
    lateinit var stone_fever: ItemSpellTalisman
    lateinit var stoneball_throw: ItemSpellTalisman
    lateinit var summon_creeper: ItemSpellTalisman
    lateinit var summon_husk: ItemSpellTalisman
    lateinit var summon_skeleton: ItemSpellTalisman
    lateinit var summon_stray: ItemSpellTalisman
    lateinit var summon_zombie: ItemSpellTalisman
    lateinit var surface_blink: ItemSpellTalisman
    lateinit var surface_shift: ItemSpellTalisman
    lateinit var target: ItemSpellTalisman
    lateinit var toadic_jump: ItemSpellTalisman
    lateinit var vitalize: ItemSpellTalisman
    lateinit var wall_slip: ItemSpellTalisman
    lateinit var warp: ItemSpellTalisman
    lateinit var warp_gem: ItemSpellTalisman
    lateinit var warp_pebble: ItemSpellTalisman
    lateinit var wild_sprint: ItemSpellTalisman
    lateinit var wind_step: ItemSpellTalisman
    lateinit var winter_breath: ItemSpellTalisman
    lateinit var wooden_punch: ItemSpellTalisman

    // New fields

    fun preInit() {
        // arbow
        arrow_throw_talisman = SpellTalismanArrowThrow("arrow_throw", ModSpirits.arbow, ConfigSpells.arrowThrow.favorCost, SpellOptions.ALL_CAST)

        // blizzrabi
        armor_of_pacifist = SpellTalismanModPotionToggle("armor_of_pacifist", ModSpirits.blizrabi, ConfigSpells.armorOfPacifist.favorCost, ModPotions.armor_of_pacifist)
        crystalline_road = SpellTalismanModPotion("crystalline_road", ModSpirits.blizrabi, ConfigSpells.crystallineRoad.favorCost, ModPotions.crystalline_road, ConfigSpells.crystallineRoad.duration)
        extreme_buoyancy = SpellTalismanModPotionToggle("extreme_buoyancy", ModSpirits.blizrabi, ConfigSpells.extremeBuoyancy.favorCost, ModPotions.extreme_buoyancy)
        fins = SpellTalismanModPotion("fins", ModSpirits.blizrabi, ConfigSpells.fins.favorCost, ModPotions.fins, ConfigSpells.fins.duration)
        flood = SpellTalismanFlood("flood", ModSpirits.blizrabi, ConfigSpells.flood.favorCost, SpellOptions.ALL_CAST)
        frost_wave = SpellTalismanFrostWave("frost_wave", ModSpirits.blizrabi, ConfigSpells.frostWave.favorCost, SpellOptions.ALL_CAST)
        gills = SpellTalismanModPotionToggle("gills", ModSpirits.blizrabi, ConfigSpells.gills.favorCost, ModPotions.gills)
        ice_bubble = SpellTalismanReplacmentBubble("ice_bubble", ModSpirits.blizrabi, ConfigSpells.iceBubble, Blocks.AIR, Blocks.ICE, UtilPredicate.or(Block::isWater, Block::isIce))
        ice_carving = SpellTalismanCarving("ice_carving", ModSpirits.blizrabi, ConfigSpells.iceCarving.favorCost, SpellOptions.USE_CAST, Blocks.ICE, Block::isWater)
        ice_surface = SpellTalismanIceSurface("ice_surface", ModSpirits.blizrabi, ConfigSpells.iceSurface.favorCost, SpellOptions.ALL_CAST)
        instant_dive = SpellTalismanModPotionToggle("instant_dive", ModSpirits.blizrabi, ConfigSpells.instandDive.favorCost, ModPotions.instant_dive)
        lake_thawing = SpellTalismanLakeThawing("lake_thawing", ModSpirits.blizrabi, ConfigSpells.lakeThawing.favorCost, SpellOptions.ALL_CAST)
        obsidian_road = SpellTalismanModPotion("obsidian_road", ModSpirits.blizrabi, ConfigSpells.obsidianRoad.favorCost, ModPotions.obsidian_road, ConfigSpells.obsidianRoad.duration)
        snowball_throw = SpellTalismanSnowballThrow("snowball_throw", ModSpirits.blizrabi, ConfigSpells.snowballThrow.favorCost, SpellOptions.ALL_CAST)
        vitalize = SpellTalismanVitalize("vitalize", ModSpirits.blizrabi, ConfigSpells.vitalize.favorCost, SpellOptions.ALL_CAST)
        winter_breath = SpellTalismanWinterBreath("winter_breath", ModSpirits.blizrabi, ConfigSpells.winterBreath.favorCost, SpellOptions.ALL_CAST)

        // endererer
        blink = SpellTalismanBlink("blink", ModSpirits.endererer, ConfigSpells.blink.favorCost, SpellOptions.ALL_CAST, true, ConfigSpells.blink.distance.toDouble())
        earthen_dive = SpellTalismanEarthenDive("earthen_dive", ModSpirits.endererer, ConfigSpells.earthenDive.favorCost, SpellOptions.USE_CAST)
        escape_plan = SpellTalismanEscapePlan("escape_plan", ModSpirits.endererer, ConfigSpells.escapePlan.favorCost, SpellOptions.ALL_CAST)
        invite_gem = SpellTalismanInviteMarker("invite_gem", ModSpirits.endererer, ConfigSpells.inviteGem.favorCost, SpellOptions.ALL_CAST, ModItems.invite_gem)
        invite_pebble = SpellTalismanInviteMarker("invite_pebble", ModSpirits.endererer, ConfigSpells.invitePebble.favorCost, SpellOptions.ALL_CAST, ModItems.invite_pebble)
        overblink = SpellTalismanBlink("overblink", ModSpirits.endererer, ConfigSpells.overblink.favorCost, SpellOptions.ALL_CAST, true, ConfigSpells.overblink.distance.toDouble())
        overwarp = SpellTalismanBlink("overwarp", ModSpirits.endererer, ConfigSpells.overwarp.favorCost, SpellOptions.ALL_CAST, false, ConfigSpells.overwarp.distance.toDouble())
        pearl_crumbs = SpellTalismanPearlCrumbs("pearl_crumbs", ModSpirits.endererer, ConfigSpells.pearlCrumbs.favorCost, SpellOptions.ALL_CAST)
        remote_chest = SpellTalismanRemoteChest("remote_chest", ModSpirits.endererer, ConfigSpells.remoteChest.favorCost, SpellOptions.USE_CAST, ModItems.storage_gem)
        surface_blink = SpellTalismanSurfaceBlink("surface_blink", ModSpirits.endererer, ConfigSpells.surfaceBlink.favorCost, SpellOptions.ALL_CAST_TRACE)
        surface_shift = SpellTalismanSurfaceShift("surface_shift", ModSpirits.endererer, ConfigSpells.surfaceShift.favorCost, SpellOptions.ALL_CAST)
        wall_slip = SpellTalismanWallSlip("wall_slip", ModSpirits.endererer, ConfigSpells.wallSlip.favorCost, SpellOptions.USE_CAST)
        warp = SpellTalismanBlink("warp", ModSpirits.endererer, ConfigSpells.warp.favorCost, SpellOptions.ALL_CAST, false, ConfigSpells.warp.distance.toDouble())
        warp_gem = SpellTalismanWarpMarker("warp_gem", ModSpirits.endererer, ConfigSpells.warpGem.favorCost, SpellOptions.ALL_CAST, ModItems.warp_gem)
        warp_pebble = SpellTalismanWarpMarker("warp_pebble", ModSpirits.endererer, ConfigSpells.warpPebble.favorCost, SpellOptions.ALL_CAST, ModItems.warp_pebble)

        // loon
        evil_eye = SpellTalismanEvilEye("evil_eye", ModSpirits.loon, ConfigSpells.evilEye.favorCost, SpellOptions.ENTITY_CAST)
        follow = SpellTalismanFollow("follow", ModSpirits.loon, ConfigSpells.follow.favorCost, SpellOptions.RIGHT_CAST)
        night_eye = SpellTalismanModPotionToggle("night_eye", ModSpirits.loon, ConfigSpells.nightEye.favorCost, ModPotions.night_eye)
        prismatic_eyes = SpellTalismanModPotionToggle("prismatic_eyes", ModSpirits.loon, ConfigSpells.prismaticEyes.favorCost, ModPotions.prismatic_eyes)
        spider_might = SpellTalismanModPotionToggle("spider_might", ModSpirits.loon, ConfigSpells.spider_might.favorCost, ModPotions.spider_might)
        summon_creeper = SpellTalismanSummonMinion("summon_creeper", ModSpirits.loon, ConfigSpells.summonCreeper.favorCost, SpellOptions.USE_CAST, EntityMinionCreeper::class.java)
        summon_husk = SpellTalismanSummonMinion("summon_husk", ModSpirits.loon, ConfigSpells.summonHusk.favorCost, SpellOptions.USE_CAST, EntityMinionHusk::class.java)
        summon_skeleton = SpellTalismanSummonMinion("summon_skeleton", ModSpirits.loon, ConfigSpells.summonSkeleton.favorCost, SpellOptions.USE_CAST, EntityMinionSkeleton::class.java)
        summon_stray = SpellTalismanSummonMinion("summon_stray", ModSpirits.loon, ConfigSpells.summonStray.favorCost, SpellOptions.USE_CAST, EntityMinionStray::class.java)
        summon_zombie = SpellTalismanSummonMinion("summon_zombie", ModSpirits.loon, ConfigSpells.summonZombie.favorCost, SpellOptions.USE_CAST, EntityMinionZombie::class.java)
        target = SpellTalismanTarget("target", ModSpirits.loon, 0, SpellOptions.ENTITY_CAST)

        // neblaze
        blazing_palm = SpellTalismanBlazingPalm("blazing_palm", ModSpirits.neblaze, ConfigSpells.blazingPalm.favorCost, SpellOptions.ALL_CAST)
        combustion = SpellTalismanCombustion("combustion", ModSpirits.neblaze, ConfigSpells.combustion.favorCost, SpellOptions.USE_CAST)
        distant_spark = SpellTalismanIgnition("distant_spark", ModSpirits.neblaze, ConfigSpells.distantSpark.favorCost, SpellOptions.ALL_CAST_TRACE)
        heat_wave = SpellTalismanHeatWave("heat_wave", ModSpirits.neblaze, ConfigSpells.heatWave.favorCost, SpellOptions.ALL_CAST)
        hellisphere = SpellTalismanHellisphere("hellisphere", ModSpirits.neblaze, ConfigSpells.hellisphere.favorCost, SpellOptions.USE_CAST)
        ignition = SpellTalismanIgnition("ignition", ModSpirits.neblaze, ConfigSpells.ignition.favorCost, SpellOptions.USE_CAST)
        infernal_touch = SpellTalismanInfernalTouch("infernal_touch", ModSpirits.neblaze, 1, SpellOptions.USE_CAST)
        molten_skin = SpellTalismanMoltenSkin("molten_skin", ModSpirits.neblaze, ConfigSpells.moltenSkin.favorCost, SpellOptions.ALL_CAST)
        nether_surge = SpellTalismanNetherSurge("nether_surge", ModSpirits.neblaze, ConfigSpells.netherSurge.favorCost, SpellOptions.USE_CAST)
        obsidian_bubble = SpellTalismanReplacmentBubble("obsidian_bubble", ModSpirits.blizrabi, ConfigSpells.obsidianBubble, Blocks.AIR, Blocks.OBSIDIAN, UtilPredicate.or(Block::isLava, Blocks.OBSIDIAN::equals))
        obsidian_carving = SpellTalismanCarving("obsidian_carving", ModSpirits.blizrabi, ConfigSpells.obsidianCarving.favorCost, SpellOptions.USE_CAST, Blocks.OBSIDIAN, Block::isLava)
        piercing_inferno = SpellTalismanPiercingInferno("piercing_inferno", ModSpirits.neblaze, ConfigSpells.piercingInferno.favorCost, SpellOptions.USE_CAST)
        searing_pulse = SpellTalismanSearingPulse("searing_pulse", ModSpirits.neblaze, ConfigSpells.searingPulse.favorCost, SpellOptions.USE_CAST)
        small_fireball_throw = SpellTalismanSmallFireballThrow("small_fireball_throw", ModSpirits.neblaze, ConfigSpells.smallFireballThrow.favorCost, SpellOptions.ALL_CAST)


        // redwind
        arrow_deflection = SpellTalismanModPotion("arrow_deflection", ModSpirits.redwind, ConfigSpells.arrowDeflection.favorCost, ModPotions.arrow_deflection, ConfigSpells.arrowDeflection.duration)
        clock = SpellTalismanClock("clock", ModSpirits.redwind, ConfigSpells.clock.favorCost, SpellOptions.ALL_CAST)
        ethereal_flash = SpellTalismanAirReplace("ethereal_flash", ModSpirits.redwind, ConfigSpells.etherealFlash.favorCost, SpellOptions.ALL_CAST_TRACE) { ModBlocks.ethereal_flash }
        ethereal_light = SpellTalismanAirReplace("ethereal_light", ModSpirits.redwind, ConfigSpells.etherealLight.favorCost, SpellOptions.ALL_CAST_TRACE) { ModBlocks.ethereal_light }
        fall_negation = SpellTalismanModPotionCharge("fall_negation", ModSpirits.redwind, ConfigSpells.fallNegation.favorCost, ModPotions.fall_negation, ConfigSpells.fallNegation.charges)
        ground_flow = SpellTalismanModPotionToggle("ground_flow", ModSpirits.redwind, ConfigSpells.groundFlow.favorCost, ModPotions.ground_flow)
        hovering = SpellTalismanModPotionToggle("hovering", ModSpirits.redwind, ConfigSpells.groundFlow.favorCost, ModPotions.hovering)
        ping = SpellTalismanPing("ping", ModSpirits.redwind, ConfigSpells.ping.favorCost, SpellOptions.TRACE_ONLY_CAST)
        red_pulse = SpellTalismanAirReplace("red_pulse", ModSpirits.redwind, ConfigSpells.redPulse.favorCost, SpellOptions.ALL_CAST_TRACE) { ModBlocks.red_pulse }
        red_signal = SpellTalismanAirReplace("red_signal", ModSpirits.redwind, ConfigSpells.redSignal.favorCost, SpellOptions.ALL_CAST_TRACE) { ModBlocks.red_signal }
        toadic_jump = SpellTalismanModPotionToggle("toadic_jump", ModSpirits.redwind, ConfigSpells.toadicJump.favorCost, ModPotions.toadic_jump)
        wild_sprint = SpellTalismanModPotion("wild_sprint", ModSpirits.redwind, ConfigSpells.wildSprint.favorCost, ModPotions.wild_charge, ConfigSpells.wildSprint.activationDelay)
        wind_step = SpellTalismanWindStep("wind_step", ModSpirits.redwind, ConfigSpells.windStep.favorCost, SpellOptions.ALL_CAST)
        place_torch = SpellTalismanPlaceTorch("place_torch", ModSpirits.redwind, ConfigSpells.placeTorch.favorCost, SpellOptions.TRACE_ONLY_CAST)

        // romol
        crushing_palm = SpellTalismanModPotionToggle("crushing_palm", ModSpirits.romol, ConfigSpells.crushingPalm.favorCost, ModPotions.crushing_palm)
        empower_axe = SpellTalismanModPotion("empower_axe", ModSpirits.romol, ConfigSpells.empowerAxe.favorCost, ModPotions.empower_axe, ConfigSpells.empowerAxe.duration)
        empower_pickaxe = SpellTalismanModPotion("empower_pickaxe", ModSpirits.romol, ConfigSpells.empowerPickaxe.favorCost, ModPotions.empower_pickaxe, ConfigSpells.empowerPickaxe.duration)
        fell_tree = SpellTalismanFellTree("fell_tree", ModSpirits.romol, ConfigSpells.fellTree.favorCost, SpellOptions.USE_CAST)
        green_cycle = SpellTalismanGreenCycle("green_cycle", ModSpirits.romol, ConfigSpells.greenCycle.favorCost, SpellOptions.ALL_CAST)
        harvest = SpellTalismanHarvest("harvest", ModSpirits.romol, ConfigSpells.harvest.favorCost, SpellOptions.ALL_CAST)
        miners_focus = SpellTalismanMinersFocus("miners_focus", ModSpirits.romol, ConfigSpells.minersFocus.favorCost, SpellOptions.ALL_CAST)
        place_block = SpellTalismanPlaceBlock("place_block", ModSpirits.romol, ConfigSpells.woodenPunch.favorCost, SpellOptions.ALL_CAST_TRACE)
        sense_block_approximate = SpellTalismanSenseInSphereHeld("sense_block_approximate", ModSpirits.romol, SpellOptions.RIGHT_CAST, Color3f(0.7f, 0.7f, 0f), ConfigSpells.senseBlockApproximate)
        sense_block_close = SpellTalismanSenseInSphereHeld("sense_block_close", ModSpirits.romol, SpellOptions.RIGHT_CAST, Color3f(0f, 0f, 0.7f), ConfigSpells.senseBlockClose)
        sense_block_precise = SpellTalismanSenseInSphereHeld("sense_block_precise", ModSpirits.romol, SpellOptions.RIGHT_CAST, Color3f(0.7f, 0f, 0f), ConfigSpells.senseBlockPrecise)
        sense_block_vague = SpellTalismanSenseInSphereHeld("sense_block_vague", ModSpirits.romol, SpellOptions.RIGHT_CAST, Color3f(0f, 0.7f, 0f), ConfigSpells.senseBlockVague)
        sense_cave = SpellTalismanSenseFloodFill("sense_cave", ModSpirits.romol, SpellOptions.USE_CAST, Color3f(0f, 0.2f, 0.9f), ConfigSpells.senseWater, SenseBlockPredicate.AIR, 500, 32)
        sense_lava = SpellTalismanSenseInSphereType("sense_lava", ModSpirits.romol, SpellOptions.RIGHT_CAST, Color3f(0.9f, 0.2f, 0f), ConfigSpells.senseLava, SenseBlockPredicate.LAVA)
        sense_liquid = SpellTalismanSenseInSphereType("sense_liquid", ModSpirits.romol, SpellOptions.RIGHT_CAST, Color3f(0.7f, 0.2f, 0.7f), ConfigSpells.senseLiquid, SenseBlockPredicate.LIQUID)
        sense_ore_approximate = SpellTalismanSenseInSphereType("sense_ore_approximate", ModSpirits.romol, SpellOptions.RIGHT_CAST, Color3f(0.7f, 0.7f, 0f), ConfigSpells.senseOreApproximate, SenseBlockPredicate.ORE)
        sense_ore_close = SpellTalismanSenseInSphereType("sense_ore_close", ModSpirits.romol, SpellOptions.RIGHT_CAST, Color3f(0f, 0f, 0.7f), ConfigSpells.senseOreClose, SenseBlockPredicate.ORE)
        sense_ore_precise = SpellTalismanSenseInSphereType("sense_ore_precise", ModSpirits.romol, SpellOptions.RIGHT_CAST, Color3f(0.7f, 0f, 0f), ConfigSpells.senseOrePrecise, SenseBlockPredicate.ORE)
        sense_ore_vague = SpellTalismanSenseInSphereType("sense_ore_vague", ModSpirits.romol, SpellOptions.RIGHT_CAST, Color3f(0f, 0.7f, 0f), ConfigSpells.senseOreVague, SenseBlockPredicate.ORE)
        sense_water = SpellTalismanSenseInSphereType("sense_water", ModSpirits.romol, SpellOptions.RIGHT_CAST, Color3f(0f, 0.2f, 0.9f), ConfigSpells.senseWater, SenseBlockPredicate.WATER)
        stone_fever = SpellTalismanModPotion("stone_fever", ModSpirits.romol, ConfigSpells.stoneFever.favorCost, ModPotions.stone_fever, ConfigSpells.stoneFever.duration)
        stoneball_throw = SpellTalismanStoneballThrow("stoneball_throw", ModSpirits.romol, ConfigSpells.stoneballThrow.favorCost, SpellOptions.ALL_CAST)
        wooden_punch = SpellTalismanModPotionToggle("wooden_punch", ModSpirits.romol, ConfigSpells.woodenPunch.favorCost, ModPotions.wooden_punch)


        // squarefury
        consuming_fury = SpellTalismanModPotion("consuming_fury", ModSpirits.squarefury, ConfigSpells.consumingFury.favorCost, ModPotions.consuming_fury, ConfigSpells.consumingFury.duration)
        focused_fury = SpellTalismanModPotion("focused_fury", ModSpirits.squarefury, ConfigSpells.focusedFury.favorCost, ModPotions.focused_fury, ConfigSpells.focusedFury.duration)
        grudge = SpellTalismanGrudge("grudge", ModSpirits.squarefury, ConfigSpells.grudge.favorCost, SpellOptions.ALL_CAST)
        mist_blade = SpellTalismanModPotionToggle("mist_blade", ModSpirits.squarefury, ConfigSpells.mistBlade.favorCost, ModPotions.mist_blade)
        sense_entity_passive = SpellTalismanSenseEntities("sense_entity_passive", ModSpirits.squarefury, SpellOptions.RIGHT_CAST, Color3f(0f, 0.2f, 0.9f), ConfigSpells.senseEntityPassive, SenseEntitiesPredicate.PASSIVE)
        sense_entity_hostile = SpellTalismanSenseEntities("sense_entity_hostile", ModSpirits.squarefury, SpellOptions.RIGHT_CAST, Color3f(0.9f, 0f, 0.2f), ConfigSpells.senseEntityHostile, SenseEntitiesPredicate.HOSTILE)
        sense_entity_player = SpellTalismanSenseEntities("sense_entity_player", ModSpirits.squarefury, SpellOptions.RIGHT_CAST, Color3f(0f, 0.9f, 0.2f), ConfigSpells.senseEntityPlayer, SenseEntitiesPredicate.PLAYERS)
        sense_entity_all = SpellTalismanSenseEntities("sense_entity_all", ModSpirits.squarefury, SpellOptions.RIGHT_CAST, Color3f(0.7f, 0.7f, 0f), ConfigSpells.senseEntityAll, SenseEntitiesPredicate.ALL)

        // timber
        blade_of_grass = SpellTalismanBladeOfGrass("blade_of_grass", ModSpirits.timber, ConfigSpells.bladeOfGrass.favorCost, SpellOptions.ALL_CAST)
        blood_of_grass = SpellTalismanBloodOfGrass("blood_of_grass", ModSpirits.timber, ConfigSpells.bloodOfGrass.favorCost, SpellOptions.ALL_CAST)
        bonemeal = SpellTalismanBonemeal("bonemeal", ModSpirits.timber, ConfigSpells.bonemeal.favorCost, SpellOptions.USE_CAST)
        starvation = SpellTalismanModPotion("starvation", ModSpirits.timber, ConfigSpells.starvation.favorCost, ModPotions.starvation, ConfigSpells.starvation.duration)

        // New instances
    }

    fun init() {}
}