package aurocosh.divinefavor.common.config.entries;

import net.minecraftforge.common.config.Config;

public class SpiritConfig {
    @Config.Name("Offering")
    public String offering;
    @Config.Name("Offering count")
    public int offeringCount;
    @Config.Name("Activity period")
    public TimePeriodConfig activityPeriod;

    @Config.Name("Players starting favor minimum")
    public int startingPlayerFavorMinimum = 0;
    @Config.Name("Players starting favor maximum")
    public int startingPlayerFavorMaximum = 0;
    @Config.Name("Players starting favor regeneration")
    public int startingPlayerFavorRegen = 0;

    public SpiritConfig(String offering, int offeringCount, TimePeriodConfig activityPeriod, int startingPlayerFavorMinimum, int startingPlayerFavorMaximum, int startingPlayerFavorRegen) {
        this.offering = offering;
        this.offeringCount = offeringCount;
        this.activityPeriod = activityPeriod;
        this.startingPlayerFavorMinimum = startingPlayerFavorMinimum;
        this.startingPlayerFavorMaximum = startingPlayerFavorMaximum;
        this.startingPlayerFavorRegen = startingPlayerFavorRegen;
    }
}
