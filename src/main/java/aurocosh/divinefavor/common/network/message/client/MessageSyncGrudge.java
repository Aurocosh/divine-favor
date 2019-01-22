package aurocosh.divinefavor.common.network.message.client;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.grudge.GrudgeData;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        GrudgeData grudgeData = PlayerData.get(player).getGrudgeData();
        grudgeData.setMobTypeId(mobTypeId);
    }
}
