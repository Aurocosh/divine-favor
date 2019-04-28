package aurocosh.divinefavor.common.item.talismans.arrow.common

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.item.talismans.arrow.*
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowOptions
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowTalismanCurse
import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.spirit.ModSpirits
import java.awt.Color

object ModArrowTalismans {
    lateinit var anti_gravity_arrow: ItemArrowTalisman
    lateinit var armor_corrosion: ItemArrowTalisman
    lateinit var blast_arrow: ItemArrowTalisman
    lateinit var blink_arrow: ItemArrowTalisman
    lateinit var climbing_arrow: ItemArrowTalisman
    lateinit var crawling_mist: ItemArrowTalisman
    lateinit var cripple: ItemArrowTalisman
    lateinit var destructive_arrow_1: ItemArrowTalisman
    lateinit var destructive_arrow_2: ItemArrowTalisman
    lateinit var destructive_arrow_3: ItemArrowTalisman
    lateinit var disarm: ItemArrowTalisman
    lateinit var explosive_arrow: ItemArrowTalisman
    lateinit var extinguish_fire: ItemArrowTalisman
    lateinit var fiery_mark: ItemArrowTalisman
    lateinit var fill_lungs: ItemArrowTalisman
    lateinit var flak_arrow: ItemArrowTalisman
    lateinit var force_arrow: ItemArrowTalisman
    lateinit var hand_swap: ItemArrowTalisman
    lateinit var high_speed_arrow: ItemArrowTalisman
    lateinit var hollow_leg: ItemArrowTalisman
    lateinit var hover_bubble_arrow: ItemArrowTalisman
    lateinit var hyper_speed_arrow: ItemArrowTalisman
    lateinit var ice_ball_arrow: ItemArrowTalisman
    lateinit var ice_breaker: ItemArrowTalisman
    lateinit var impulse_arrow: ItemArrowTalisman
    lateinit var incendiary_arrow: ItemArrowTalisman
    lateinit var life_steal_arrow: ItemArrowTalisman
    lateinit var limp_leg: ItemArrowTalisman
    lateinit var mine_arrow: ItemArrowTalisman
    lateinit var nether_swap: ItemArrowTalisman
    lateinit var nuke_arrow: ItemArrowTalisman
    lateinit var petrification: ItemArrowTalisman
    lateinit var piercing_arrow: ItemArrowTalisman
    lateinit var reinforced_arrow_1: ItemArrowTalisman
    lateinit var reinforced_arrow_2: ItemArrowTalisman
    lateinit var reinforced_arrow_3: ItemArrowTalisman
    lateinit var ricochet_arrow: ItemArrowTalisman
    lateinit var roots: ItemArrowTalisman
    lateinit var skyfall: ItemArrowTalisman
    lateinit var sniper_arrow: ItemArrowTalisman
    lateinit var spooky_arrow: ItemArrowTalisman
    lateinit var stasis_arrow: ItemArrowTalisman
    lateinit var suffocating_fumes: ItemArrowTalisman
    lateinit var tracer_arrow: ItemArrowTalisman
    lateinit var vacuum_arrow: ItemArrowTalisman
    lateinit var wind_leash: ItemArrowTalisman
    lateinit var yummy_smell: ItemArrowTalisman
    lateinit var zero_g_arrow: ItemArrowTalisman

    fun preInit() {
        val enderererColor = Color(0, 124, 86)
        val blizrabiColor = Color(231, 236, 255)
        val genericTier1 = Color(255, 232, 109)
        val genericTier2 = Color(51, 163, 255)
        val genericTier3 = Color(255, 110, 31)
        val climbingColor = Color(124, 78, 47)

        // arbow;
        anti_gravity_arrow = ArrowTalismanAntiGravityArrow("anti_gravity_arrow", ModSpirits.arbow, ConfigArrow.antiGravityArrow.favorCost, Color.green, ConfigArrow.antiGravityArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        blast_arrow = ArrowTalismanExplosive("blast_arrow", ModSpirits.arbow, genericTier1, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.blastArrow)
        climbing_arrow = ArrowTalismanClimbableArrow("climbing_arrow", ModSpirits.arbow, ConfigArrow.climbingArrow.favorCost, climbingColor, ConfigArrow.climbingArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW, ConfigArrow.climbingArrow.climbingSpeed, ConfigArrow.climbingArrow.climbingDistance, ConfigArrow.climbingArrow.despawnDelay)
        destructive_arrow_1 = ArrowTalismanDestructiveArrow("destructive_arrow_1", ModSpirits.arbow, ConfigArrow.destructiveArrow1.favorCost, genericTier1, ConfigArrow.destructiveArrow1.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow1.maxHardness.toFloat())
        destructive_arrow_2 = ArrowTalismanDestructiveArrow("destructive_arrow_2", ModSpirits.arbow, ConfigArrow.destructiveArrow2.favorCost, genericTier2, ConfigArrow.destructiveArrow2.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow2.maxHardness.toFloat())
        destructive_arrow_3 = ArrowTalismanDestructiveArrow("destructive_arrow_3", ModSpirits.arbow, ConfigArrow.destructiveArrow3.favorCost, genericTier3, ConfigArrow.destructiveArrow3.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow3.maxHardness.toFloat())
        disarm = ArrowTalismanDisarm("disarm", ModSpirits.arbow, ConfigArrow.disarm.favorCost, Color.orange, ConfigArrow.disarm.damage.toDouble(), ArrowOptions.REQUIRES_TARGET, ArrowType.WOODEN_ARROW)
        explosive_arrow = ArrowTalismanExplosive("explosive_arrow", ModSpirits.arbow, genericTier2, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.explosiveArrow)
        flak_arrow = ArrowTalismanFlakArrow("flak_arrow", ModSpirits.arbow, ConfigArrow.flakArrow.favorCost, Color(185, 100, 110), ConfigArrow.flakArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        force_arrow = ArrowTalismanForceArrow("force_arrow", ModSpirits.arbow, ConfigArrow.forceArrow.favorCost, Color(65, 64, 69), ConfigArrow.forceArrow.damage.toDouble(), ArrowType.WOODEN_ARROW, ConfigArrow.forceArrow.velocity)
        hand_swap = ArrowTalismanHandSwap("hand_swap", ModSpirits.arbow, ConfigArrow.handSwap.favorCost, Color.orange, ConfigArrow.handSwap.damage.toDouble(), ArrowOptions.REQUIRES_TARGET, ArrowType.WOODEN_ARROW)
        high_speed_arrow = ArrowTalismanHighSpeedArrow("high_speed_arrow", ModSpirits.arbow, ConfigArrow.highSpeedArrow.favorCost, genericTier2, ConfigArrow.highSpeedArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW, ConfigArrow.highSpeedArrow.extraVelocity)
        hover_bubble_arrow = ArrowTalismanHoverBubbleArrow("hover_bubble_arrow", ModSpirits.arbow, ConfigArrow.hoverBubbleArrow.favorCost, climbingColor, ConfigArrow.hoverBubbleArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW, ConfigArrow.hoverBubbleArrow.climbingSpeed, ConfigArrow.hoverBubbleArrow.climbingDistance, ConfigArrow.hoverBubbleArrow.despawnDelay)
        hyper_speed_arrow = ArrowTalismanHighSpeedArrow("hyper_speed_arrow", ModSpirits.arbow, ConfigArrow.hyperSpeedArrow.favorCost, genericTier3, ConfigArrow.hyperSpeedArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW, ConfigArrow.hyperSpeedArrow.extraVelocity)
        impulse_arrow = ArrowTalismanForceArrow("impulse_arrow", ModSpirits.arbow, ConfigArrow.impulseArrow.favorCost, Color(114, 113, 118), ConfigArrow.impulseArrow.damage.toDouble(), ArrowType.WOODEN_ARROW, ConfigArrow.impulseArrow.velocity)
        incendiary_arrow = ArrowTalismanIncendiaryArrow("incendiary_arrow", ModSpirits.arbow, ConfigArrow.incendiaryArrow.favorCost, Color(180, 80, 0), ConfigArrow.incendiaryArrow.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW)
        life_steal_arrow = ArrowTalismanLifeStealArrow("life_steal_arrow", ModSpirits.arbow, ConfigArrow.lifeStealArrow.favorCost, Color(16, 211, 0), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        mine_arrow = ArrowTalismanMineArrow("mine_arrow", ModSpirits.arbow, ConfigArrow.mineArrow.favorCost, Color(164, 163, 168), ConfigArrow.mineArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        nuke_arrow = ArrowTalismanExplosive("nuke_arrow", ModSpirits.arbow, genericTier3, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.nukeArrow)
        piercing_arrow = ArrowTalismanPiercingArrow("piercing_arrow", ModSpirits.arbow, ConfigArrow.piercingArrow.favorCost, Color(100, 104, 168), ConfigArrow.piercingArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        reinforced_arrow_1 = ItemArrowTalisman("reinforced_arrow_1", ModSpirits.arbow, ConfigArrow.reinforcedArrow1.favorCost, genericTier1, ConfigArrow.reinforcedArrow1.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        reinforced_arrow_2 = ItemArrowTalisman("reinforced_arrow_2", ModSpirits.arbow, ConfigArrow.reinforcedArrow2.favorCost, genericTier2, ConfigArrow.reinforcedArrow2.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        reinforced_arrow_3 = ItemArrowTalisman("reinforced_arrow_3", ModSpirits.arbow, ConfigArrow.reinforcedArrow3.favorCost, genericTier3, ConfigArrow.reinforcedArrow3.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        ricochet_arrow = ArrowTalismanRicochetArrow("ricochet_arrow", ModSpirits.arbow, ConfigArrow.ricochetArrow.favorCost, Color(168, 24, 105), ConfigArrow.ricochetArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        sniper_arrow = ArrowTalismanSniperArrow("sniper_arrow", ModSpirits.arbow, ConfigArrow.sniperArrow.favorCost, Color(206, 206, 0), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        spooky_arrow = ArrowTalismanSpookyArrow("spooky_arrow", ModSpirits.arbow, ConfigArrow.spookyArrow.favorCost, Color.green, ConfigArrow.spookyArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        stasis_arrow = ArrowTalismanStasisArrow("stasis_arrow", ModSpirits.arbow, ConfigArrow.stasisArrow.favorCost, Color(164, 163, 168), ConfigArrow.stasisArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        tracer_arrow = ArrowTalismanTracerArrow("tracer_arrow", ModSpirits.arbow, ConfigArrow.tracerArrow.favorCost, Color(203, 3, 0), ConfigArrow.tracerArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        vacuum_arrow = ArrowTalismanVacuumArrow("vacuum_arrow", ModSpirits.arbow, ConfigArrow.vacuumArrow.favorCost, Color(83, 4, 124), ConfigArrow.vacuumArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        zero_g_arrow = ArrowTalismanZeroGArrow("zero_g_arrow", ModSpirits.arbow, ConfigArrow.zeroGArrow.favorCost, Color(0, 168, 122), ConfigArrow.zeroGArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)

        // blizrabi;
        ice_ball_arrow = ArrowTalismanIceSphere("ice_sphere", ModSpirits.arbow, ConfigArrow.iceSphereArrow.favorCost, blizrabiColor, ConfigArrow.iceSphereArrow.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW)
        ice_breaker = ArrowTalismanIceBreaker("ice_breaker", ModSpirits.arbow, ConfigArrow.iceSphereArrow.favorCost, blizrabiColor, ConfigArrow.iceSphereArrow.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW)
        extinguish_fire = ArrowTalismanExtinguishFire("extinguish_fire", ModSpirits.arbow, ConfigArrow.extinguishFire.favorCost, Color(125, 124, 128), ConfigArrow.extinguishFire.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW)

        // endererer;
        blink_arrow = ArrowTalismanBlinkArrow("blink_arrow", ModSpirits.endererer, ConfigArrow.blinkArrow.favorCost, enderererColor, ConfigArrow.blinkArrow.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW)
        nether_swap = ArrowTalismanNetherSwap("nether_swap", ModSpirits.endererer, ConfigArrow.netherSwap.favorCost, enderererColor, ConfigArrow.netherSwap.damage.toDouble(), ArrowType.WOODEN_ARROW)
        // loon;

        // neblaze;

        // redwind;

        // romol;

        // squarefury;

        // timber;
        val curseColor1 = Color(88, 4, 130)
        val curseColor2 = Color(90, 23, 0)
        val curseColor3 = Color(90, 90, 0)
        armor_corrosion = ArrowTalismanCurse("armor_corrosion", ModSpirits.timber, ConfigArrow.armorCorrosion.favorCost, curseColor1, ModCurses.armor_corrosion, ConfigArrow.armorCorrosion.duration)
        crawling_mist = ArrowTalismanCurse("crawling_mist", ModSpirits.timber, ConfigArrow.crawlingMist.favorCost, curseColor2, ModCurses.crawling_mist, ConfigArrow.crawlingMist.duration)
        cripple = ArrowTalismanCurse("cripple", ModSpirits.timber, ConfigArrow.cripple.favorCost, curseColor3, ModCurses.cripple, ConfigArrow.cripple.duration)
        fiery_mark = ArrowTalismanCurse("fiery_mark", ModSpirits.neblaze, ConfigArrow.fieryMark.favorCost, curseColor1, ModCurses.fiery_mark, ConfigArrow.fieryMark.duration)
        fill_lungs = ArrowTalismanCurse("fill_lungs", ModSpirits.blizrabi, ConfigArrow.fillLungs.favorCost, curseColor2, ModCurses.fill_lungs, ConfigArrow.fillLungs.duration)
        hollow_leg = ArrowTalismanCurse("hollow_leg", ModSpirits.timber, ConfigArrow.hollowLeg.favorCost, curseColor3, ModCurses.hollow_leg, ConfigArrow.hollowLeg.duration)
        limp_leg = ArrowTalismanCurse("limp_leg", ModSpirits.timber, ConfigArrow.limpLeg.favorCost, curseColor3, ModCurses.limp_leg, ConfigArrow.limpLeg.duration)
        petrification = ArrowTalismanCurse("petrification", ModSpirits.romol, ConfigArrow.petrification.favorCost, curseColor2, ModCurses.petrification, ConfigArrow.petrification.duration)
        roots = ArrowTalismanCurse("roots", ModSpirits.timber, ConfigArrow.roots.favorCost, curseColor2, ModCurses.roots, ConfigArrow.roots.duration)
        skyfall = ArrowTalismanCurse("skyfall", ModSpirits.redwind, ConfigArrow.skyfall.favorCost, curseColor1, ModCurses.skyfall, ConfigArrow.skyfall.duration)
        suffocating_fumes = ArrowTalismanCurse("suffocating_fumes", ModSpirits.timber, ConfigArrow.suffocatingFumes.favorCost, curseColor1, ModCurses.suffocating_fumes, ConfigArrow.suffocatingFumes.duration)
        wind_leash = ArrowTalismanCurse("wind_leash", ModSpirits.redwind, ConfigArrow.windLeash.favorCost, curseColor1, ModCurses.wind_leash, ConfigArrow.windLeash.duration)
        yummy_smell = ArrowTalismanCurse("yummy_smell", ModSpirits.timber, ConfigArrow.yummySmell.favorCost, curseColor2, ModCurses.yummy_smell, ConfigArrow.yummySmell.duration)
    }
}
