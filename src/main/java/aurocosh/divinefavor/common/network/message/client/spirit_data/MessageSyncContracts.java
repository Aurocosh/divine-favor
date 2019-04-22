package aurocosh.divinefavor.common.network.message.client.spirit_data;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData;
import aurocosh.divinefavor.common.network.base.WrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncContracts extends WrappedClientMessage {
    private static String TAG_CONTRACTS = "Contracts";

    public NBTTagCompound tag;

    public MessageSyncContracts() {
    }

    public MessageSyncContracts(SpiritData data) {
        tag = getNbtTagCompound(data);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        SpiritData data = PlayerData.get(player).getSpiritData();
        setDataFromNBT(data, tag);
    }

    public static NBTTagCompound getNbtTagCompound(SpiritData instance) {
        final NBTTagCompound tag = new NBTTagCompound();
        tag.setTag(TAG_CONTRACTS, instance.serializeContracts());
        return tag;
    }

    public static void setDataFromNBT(SpiritData instance, NBTTagCompound nbt) {
        instance.deserializeContracts(nbt.getCompoundTag(TAG_CONTRACTS));
    }
}