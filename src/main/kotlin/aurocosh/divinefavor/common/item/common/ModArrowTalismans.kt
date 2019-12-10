package aurocosh.divinefavor.common.item.common

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.config.common.ConfigCurses
import aurocosh.divinefavor.common.item.arrow_talismans.*
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowOptions
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowTalismanCurse
import aurocosh.divinefavor.common.item.arrow_talismans.base.ArrowType
import aurocosh.divinefavor.common.item.arrow_talismans.base.ItemArrowTalisman
import aurocosh.divinefavor.common.potions.common.ModCurses
import aurocosh.divinefavor.common.spirit.ModSpirits
import java.awt.Color

object ModArrowTalismans {
    lateinit var anti_gravity: ItemArrowTalisman
    lateinit var armor_corrosion: ItemArrowTalisman
    lateinit var blast: ItemArrowTalisman
    lateinit var blink: ItemArrowTalisman
    lateinit var climbing: ItemArrowTalisman
    lateinit var crawling_mist: ItemArrowTalisman
    lateinit var cripple: ItemArrowTalisman
    lateinit var destructive_1: ItemArrowTalisman
    lateinit var destructive_2: ItemArrowTalisman
    lateinit var destructive_3: ItemArrowTalisman
    lateinit var disarm: ItemArrowTalisman
    lateinit var explosive: ItemArrowTalisman
    lateinit var extinguish_fire: ItemArrowTalisman
    lateinit var fiery_mark: ItemArrowTalisman
    lateinit var fill_lungs: ItemArrowTalisman
    lateinit var flak: ItemArrowTalisman
    lateinit var force: ItemArrowTalisman
    lateinit var gamble: ItemArrowTalisman
    lateinit var hand_swap: ItemArrowTalisman
    lateinit var high_speed: ItemArrowTalisman
    lateinit var hollow_leg: ItemArrowTalisman
    lateinit var hover_bubble: ItemArrowTalisman
    lateinit var hyper_speed: ItemArrowTalisman
    lateinit var ice_breaker: ItemArrowTalisman
    lateinit var ice_sphere: ItemArrowTalisman
    lateinit var impulse: ItemArrowTalisman
    lateinit var incendiary: ItemArrowTalisman
    lateinit var life_steal: ItemArrowTalisman
    lateinit var limp_leg: ItemArrowTalisman
    lateinit var lucky_arrow: ItemArrowTalisman
    lateinit var mine: ItemArrowTalisman
    lateinit var nether_swap: ItemArrowTalisman
    lateinit var nuke: ItemArrowTalisman
    lateinit var petrification: ItemArrowTalisman
    lateinit var piercing: ItemArrowTalisman
    lateinit var reinforced_1: ItemArrowTalisman
    lateinit var reinforced_2: ItemArrowTalisman
    lateinit var reinforced_3: ItemArrowTalisman
    lateinit var ricochet: ItemArrowTalisman
    lateinit var roots: ItemArrowTalisman
    lateinit var skyfall: ItemArrowTalisman
    lateinit var sniper: ItemArrowTalisman
    lateinit var spooky: ItemArrowTalisman
    lateinit var stasis: ItemArrowTalisman
    lateinit var suffocating_fumes: ItemArrowTalisman
    lateinit var tracer: ItemArrowTalisman
    lateinit var vacuum: ItemArrowTalisman
    lateinit var wind_leash: ItemArrowTalisman
    lateinit var yummy_smell: ItemArrowTalisman
    lateinit var zero_g: ItemArrowTalisman

    // New fields

    fun preInit() {
        val enderererColor = Color(0, 124, 86)
        val blizrabiColor = Color(231, 236, 255)
        val genericTier1 = Color(255, 232, 109)
        val genericTier2 = Color(51, 163, 255)
        val genericTier3 = Color(255, 110, 31)
        val climbingColor = Color(255, 133, 53)

        // arbow;
        anti_gravity = ArrowTalismanAntiGravityArrow("anti_gravity", ModSpirits.arbow, ConfigArrow.antiGravityArrow.favorCost, Color.green, ConfigArrow.antiGravityArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        climbing = ArrowTalismanClimbableArrow("climbing", ModSpirits.arbow, ConfigArrow.climbingArrow.favorCost, climbingColor, ConfigArrow.climbingArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW, ConfigArrow.climbingArrow.climbingSpeed, ConfigArrow.climbingArrow.climbingDistance, ConfigArrow.climbingArrow.despawnDelay)
        destructive_1 = ArrowTalismanDestructiveArrow("destructive_1", ModSpirits.arbow, ConfigArrow.destructiveArrow1.favorCost, genericTier1, ConfigArrow.destructiveArrow1.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow1.maxHardness.toFloat())
        gamble = ArrowTalismanGamble("gamble", ModSpirits.arbow, Color(250, 20, 20), ArrowOptions.REQUIRES_TARGET, ArrowType.WOODEN_ARROW, ConfigArrow.gambleArrow)
        mine = ArrowTalismanMineArrow("mine", ModSpirits.arbow, ConfigArrow.mineArrow.favorCost, Color(164, 163, 168), ConfigArrow.mineArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        ricochet = ArrowTalismanRicochetArrow("ricochet", ModSpirits.arbow, ConfigArrow.ricochetArrow.favorCost, Color(168, 24, 105), ConfigArrow.ricochetArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        tracer = ArrowTalismanTracerArrow("tracer", ModSpirits.arbow, ConfigArrow.tracerArrow.favorCost, Color(203, 3, 0), ConfigArrow.tracerArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)

        incendiary = ArrowTalismanIncendiaryArrow("incendiary", ModSpirits.arbow, ConfigArrow.incendiaryArrow.favorCost, Color(180, 80, 0), ConfigArrow.incendiaryArrow.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW)
        destructive_2 = ArrowTalismanDestructiveArrow("destructive_2", ModSpirits.arbow, ConfigArrow.destructiveArrow2.favorCost, genericTier2, ConfigArrow.destructiveArrow2.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow2.maxHardness.toFloat())
        destructive_3 = ArrowTalismanDestructiveArrow("destructive_3", ModSpirits.arbow, ConfigArrow.destructiveArrow3.favorCost, genericTier3, ConfigArrow.destructiveArrow3.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.destructiveArrow3.maxHardness.toFloat())
        disarm = ArrowTalismanDisarm("disarm", ModSpirits.arbow, ConfigArrow.disarm.favorCost, Color.orange, ConfigArrow.disarm.damage.toDouble(), ArrowOptions.REQUIRES_TARGET, ArrowType.WOODEN_ARROW)
        flak = ArrowTalismanFlakArrow("flak", ModSpirits.arbow, ConfigArrow.flakArrow.favorCost, Color(185, 100, 110), ConfigArrow.flakArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        force = ArrowTalismanForceArrow("force", ModSpirits.arbow, ConfigArrow.forceArrow.favorCost, Color(65, 64, 69), ConfigArrow.forceArrow.damage.toDouble(), ArrowType.WOODEN_ARROW, ConfigArrow.forceArrow.velocity)
        hand_swap = ArrowTalismanHandSwap("hand_swap", ModSpirits.arbow, ConfigArrow.handSwap.favorCost, Color.orange, ConfigArrow.handSwap.damage.toDouble(), ArrowOptions.REQUIRES_TARGET, ArrowType.WOODEN_ARROW)
        high_speed = ArrowTalismanHighSpeedArrow("high_speed", ModSpirits.arbow, ConfigArrow.highSpeedArrow.favorCost, genericTier2, ConfigArrow.highSpeedArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW, ConfigArrow.highSpeedArrow.extraVelocity)
        hover_bubble = ArrowTalismanHoverBubbleArrow("hover_bubble", ModSpirits.arbow, ConfigArrow.hoverBubbleArrow.favorCost, climbingColor, ConfigArrow.hoverBubbleArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW, ConfigArrow.hoverBubbleArrow.climbingSpeed, ConfigArrow.hoverBubbleArrow.climbingDistance, ConfigArrow.hoverBubbleArrow.despawnDelay)
        hyper_speed = ArrowTalismanHighSpeedArrow("hyper_speed", ModSpirits.arbow, ConfigArrow.hyperSpeedArrow.favorCost, genericTier3, ConfigArrow.hyperSpeedArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW, ConfigArrow.hyperSpeedArrow.extraVelocity)
        impulse = ArrowTalismanForceArrow("impulse", ModSpirits.arbow, ConfigArrow.impulseArrow.favorCost, Color(114, 113, 118), ConfigArrow.impulseArrow.damage.toDouble(), ArrowType.WOODEN_ARROW, ConfigArrow.impulseArrow.velocity)
        piercing = ArrowTalismanPiercingArrow("piercing", ModSpirits.arbow, ConfigArrow.piercingArrow.favorCost, Color(100, 104, 168), ConfigArrow.piercingArrow.damage, ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)

        sniper = ArrowTalismanSniperArrow("sniper", ModSpirits.arbow, ConfigArrow.sniperArrow.favorCost, Color(206, 206, 0), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        zero_g = ArrowTalismanZeroGArrow("zero_g", ModSpirits.arbow, ConfigArrow.zeroGArrow.favorCost, Color(0, 168, 122), ConfigArrow.zeroGArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        lucky_arrow = ArrowTalismanLuckyArrow("lucky_arrow", ModSpirits.arbow, ConfigArrow.luckyArrow.favorCost, Color.gray,ConfigArrow.luckyArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)

        // blizrabi;
        extinguish_fire = ArrowTalismanExtinguishFire("extinguish_fire", ModSpirits.blizrabi, ConfigArrow.extinguishFire.favorCost, Color(125, 124, 128), ConfigArrow.extinguishFire.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW)

        ice_sphere = ArrowTalismanIceSphere("ice_sphere", ModSpirits.blizrabi, ConfigArrow.iceSphereArrow.favorCost, blizrabiColor, ConfigArrow.iceSphereArrow.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW)
        ice_breaker = ArrowTalismanIceBreaker("ice_breaker", ModSpirits.blizrabi, ConfigArrow.iceSphereArrow.favorCost, blizrabiColor, ConfigArrow.iceSphereArrow.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW)

        // endererer;
        nether_swap = ArrowTalismanNetherSwap("nether_swap", ModSpirits.endererer, ConfigArrow.netherSwap.favorCost, enderererColor, ConfigArrow.netherSwap.damage.toDouble(), ArrowType.WOODEN_ARROW)

        blink = ArrowTalismanBlinkArrow("blink", ModSpirits.endererer, ConfigArrow.blinkArrow.favorCost, enderererColor, ConfigArrow.blinkArrow.damage.toDouble(), ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW)
        // loon;
        life_steal = ArrowTalismanLifeStealArrow("life_steal", ModSpirits.loon, ConfigArrow.lifeStealArrow.favorCost, Color(16, 211, 0), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        spooky = ArrowTalismanSpookyArrow("spooky", ModSpirits.loon, ConfigArrow.spookyArrow.favorCost, Color.green, ConfigArrow.spookyArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)

        stasis = ArrowTalismanStasisArrow("stasis", ModSpirits.loon, ConfigArrow.stasisArrow.favorCost, Color(164, 163, 168), ConfigArrow.stasisArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        vacuum = ArrowTalismanVacuumArrow("vacuum", ModSpirits.loon, ConfigArrow.vacuumArrow.favorCost, Color(83, 4, 124), ConfigArrow.vacuumArrow.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)

        // neblaze;
        blast = ArrowTalismanExplosive("blast", ModSpirits.neblaze, genericTier1, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.blastArrow)
        explosive = ArrowTalismanExplosive("explosive", ModSpirits.neblaze, genericTier2, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.explosiveArrow)
        nuke = ArrowTalismanExplosive("nuke", ModSpirits.neblaze, genericTier3, ArrowOptions.ARROW_BREAKS, ArrowType.WOODEN_ARROW, ConfigArrow.nukeArrow)

        // redwind;

        // romol;

        // squarefury;
        reinforced_1 = ItemArrowTalisman("reinforced_1", ModSpirits.squarefury, ConfigArrow.reinforcedArrow1.favorCost, genericTier1, ConfigArrow.reinforcedArrow1.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)

        reinforced_2 = ItemArrowTalisman("reinforced_2", ModSpirits.squarefury, ConfigArrow.reinforcedArrow2.favorCost, genericTier2, ConfigArrow.reinforcedArrow2.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)
        reinforced_3 = ItemArrowTalisman("reinforced_3", ModSpirits.squarefury, ConfigArrow.reinforcedArrow3.favorCost, genericTier3, ConfigArrow.reinforcedArrow3.damage.toDouble(), ArrowOptions.NORMAL, ArrowType.WOODEN_ARROW)

        // timber;
        val curseColor1 = Color(88, 4, 130)
        val curseColor2 = Color(90, 23, 0)
        val curseColor3 = Color(90, 90, 0)
        fill_lungs = ArrowTalismanCurse("fill_lungs", ModSpirits.timber, ConfigArrow.fillLungs.favorCost, curseColor2, ModCurses.fill_lungs, ConfigCurses.fillLungs.duration)
        suffocating_fumes = ArrowTalismanCurse("suffocating_fumes", ModSpirits.timber, ConfigArrow.suffocatingFumes.favorCost, curseColor1, ModCurses.suffocating_fumes, ConfigCurses.suffocatingFumes.duration)
        wind_leash = ArrowTalismanCurse("wind_leash", ModSpirits.timber, ConfigArrow.windLeash.favorCost, curseColor1, ModCurses.wind_leash, ConfigCurses.windLeash.duration)

        armor_corrosion = ArrowTalismanCurse("armor_corrosion", ModSpirits.timber, ConfigArrow.armorCorrosion.favorCost, curseColor1, ModCurses.armor_corrosion, ConfigCurses.armorCorrosion.duration)
        crawling_mist = ArrowTalismanCurse("crawling_mist", ModSpirits.timber, ConfigArrow.crawlingMist.favorCost, curseColor2, ModCurses.crawling_mist, ConfigCurses.crawlingMist.duration)
        cripple = ArrowTalismanCurse("cripple", ModSpirits.timber, ConfigArrow.cripple.favorCost, curseColor3, ModCurses.cripple, ConfigCurses.cripple.duration)
        fiery_mark = ArrowTalismanCurse("fiery_mark", ModSpirits.neblaze, ConfigArrow.fieryMark.favorCost, curseColor1, ModCurses.fiery_mark, ConfigCurses.fieryMark.duration)
        hollow_leg = ArrowTalismanCurse("hollow_leg", ModSpirits.timber, ConfigArrow.hollowLeg.favorCost, curseColor3, ModCurses.hollow_leg, ConfigCurses.hollowLeg.duration)
        limp_leg = ArrowTalismanCurse("limp_leg", ModSpirits.timber, ConfigArrow.limpLeg.favorCost, curseColor3, ModCurses.limp_leg, ConfigCurses.limpLeg.duration)
        petrification = ArrowTalismanCurse("petrification", ModSpirits.timber, ConfigArrow.petrification.favorCost, curseColor2, ModCurses.petrification, ConfigCurses.petrification.duration)
        roots = ArrowTalismanCurse("roots", ModSpirits.timber, ConfigArrow.roots.favorCost, curseColor2, ModCurses.roots, ConfigCurses.roots.duration)
        skyfall = ArrowTalismanCurse("skyfall", ModSpirits.timber, ConfigArrow.skyfall.favorCost, curseColor1, ModCurses.skyfall, ConfigCurses.skyfall.duration)
        yummy_smell = ArrowTalismanCurse("yummy_smell", ModSpirits.timber, ConfigArrow.yummySmell.favorCost, curseColor2, ModCurses.yummy_smell, ConfigCurses.yummySmell.duration)

        // New instances
    }
}
