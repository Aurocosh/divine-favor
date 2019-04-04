package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.config.common.ConfigSpirits;
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
        arbow = new SpiritBuilder("arbow", ConfigSpirits.arbow)
                .addFavor(ModFavors.arbow)
                .create();

        blizrabi = new SpiritBuilder("blizrabi", ConfigSpirits.blizrabi)
                .setPunishment(new BlizrabiPunishment())
                .addFavor(ModFavors.blizrabi)
                .create();

        endererer = new SpiritBuilder("endererer", ConfigSpirits.endererer)
                .setPunishment(new EnderererPunishment())
                .addFavor(ModFavors.endererer)
                .create();
        loon = new SpiritBuilder("loon", ConfigSpirits.loon)
                .addFavor(ModFavors.loon)
                .create();

        neblaze = new SpiritBuilder("neblaze", ConfigSpirits.neblaze)
                .setPunishment(new NeblazePunishment())
                .addFavor(ModFavors.neblaze)
                .create();

        redwind = new SpiritBuilder("redwind", ConfigSpirits.redwind)
                .addFavor(ModFavors.redwind)
                .create();

        romol = new SpiritBuilder("romol", ConfigSpirits.romol)
                .addFavor(ModFavors.romol)
                .create();

        squarefury = new SpiritBuilder("squarefury", ConfigSpirits.squarefury)
                .setPunishment(new SquareFuryPunishment())
                .addFavor(ModFavors.squarefury)
                .create();

        timber = new SpiritBuilder("timber", ConfigSpirits.timber)
                .setPunishment(new TimberPunishment())
                .addFavor(ModFavors.timber)
                .create();
    }
}