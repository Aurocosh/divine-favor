package aurocosh.divinefavor.common.network.message.client;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.custom_data.player.PlayerData;
import aurocosh.divinefavor.common.custom_data.player.data.focused_fury.FocusedFuryData;
import aurocosh.divinefavor.common.network.base.NetworkWrappedClientMessage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncFury extends NetworkWrappedClientMessage {
    public int mobTypeId;

	public MessageSyncFury() { }

	public MessageSyncFury(int mobTypeId) {
	    this.mobTypeId = mobTypeId;
	}

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        FocusedFuryData furyData = PlayerData.get(player).getFocusedFuryData();
        furyData.setMobTypeId(mobTypeId);
    }
}
