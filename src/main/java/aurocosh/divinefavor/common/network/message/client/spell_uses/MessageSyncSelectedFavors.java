package aurocosh.divinefavor.common.network.message.client.spell_uses;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.FavorData;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Set;

public class MessageSyncSelectedFavors extends NetworkWrappedClientMessage {
    private static String TAG_FAVOR_IDS = "FavorIds";
    private static String TAG_FAVOR_VALUES = "FavorValues";

    public NBTTagCompound tag;

    public MessageSyncSelectedFavors() {
    }

    public MessageSyncSelectedFavors(FavorData data, Set<Integer> favorsToSync) {
        tag = getNbtTagCompound(data, favorsToSync);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        FavorData data = PlayerData.get(player).getFavorData();
        setDataFromNBT(data, tag);
    }

    public static NBTTagCompound getNbtTagCompound(FavorData instance, Set<Integer> favorsToSync) {
        final NBTTagCompound tag = new NBTTagCompound();
        int[] favorsIds = new int[favorsToSync.size()];
        int[] favorsValues = new int[favorsToSync.size()];
        int i = 0;
        for (Integer favorId : favorsToSync) {
            favorsIds[i] = favorId;
            favorsValues[i] = instance.getFavor(favorId);
        }
        tag.setIntArray(TAG_FAVOR_IDS, favorsIds);
        tag.setIntArray(TAG_FAVOR_VALUES, favorsValues);
        return tag;
    }

    public static void setDataFromNBT(FavorData instance, NBTTagCompound nbt) {
        int[] favorIds = nbt.getIntArray(TAG_FAVOR_IDS);
        int[] favorValues = nbt.getIntArray(TAG_FAVOR_VALUES);
        for (int i = 0; i < favorIds.length; i++)
            instance.setFavor(favorIds[i], favorValues[i]);
    }
}