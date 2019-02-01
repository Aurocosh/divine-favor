package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.item.talismans.spell.common.ModSpellTalismans;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.spirit.punishment.*;

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
        allfire = new SpiritBuilder("allfire")
                .addActivityPeriod(10, 14)
                .setPunishment(new AllfirePunishment())
                .addTalisman(ModSpellTalismans.combustion)
                .addTalisman(ModSpellTalismans.heat_wave)
                .addTalisman(ModSpellTalismans.ignition)
                .addTalisman(ModSpellTalismans.searing_pulse)
                .addTalisman(ModSpellTalismans.small_fireball_throw)
                .addTalisman(ModSpellTalismans.distant_spark)
                .create();

        arbow = new SpiritBuilder("arbow")
                .addActivityPeriod(10, 14)
                .addTalisman(ModSpellTalismans.arrow_throw_talisman)
                .create();

        blizrabi = new SpiritBuilder("blizrabi")
                .addActivityPeriod(10, 14)
                .setPunishment(new BlizrabiPunishment())
                .addTalisman(ModSpellTalismans.crystalline_road)
                .addTalisman(ModSpellTalismans.snowball_throw)
                .addTalisman(ModSpellTalismans.obsidian_road)
                .create();

        endererer = new SpiritBuilder("endererer")
                .addActivityPeriod(10, 14)
                .setPunishment(new EnderererPunishment())
                .addTalisman(ModSpellTalismans.escape_plan)
                .addTalisman(ModSpellTalismans.pearl_crumbs)
                .addTalisman(ModSpellTalismans.blink)
                .addTalisman(ModSpellTalismans.warp)
                .addTalisman(ModSpellTalismans.overblink)
                .addTalisman(ModSpellTalismans.overwarp)
                .addTalisman(ModSpellTalismans.surface_blink)
                .create();

        nefir = new SpiritBuilder("nefir")
                .addActivityPeriod(10, 14)
                .setPunishment(new NefirPunishment())
                .addTalisman(ModSpellTalismans.molten_skin)
                .addTalisman(ModSpellTalismans.infernal_touch)
                .addTalisman(ModSpellTalismans.hellisphere)
                .addTalisman(ModSpellTalismans.nether_surge)
                .addTalisman(ModSpellTalismans.piercing_inferno)
                .create();

        redwind =
                new SpiritBuilder("redwind")
                        .addActivityPeriod(6, 18)
                        .addTalisman(ModSpellTalismans.fall_negation)
                        .addTalisman(ModSpellTalismans.toadic_jump)
                        .addTalisman(ModSpellTalismans.wild_sprint)
                        .addTalisman(ModSpellTalismans.wind_step)
                        .create();

        romol = new SpiritBuilder("romol")
                .addActivityPeriod(6, 18)
                .addTalisman(ModSpellTalismans.crushing_palm)
                .addTalisman(ModSpellTalismans.stone_fever)
                .addTalisman(ModSpellTalismans.empower_pickaxe)
                .addTalisman(ModSpellTalismans.stoneball_throw)
                .create();

        squarefury = new SpiritBuilder("squarefury")
                .addActivityPeriod(15, 21)
                .setPunishment(new SquareFuryPunishment())
                .addTalisman(ModSpellTalismans.butchering_strike)
                .addTalisman(ModSpellTalismans.consuming_fury)
                .addTalisman(ModSpellTalismans.grudge)
                .addTalisman(ModSpellTalismans.mist_blade)
                .addTalisman(ModSpellTalismans.night_eye)
                .create();

        timber = new SpiritBuilder("timber")
                .addActivityPeriod(6, 12)
                .setPunishment(new TimberPunishment())
                .addTalisman(ModSpellTalismans.fell_tree)
                .addTalisman(ModSpellTalismans.wooden_punch)

                .addTalisman(ModSpellTalismans.wall_slip)
                .create();

    }
}