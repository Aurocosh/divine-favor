package aurocosh.divinefavor.common.config;

import net.minecraftforge.common.config.Config;

public class SpiritConfig {
    @Config.Name("Offering")
    public String offering;
    @Config.Name("Offering count")
    public int offeringCount;
    @Config.Name("Activity period")
    public TimePeriodConfig activityPeriod;

    public SpiritConfig(String offering, int offeringCount, TimePeriodConfig activityPeriod) {
        this.offering = offering;
        this.offeringCount = offeringCount;
        this.activityPeriod = activityPeriod;
    }
}
