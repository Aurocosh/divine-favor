package aurocosh.divinefavor.common.spirit.base;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.config.SpiritConfig;
import aurocosh.divinefavor.common.constants.ConstResources;
import aurocosh.divinefavor.common.core.ResourceNamer;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData;
import aurocosh.divinefavor.common.global.dayClock.DayClock;
import aurocosh.divinefavor.common.lib.TimePeriod;
import aurocosh.divinefavor.common.lib.interfaces.IIndexedEntry;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncFavor;
import aurocosh.divinefavor.common.registry.ModRegistries;
import aurocosh.divinefavor.common.registry.mappers.ModMappers;
import aurocosh.divinefavor.common.util.UtilDayTime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.List;

public class ModSpirit extends IForgeRegistryEntry.Impl<ModSpirit> implements IIndexedEntry {
    private final int id;
    private final String name;
    private final Item offering;
    private final int offeringCount;
    private final TimePeriod activityPeriod;
    private final SpiritPunishment punishment;

    private final ResourceLocation icon;
    private final ResourceLocation symbol;

    private final int favorMin;
    private final int favorMax;
    private final int favorRegen;

    public ModSpirit(String name, SpiritPunishment punishment, SpiritConfig config) {
        this.name = name;
        offering = Item.getByNameOrId(config.offering);
        if (offering == null)
            DivineFavor.logger.error("Spirit error: " + name + ". Offering not found:" + config.offering);
        offeringCount = config.offeringCount;
        activityPeriod = UtilDayTime.fromHours(config.activityPeriod.start, config.activityPeriod.stop);
        this.punishment = punishment;
        favorMin = config.startingPlayerFavorMinimum;
        favorMax = config.startingPlayerFavorMaximum;
        favorRegen = config.startingPlayerFavorRegen;
        setRegistryName(ResourceNamer.getFullName("spirit", name));
        icon = new ResourceLocation(ConstResources.PREFIX_SPIRIT_ICONS + name + ".png");
        symbol = new ResourceLocation(ConstResources.PREFIX_SPIRIT_SYMBOLS + name + ".png");

        id = ModMappers.spirits.register(this);
        ModRegistries.spirits.register(this);
        registerActivityPeriod();
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

    public SpiritPunishment getPunishment() {
        return punishment;
    }

    public ResourceLocation getIcon() {
        return icon;
    }

    public ResourceLocation getSymbol() {
        return symbol;
    }

    public int getFavorMin() {
        return favorMin;
    }

    public int getFavorMax() {
        return favorMax;
    }

    public int getFavorRegen() {
        return favorRegen;
    }

    public boolean isActive() {
        int timeOfDay = DayClock.getTime();
        return activityPeriod.isDayTimeInRange(timeOfDay);
    }

    private void becameActive() {
        List<EntityPlayerMP> playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
        if (playerList.isEmpty())
            return;
        if (playerList.get(0).world.isRemote)
            return;
        regenerateFavor(playerList);
        informActivity(playerList);
    }

    private void regenerateFavor(List<EntityPlayerMP> playerList) {
        for (EntityPlayerMP player : playerList) {
            SpiritData spiritData = PlayerData.get(player).getSpiritData();
            if (spiritData.regenerateFavor(id))
                new MessageSyncFavor(this, spiritData).sendTo(player);
        }
    }

    private void informActivity(List<EntityPlayerMP> playerList) {
        String message = "Spirit " + name + " became active";
        for (EntityPlayer player : playerList)
            player.sendMessage(new TextComponentString(message));
    }

    private void becameInactive() {
        List<EntityPlayerMP> playerList = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();
        if (playerList.isEmpty())
            return;
        if (playerList.get(0).world.isRemote)
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
