package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.config.common.ConfigSpirits;
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
        arbow = new ModSpirit("arbow", new SquareFuryPunishment(), ConfigSpirits.arbow);
        blizrabi = new ModSpirit("blizrabi", new BlizrabiPunishment(), ConfigSpirits.blizrabi);
        endererer = new ModSpirit("endererer", new EnderererPunishment(), ConfigSpirits.endererer);
        loon = new ModSpirit("loon", new LoonPunishment(), ConfigSpirits.loon);
        neblaze = new ModSpirit("neblaze", new NeblazePunishment(), ConfigSpirits.neblaze);
        redwind = new ModSpirit("redwind", new RedwindPunishment(), ConfigSpirits.redwind);
        romol = new ModSpirit("romol", new RomolPunishment(), ConfigSpirits.romol);
        squarefury = new ModSpirit("squarefury", new SquareFuryPunishment(), ConfigSpirits.squarefury);
        timber = new ModSpirit("timber", new TimberPunishment(), ConfigSpirits.timber);
    }
}