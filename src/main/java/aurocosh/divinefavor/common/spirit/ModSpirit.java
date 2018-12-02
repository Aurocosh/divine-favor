package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.constants.LibMisc;
import aurocosh.divinefavor.common.util.helper_classes.TimePeriod;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public class ModSpirit extends IForgeRegistryEntry.Impl<ModSpirit>  {
    private String name;
    private List<TimePeriod> activityPeriods;

    public ModSpirit(String name, List<TimePeriod> activityPeriods) {
        this.name = name;
        this.activityPeriods = activityPeriods;
        setRegistryName(LibMisc.MOD_ID,"spirit." + name);
    }

    public boolean isActive(World world){
        int timeOfDay = (int) (world.getWorldTime() % 24000);
        for (TimePeriod period : activityPeriods)
            if (period.isDayTimeInRange(timeOfDay))
                return true;
        return false;
    }
}
