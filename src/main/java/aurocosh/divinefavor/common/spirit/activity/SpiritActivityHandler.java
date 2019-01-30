package aurocosh.divinefavor.common.spirit.activity;

import aurocosh.divinefavor.common.block.medium.TileMedium;
import aurocosh.divinefavor.common.custom_data.world.WorldData;
import aurocosh.divinefavor.common.custom_data.world.data.altars.AltarsData;
import aurocosh.divinefavor.common.item.contract.ItemContract;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilWorld;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.management.PlayerList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

import java.util.*;

public class SpiritActivityHandler {
    private final ModSpirit spirit;
    private final List<SpiritActivity> activities;

    public SpiritActivityHandler(ModSpirit spirit) {
        this.spirit = spirit;
        this.activities = new ArrayList<>();
    }

    public void addActivity(SpiritActivity activity) {
        activities.add(activity);
    }

    public void execute() {
        Set<EntityPlayer> contractors = new HashSet<>();
        List<Integer> dimensions = spirit.getActiveInDimensions();
        for (Integer dimension : dimensions) {
            WorldServer world = DimensionManager.getWorld(dimension);
            List<EntityPlayer> players = getValidPlayersFromAltars(world);
            contractors.addAll(players);
        }

        for (SpiritActivity activityPlayer : activities)
            activityPlayer.execute(spirit, contractors);
    }

    private List<EntityPlayer> getValidPlayersFromAltars(World world) {
        List<EntityPlayer> contractors = new ArrayList<>();
        AltarsData altarData = WorldData.get(world).getAltarData();
        Set<BlockPos> altarLocations = altarData.getAltarLocations(spirit);

        for (BlockPos altarLocation : altarLocations) {
            TileEntity entity = world.getTileEntity(altarLocation);
            if (!(entity instanceof TileMedium))
                continue;
            TileMedium medium = (TileMedium) entity;
            if (!medium.isMultiblockValid())
                continue;
            List<ItemStack> contracts = medium.getValidContracts();
            List<EntityPlayer> players = getPlayersFromContracts(world, contracts);
            contractors.addAll(players);
        }
        return contractors;
    }

    private List<EntityPlayer> getPlayersFromContracts(World world, List<ItemStack> contracts) {
        List<EntityPlayer> contractors = new ArrayList<>();
        PlayerList playerList = UtilWorld.getPlayerList(world);
        for (ItemStack contract : contracts) {
            UUID uuid = ItemContract.getPlayerId(contract);
            EntityPlayer player;
            if (uuid == null)
                continue;
            player = playerList.getPlayerByUUID(uuid);
            //noinspection ConstantConditions
            if (player != null)
                contractors.add(player);
        }
        return contractors;
    }
}
