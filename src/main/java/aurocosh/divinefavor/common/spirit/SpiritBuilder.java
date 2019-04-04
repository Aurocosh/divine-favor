package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.SpiritConfig;
import aurocosh.divinefavor.common.config.TimePeriodConfig;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.lib.TimePeriod;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.spirit.base.SpiritPunishment;
import aurocosh.divinefavor.common.util.UtilDayTime;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpiritBuilder {
    private String name;
    private Item offering;
    private int offeringCount;
    private List<ModFavor> favors;
    private TimePeriod activityPeriod;
    private SpiritPunishment punishment;

    public SpiritBuilder(String name, SpiritConfig config) {
        this.name = name;
        offering = Item.getByNameOrId(config.offering);
        if (offering == null)
            DivineFavor.logger.error("Spirit error: " + name + ". Offering not found:" + offering);
        offeringCount = config.offeringCount;
        favors = new ArrayList<>();
        punishment = new SpiritPunishment();
        TimePeriodConfig timePeriod = config.activityPeriod;
        this.activityPeriod = UtilDayTime.fromHours(timePeriod.start, timePeriod.stop);
    }

    public SpiritBuilder addFavor(ModFavor favor) {
        favors.add(favor);
        return this;
    }

    public SpiritBuilder setPunishment(SpiritPunishment punishment) {
        this.punishment = punishment;
        return this;
    }

    public ModSpirit create() {
        return new ModSpirit(name, favors, activityPeriod, Collections.singletonList(0), punishment, offering, offeringCount);
    }
}
