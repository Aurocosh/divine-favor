package aurocosh.divinefavor.common.config.entries;

import net.minecraftforge.common.config.Config;

public class FavorConfig {
    @Config.Name("Players starting favor minimum")
    public int startingPlayerFavorMinimum = 0;
    @Config.Name("Players starting favor maximum")
    public int startingPlayerFavorMaximum = 0;
    @Config.Name("Players starting favor regeneration")
    public int startingPlayerFavorRegen = 0;

    public FavorConfig(int startingPlayerFavorMinimum, int startingPlayerFavorMaximum, int startingPlayerFavorRegen) {
        this.startingPlayerFavorMinimum = startingPlayerFavorMinimum;
        this.startingPlayerFavorMaximum = startingPlayerFavorMaximum;
        this.startingPlayerFavorRegen = startingPlayerFavorRegen;
    }
}
