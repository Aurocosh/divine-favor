package aurocosh.divinefavor.common.spirit;

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
        loon = new SpiritBuilder("loon")
                .addActivityPeriod(10, 14)
                .addFavor(ModFavors.loon)
                .create();

        neblaze = new SpiritBuilder("neblaze")
                .addActivityPeriod(10, 14)
                .setPunishment(new NeblazePunishment())
                .addFavor(ModFavors.neblaze)
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