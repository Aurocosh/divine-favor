package aurocosh.divinefavor.common.spirit.base;

import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.global.dayClock.DayClock;
import aurocosh.divinefavor.common.lib.TimePeriod;
import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSelectedFavors;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ModSpirit extends IForgeRegistryEntry.Impl<ModSpirit> implements IIndexedEntry {
    private final int id;
    private final String name;
    private final Item offering;
    private final int offeringCount;
    private final List<ModFavor> favors;
    private final TimePeriod activityPeriod;
    private final List<Integer> activeInDimensions;
    private final SpiritPunishment punishment;

    public ModSpirit(String name, List<ModFavor> favors, TimePeriod activityPeriod, List<Integer> activeInDimensions, SpiritPunishment punishment, Item offering, int offeringCount) {
        this.name = name;
        this.offering = offering;
        this.offeringCount = offeringCount;
        this.favors = favors;
        this.activityPeriod = activityPeriod;
        this.activeInDimensions = activeInDimensions;
        this.punishment = punishment;
        setRegistryName(ResourceNamer.getFullName("spirit", name));
        id = ModMappers.spirits.register(this);

        registerActivityPeriod();
        ModRegistries.spirits.register(this);
    }

    public String getName() {
        return name;
    }

    public Item getOffering() {
        return offering;
    }

    public int getOfferingCount() {
        return offeringCount;
    }

    public TimePeriod getActivityPeriod() {
        return activityPeriod;
    }

    public List<Integer> getActiveInDimensions() {
        return activeInDimensions;
    }

    public SpiritPunishment getPunishment() {
        return punishment;
    }

    public boolean isActive() {
        int timeOfDay = DayClock.getTime();
        return activityPeriod.isDayTimeInRange(timeOfDay);
    }

    private void becameActive() {
        List<EntityPlayerMP> playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
        if(playerList.isEmpty())
            return;
        if(playerList.get(0).world.isRemote)
            return;
        regenerateFavor(playerList);
        informActivity(playerList);
    }

    private void regenerateFavor(List<EntityPlayerMP> playerList) {
        for (EntityPlayerMP player : playerList) {
            Set<Integer> favorsToSync = new HashSet<>();
            FavorData favorData = PlayerData.get(player).getFavorData();
            for (ModFavor favor : favors)
                if (favorData.regenerateFavor(favor.getId()))
                    favorsToSync.add(favor.getId());
            if (!favorsToSync.isEmpty())
                new MessageSyncSelectedFavors(favorData, favorsToSync).sendTo(player);
        }
    }

    private void informActivity(List<EntityPlayerMP> playerList) {
        String message = "Spirit " + name + " became active";
        for (EntityPlayer player : playerList)
            player.sendMessage(new TextComponentString(message));
    }

    private void becameInactive() {
        List<EntityPlayerMP> playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
        if(playerList.isEmpty())
            return;
        if(playerList.get(0).world.isRemote)
            return;
        informInactivity(playerList);
    }

    private void informInactivity(List<EntityPlayerMP> playerList) {
        String message = "Spirit " + name + " became inactive";
        for (EntityPlayer player : playerList)
            player.sendMessage(new TextComponentString(message));
    }

    private void registerActivityPeriod() {
        DayClock.addAlarm(activityPeriod.getStart(), this::becameActive, true);
        DayClock.addAlarm(activityPeriod.getStop(), this::becameInactive, true);
    }

    @Override
    public int getId() {
        return id;
    }
}
