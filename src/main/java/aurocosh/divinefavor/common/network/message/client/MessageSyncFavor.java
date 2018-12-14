package aurocosh.divinefavor.common.network.message.client;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.common.network.base.NetworkAutoMessage;
import aurocosh.divinefavor.common.network.base.NetworkClientMessage;
import aurocosh.divinefavor.common.player_data.favor.IFavorHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static aurocosh.divinefavor.common.player_data.favor.FavorDataHandler.CAPABILITY_FAVOR;

public class MessageSyncFavor extends NetworkClientMessage {

    public int favorId;
	public int favorCount;

	public MessageSyncFavor() { }

	public MessageSyncFavor(int favorId, int count) {
	    this.favorId = favorId;
	    this.favorCount = count;
	}

	@Override
    @SideOnly(Side.CLIENT)
    protected void handle() {
        EntityPlayer player = DivineFavor.proxy.getClientPlayer();
        IFavorHandler favorHandler = player.getCapability(CAPABILITY_FAVOR, null);
        assert favorHandler != null;
        favorHandler.setFavor(favorId,favorCount);
    }
}
