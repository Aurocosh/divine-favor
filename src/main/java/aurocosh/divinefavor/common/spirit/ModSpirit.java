package aurocosh.divinefavor.common.spirit;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.item.talismans.ItemTalisman;
import aurocosh.divinefavor.common.lib.TimePeriod;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModSpirit extends IForgeRegistryEntry.Impl<ModSpirit> {
    private final String name;
    private final List<TimePeriod> activityPeriods;
    private final List<ItemTalisman> talismans;

    public ModSpirit(String name, List<TimePeriod> activityPeriods, List<ItemTalisman> talismans) {
        this.name = name;
        this.activityPeriods = Collections.unmodifiableList(new ArrayList<>(activityPeriods));
        this.talismans = talismans;
        setRegistryName(ResourceNamer.getFullName("spirit", name));
    }

    public String getName() {
        return name;
    }

    public void addTalisman(ItemTalisman talisman){
        talismans.add(talisman);
    }

    public List<ItemTalisman> getTalismans(){
        return Collections.unmodifiableList(talismans);
    }

    public boolean isActive(World world) {
        int timeOfDay = (int) (world.getWorldTime() % 24000);
        for (TimePeriod period : activityPeriods)
            if (period.isDayTimeInRange(timeOfDay))
                return true;
        return false;
    }
}
