package aurocosh.divinefavor.common.network.message.client;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import aurocosh.divinefavor.common.custom_data.player.grudge.IGrudgeHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.custom_data.player.grudge.GrudgeDataHandler.CAPABILITY_GRUDGE;

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
}
