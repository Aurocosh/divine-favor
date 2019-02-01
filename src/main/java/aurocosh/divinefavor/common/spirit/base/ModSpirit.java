package aurocosh.divinefavor.common.spirit.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.global.dayClock.DayClock;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.lib.TimePeriod;
import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.spirit.activity.SpiritActivityHandler;
import aurocosh.divinefavor.common.spirit.activity.SpiritActivityMessage;
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
    private final List<Integer> activeInDimensions;
    private final SpiritActivityHandler spiritBecameActiveHandler;
    private final SpiritActivityHandler spiritBecameInactiveHandler;
    private final SpiritPunishment punishment;

    public ModSpirit(String name, List<TimePeriod> activityPeriods, List<ItemSpellTalisman> talismans, List<Integer> activeInDimensions, SpiritPunishment punishment) {
        this.name = name;
        this.activityPeriods = Collections.unmodifiableList(new ArrayList<>(activityPeriods));
        this.talismans = talismans;
        this.activeInDimensions = activeInDimensions;
        this.punishment = punishment;
        setRegistryName(ResourceNamer.getFullName("spirit", name));
        id = ModMappers.spirits.register(this);

        spiritBecameActiveHandler = new SpiritActivityHandler(this);
        spiritBecameActiveHandler.addActivity(new SpiritActivityMessage("Spirit " + name + " became active"));

        spiritBecameInactiveHandler = new SpiritActivityHandler(this);
        spiritBecameInactiveHandler.addActivity(new SpiritActivityMessage("Spirit " + name + " became inactive"));

        registerActivityPeriods();
        ModRegistries.spirits.register(this);
    }

    public String getName() {
        return name;
    }

    public void addTalisman(ItemSpellTalisman talisman) {
        talismans.add(talisman);
    }

    public List<ItemSpellTalisman> getTalismans() {
        return Collections.unmodifiableList(talismans);
    }

    public List<Integer> getActiveInDimensions() {
        return activeInDimensions;
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

    private void registerActivityPeriods() {
        for (TimePeriod activityPeriod : activityPeriods) {
            DayClock.addAlarm(activityPeriod.getStart(), spiritBecameActiveHandler::execute, true);
            DayClock.addAlarm(activityPeriod.getStop(), spiritBecameInactiveHandler::execute, true);
        }
    }

    @Override
    public int getId() {
        return id;
    }
}
