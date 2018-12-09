package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.lib.TimePeriod;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModSpirit extends IForgeRegistryEntry.Impl<ModSpirit> {
    private final List<TimePeriod> activityPeriods;

    public ModSpirit(String name, List<TimePeriod> activityPeriods) {
        this.activityPeriods = Collections.unmodifiableList(new ArrayList<>(activityPeriods));
        setRegistryName(ResourceNamer.getFullName("spirit", name));
    }

    public boolean isActive(World world) {
        int timeOfDay = (int) (world.getWorldTime() % 24000);
        for (TimePeriod period : activityPeriods)
            if (period.isDayTimeInRange(timeOfDay))
                return true;
        return false;
    }
}
