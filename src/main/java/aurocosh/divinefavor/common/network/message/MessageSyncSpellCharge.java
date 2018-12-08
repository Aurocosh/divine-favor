package aurocosh.divinefavor.common.network.message;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.ClientTickHandler;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.network.base.NetworkAutoMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageSyncSpellCharge extends NetworkAutoMessage {

    public int favorId;
	public int favorCount;

	public MessageSyncSpellCharge() { }

	public MessageSyncSpellCharge(int favorId, int count) {
	    this.favorId = favorId;
	    this.favorCount = count;
	}

	@Override
	public IMessage handleMessage(MessageContext context) {
        ClientTickHandler.scheduledActions.add(() -> {
            PlayerDataHandler.PlayerData data = PlayerDataHandler.get(DivineFavor.proxy.getClientPlayer());
            data.setSpellCharge(favorId, favorCount);
        });
        return null;
    }
}
