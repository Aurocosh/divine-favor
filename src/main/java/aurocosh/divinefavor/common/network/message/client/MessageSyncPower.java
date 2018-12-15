package aurocosh.divinefavor.common.network.message.client;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkClientMessage;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.tool.IEnergyContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncPower extends NetworkWrappedClientMessage {
    public int energy;

    public MessageSyncPower() {
    }

    public MessageSyncPower(int energy) {
        this.energy = energy;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        if (player.openContainer instanceof IEnergyContainer)
            ((IEnergyContainer) player.openContainer).syncPower(energy);
    }
}
