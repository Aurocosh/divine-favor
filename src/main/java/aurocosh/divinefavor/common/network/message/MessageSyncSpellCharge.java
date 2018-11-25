package aurocosh.divinefavor.common.network.message;

import aurocosh.divinefavor.DivineFavor;
import aurocosh.divinefavor.client.core.handler.ClientTickHandler;
import aurocosh.divinefavor.common.core.handlers.PlayerDataHandler;
import aurocosh.divinefavor.common.spell.base.SpellChargeType;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import vazkii.arl.network.NetworkMessage;

public class MessageSyncSpellCharge extends NetworkMessage {

    public int spellChargeType;
	public int charges;

	public MessageSyncSpellCharge() { }

	public MessageSyncSpellCharge(SpellChargeType spellChargeType, int charges) {
	    this.spellChargeType = spellChargeType.ordinal();
	    this.charges = charges;
	}

	@Override
	public IMessage handleMessage(MessageContext context) {
        ClientTickHandler.scheduledActions.add(() -> {
            PlayerDataHandler.PlayerData data = PlayerDataHandler.get(DivineFavor.proxy.getClientPlayer());
            data.setSpellCharge(SpellChargeType.values()[spellChargeType],charges);
        });
        return null;
    }
}
