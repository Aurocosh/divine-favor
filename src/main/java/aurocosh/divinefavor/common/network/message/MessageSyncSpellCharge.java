package aurocosh.divinefavor.common.network.message;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.ClientTickHandler;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import vazkii.arl.network.NetworkMessage;

public class MessageSyncSpellCharge extends NetworkMessage {

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
