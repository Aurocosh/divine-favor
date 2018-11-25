package aurocosh.divinefavor.common.network.message;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.ClientTickHandler;
import aurocosh.divinefavor.common.tool.IEnergyContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.arl.network.NetworkMessage;

public class MessageSyncPower extends NetworkMessage {
    public int energy;

    public MessageSyncPower() { }
    public MessageSyncPower(int energy) {
        this.energy = energy;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage handleMessage(MessageContext context) {
        ClientTickHandler.scheduledActions.add(() -> {
            EntityPlayer player = DivineFavor.proxy.getClientPlayer();
            if (player.openContainer instanceof IEnergyContainer)
                ((IEnergyContainer) player.openContainer).syncPower(energy);
        });

        return null;
    }
}
