package aurocosh.divinefavor.common.network.message.client;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkClientMessage;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.player_data.grudge.IGrudgeHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.player_data.grudge.GrudgeDataHandler.CAPABILITY_GRUDGE;

public class MessageSyncGrudge extends NetworkWrappedClientMessage {
    public int mobTypeId;

	public MessageSyncGrudge() { }

	public MessageSyncGrudge(int mobTypeId) {
	    this.mobTypeId = mobTypeId;
	}

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        IGrudgeHandler grudgeHandler = player.getCapability(CAPABILITY_GRUDGE, null);
        assert grudgeHandler != null;
        grudgeHandler.setMobTypeId(mobTypeId);
    }

    public static void sync(EntityPlayer player, int mobTypeId) {
        MessageSyncGrudge message = new MessageSyncGrudge(mobTypeId);
        NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) player);
    }
}
