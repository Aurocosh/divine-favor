package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.favor.ModFavors;
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
                .addFavor(ModFavors.allfire)
                .create();

        arbow = new SpiritBuilder("arbow")
                .addActivityPeriod(10, 14)
                .addFavor(ModFavors.arbow)
                .create();

        blizrabi = new SpiritBuilder("blizrabi")
                .addActivityPeriod(10, 14)
                .setPunishment(new BlizrabiPunishment())
                .addFavor(ModFavors.blizrabi)
                .create();

        endererer = new SpiritBuilder("endererer")
                .addActivityPeriod(10, 14)
                .setPunishment(new EnderererPunishment())
                .addFavor(ModFavors.endererer)
                .create();

        nefir = new SpiritBuilder("nefir")
                .addActivityPeriod(10, 14)
                .setPunishment(new NefirPunishment())
                .addFavor(ModFavors.nefir)
                .create();

        redwind = new SpiritBuilder("redwind")
                .addActivityPeriod(6, 18)
                .addFavor(ModFavors.redwind)
                .create();

        romol = new SpiritBuilder("romol")
                .addActivityPeriod(6, 18)
                .addFavor(ModFavors.romol)
                .create();

        squarefury = new SpiritBuilder("squarefury")
                .addActivityPeriod(15, 21)
                .setPunishment(new SquareFuryPunishment())
                .addFavor(ModFavors.squarefury)
                .create();

        timber = new SpiritBuilder("timber")
                .addActivityPeriod(6, 12)
                .setPunishment(new TimberPunishment())
                .addFavor(ModFavors.timber)
                .create();
    }
}