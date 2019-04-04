package aurocosh.divinefavor.common.config;

public class SpiritConfig {
    public String offering;
    public int offeringCount;
    public TimePeriodConfig activityPeriod;

    public SpiritConfig(String offering, int offeringCount, TimePeriodConfig activityPeriod) {
        this.offering = offering;
        this.offeringCount = offeringCount;
        this.activityPeriod = activityPeriod;
    }
}
