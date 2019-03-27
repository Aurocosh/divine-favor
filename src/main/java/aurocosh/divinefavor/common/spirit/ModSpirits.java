package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.favor.ModFavors;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.spirit.punishment.*;

public final class ModSpirits {
    public static ModSpirit arbow;
    public static ModSpirit blizrabi;
    public static ModSpirit endererer;
    public static ModSpirit loon;
    public static ModSpirit neblaze;
    public static ModSpirit redwind;
    public static ModSpirit romol;
    public static ModSpirit squarefury;
    public static ModSpirit timber;

    public static void preInit() {
        arbow = new SpiritBuilder("arbow")
                .addActivityPeriod(ConfigGeneral.spiritActivityPeriods.arbow)
                .addFavor(ModFavors.arbow)
                .create();

        blizrabi = new SpiritBuilder("blizrabi")
                .addActivityPeriod(ConfigGeneral.spiritActivityPeriods.blizrabi)
                .setPunishment(new BlizrabiPunishment())
                .addFavor(ModFavors.blizrabi)
                .create();

        endererer = new SpiritBuilder("endererer")
                .addActivityPeriod(ConfigGeneral.spiritActivityPeriods.endererer)
                .setPunishment(new EnderererPunishment())
                .addFavor(ModFavors.endererer)
                .create();
        loon = new SpiritBuilder("loon")
                .addActivityPeriod(ConfigGeneral.spiritActivityPeriods.loon)
                .addFavor(ModFavors.loon)
                .create();

        neblaze = new SpiritBuilder("neblaze")
                .addActivityPeriod(ConfigGeneral.spiritActivityPeriods.neblaze)
                .setPunishment(new NeblazePunishment())
                .addFavor(ModFavors.neblaze)
                .create();

        redwind = new SpiritBuilder("redwind")
                .addActivityPeriod(ConfigGeneral.spiritActivityPeriods.redwind)
                .addFavor(ModFavors.redwind)
                .create();

        romol = new SpiritBuilder("romol")
                .addActivityPeriod(ConfigGeneral.spiritActivityPeriods.romol)
                .addFavor(ModFavors.romol)
                .create();

        squarefury = new SpiritBuilder("squarefury")
                .addActivityPeriod(ConfigGeneral.spiritActivityPeriods.squarefury)
                .setPunishment(new SquareFuryPunishment())
                .addFavor(ModFavors.squarefury)
                .create();

        timber = new SpiritBuilder("timber")
                .addActivityPeriod(ConfigGeneral.spiritActivityPeriods.timber)
                .setPunishment(new TimberPunishment())
                .addFavor(ModFavors.timber)
                .create();
    }
}