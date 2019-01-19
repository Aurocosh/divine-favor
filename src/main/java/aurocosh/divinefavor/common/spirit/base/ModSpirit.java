package aurocosh.divinefavor.common.spirit.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.item.talismans.base.spell.ItemSpellTalisman;
import aurocosh.divinefavor.common.lib.TimePeriod;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModSpirit extends IForgeRegistryEntry.Impl<ModSpirit> {
    private final String name;
    private final List<TimePeriod> activityPeriods;
    private final List<ItemSpellTalisman> talismans;

    private final SpiritPunishment punishment;

    public ModSpirit(String name, List<TimePeriod> activityPeriods, List<ItemSpellTalisman> talismans, SpiritPunishment punishment) {
        this.name = name;
        this.activityPeriods = Collections.unmodifiableList(new ArrayList<>(activityPeriods));
        this.talismans = talismans;
        this.punishment = punishment;
        setRegistryName(ResourceNamer.getFullName("spirit", name));
    }

    public String getName() {
        return name;
    }

    public void addTalisman(ItemSpellTalisman talisman){
        talismans.add(talisman);
    }

    public List<ItemSpellTalisman> getTalismans(){
        return Collections.unmodifiableList(talismans);
    }

    public SpiritPunishment getPunishment() {
        return punishment;
    }

    public boolean isActive(World world) {
        int timeOfDay = (int) (world.getWorldTime() % 24000);
        for (TimePeriod period : activityPeriods)
            if (period.isDayTimeInRange(timeOfDay))
                return true;
        return false;
    }
}
