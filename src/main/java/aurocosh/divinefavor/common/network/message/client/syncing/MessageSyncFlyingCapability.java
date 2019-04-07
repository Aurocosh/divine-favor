package aurocosh.divinefavor.common.network.message.client.syncing;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncFlyingCapability extends NetworkWrappedClientMessage {
    public boolean canFly;

    public MessageSyncFlyingCapability() {
    }

    public MessageSyncFlyingCapability(boolean canFly) {
        this.canFly = canFly;
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        player.capabilities.allowFlying = canFly;
        player.capabilities.isFlying = canFly;
    }
}
