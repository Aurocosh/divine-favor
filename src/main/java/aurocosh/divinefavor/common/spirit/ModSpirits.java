package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.item.talismans.ModTalismans;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.spirit.punishment.BlizrabiPunishment;
import aurocosh.divinefavor.common.spirit.punishment.NefirPunishment;
import aurocosh.divinefavor.common.spirit.punishment.SquareFuryPunishment;

public final class ModSpirits {
    public static ModSpirit allfire;
    public static ModSpirit arbow;
    public static ModSpirit blizrabi;
    public static ModSpirit endererer;
    public static ModSpirit nefir;
    public static ModSpirit redwind;
    public static ModSpirit romol;
    public static ModSpirit squarefury;
    public static ModSpirit timber;

    public static void preInit() {
        allfire = ModRegistries.spirits.register(
                new SpiritBuilder("allfire")
                        .addActivityPeriod(10, 14)
                        .addTalisman(ModTalismans.combustion)
                        .addTalisman(ModTalismans.heat_wave)
                        .addTalisman(ModTalismans.ignition)
                        .addTalisman(ModTalismans.searing_pulse)
                        .addTalisman(ModTalismans.small_fireball_throw)
                        .addTalisman(ModTalismans.distant_spark)
                        .create()
        );
        arbow = ModRegistries.spirits.register(
                new SpiritBuilder("arbow")
                        .addActivityPeriod(10, 14)
                        .addTalisman(ModTalismans.arrow_throw_talisman)
                        .create()
        );
        blizrabi = ModRegistries.spirits.register(
                new SpiritBuilder("blizrabi")
                        .addActivityPeriod(10, 14)
                        .setPunishment(new BlizrabiPunishment())
                        .addTalisman(ModTalismans.crystalline_road)
                        .addTalisman(ModTalismans.snowball_throw)
                        .addTalisman(ModTalismans.obsidian_road)
                        .create()
        );
        endererer = ModRegistries.spirits.register(
                new SpiritBuilder("endererer")
                        .addActivityPeriod(10, 14)
                        .addTalisman(ModTalismans.escape_plan)
                        .addTalisman(ModTalismans.pearl_crumbs)
                        .addTalisman(ModTalismans.blink)
                        .addTalisman(ModTalismans.warp)
                        .addTalisman(ModTalismans.overblink)
                        .addTalisman(ModTalismans.overwarp)
                        .addTalisman(ModTalismans.surface_blink)
                        .create()
        );
        nefir = ModRegistries.spirits.register(
                new SpiritBuilder("nefir")
                        .addActivityPeriod(10, 14)
                        .setPunishment(new NefirPunishment())
                        .addTalisman(ModTalismans.molten_skin)
                        .addTalisman(ModTalismans.infernal_touch)
                        .addTalisman(ModTalismans.hellisphere)
                        .addTalisman(ModTalismans.nether_surge)
                        .addTalisman(ModTalismans.piercing_inferno)
                        .create()
        );
        redwind = ModRegistries.spirits.register(
                new SpiritBuilder("redwind")
                        .addActivityPeriod(6, 18)
                        .addTalisman(ModTalismans.fall_negation)
                        .addTalisman(ModTalismans.toadic_jump)
                        .addTalisman(ModTalismans.wild_sprint)
                        .addTalisman(ModTalismans.wind_step)
                        .create()
        );
        romol = ModRegistries.spirits.register(
                new SpiritBuilder("romol")
                        .addActivityPeriod(6, 18)
                        .addTalisman(ModTalismans.crushing_palm)
                        .addTalisman(ModTalismans.stone_fever)
                        .addTalisman(ModTalismans.empower_pickaxe)
                        .addTalisman(ModTalismans.stoneball_throw)
                        .create()
        );
        squarefury = ModRegistries.spirits.register(
                new SpiritBuilder("squarefury")
                        .addActivityPeriod(15, 21)
                        .setPunishment(new SquareFuryPunishment())
                        .addTalisman(ModTalismans.butchering_strike)
                        .addTalisman(ModTalismans.consuming_fury)
                        .addTalisman(ModTalismans.grudge)
                        .addTalisman(ModTalismans.mist_blade)
                        .addTalisman(ModTalismans.night_eye)
                        .create()
        );
        timber = ModRegistries.spirits.register(
                new SpiritBuilder("timber")
                        .addActivityPeriod(6, 12)
                        .addTalisman(ModTalismans.fell_tree)
                        .addTalisman(ModTalismans.wooden_punch)

                        .addTalisman(ModTalismans.wall_slip)
                        .create()
        );
    }
}