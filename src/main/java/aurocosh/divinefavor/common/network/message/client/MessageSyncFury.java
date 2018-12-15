package aurocosh.divinefavor.common.network.message.client;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkClientMessage;
import aurocosh.divinefavor.common.network.common.NetworkHandler;
import aurocosh.divinefavor.common.player_data.focused_fury.IFocusedFuryHandler;
import aurocosh.divinefavor.common.player_data.grudge.IGrudgeHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.player_data.focused_fury.FocusedFuryDataHandler.CAPABILITY_FOCUSED_FURY;
import static aurocosh.divinefavor.common.player_data.grudge.GrudgeDataHandler.CAPABILITY_GRUDGE;

public class MessageSyncFury extends NetworkClientMessage {
    public int mobTypeId;

	public MessageSyncFury() { }

	public MessageSyncFury(int mobTypeId) {
	    this.mobTypeId = mobTypeId;
	}

    @Override
    @SideOnly(Side.CLIENT)
    protected void handleSafe() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        IFocusedFuryHandler furyHandler = player.getCapability(CAPABILITY_FOCUSED_FURY, null);
        assert furyHandler != null;
        furyHandler.setMobTypeId(mobTypeId);
    }

    public static void sync(EntityPlayer player, int mobTypeId) {
        MessageSyncFury message = new MessageSyncFury(mobTypeId);
        NetworkHandler.INSTANCE.sendTo(message, (EntityPlayerMP) player);
    }
}
