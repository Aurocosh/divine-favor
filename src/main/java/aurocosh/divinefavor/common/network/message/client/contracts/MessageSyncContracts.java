package aurocosh.divinefavor.common.network.message.client.contracts;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.contracts.ContractsData;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncContracts extends NetworkWrappedClientMessage {
    private static String TAG_CONTRACTS = "Contracts";

    public NBTTagCompound compound;

    public MessageSyncContracts() {
    }

    public MessageSyncContracts(ContractsData data) {
        compound = data.getStackHandler().serializeNBT();
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        ContractsData contractsData = PlayerData.get(player).getContractsData();
        contractsData.getStackHandler().deserializeNBT(compound);
    }
}