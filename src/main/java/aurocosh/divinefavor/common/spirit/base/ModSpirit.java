package aurocosh.divinefavor.common.spirit.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.lib.TimePeriod;
import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModSpirit extends IForgeRegistryEntry.Impl<ModSpirit> implements IIndexedEntry {
    private final int id;
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
        id = ModMappers.spirits.register(this);
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

    @Override
    public int getId() {
        return id;
    }
}
