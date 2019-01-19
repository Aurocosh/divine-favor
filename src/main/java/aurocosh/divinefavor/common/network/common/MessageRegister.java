package aurocosh.divinefavor.common.network.common;

import aurocosh.divinefavor.common.network.message.client.*;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncAllTalismanUses;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncMaxSpellUses;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncSpellUses;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncGrimoireSlot;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncSpellBowSlot;
import net.minecraftforge.fml.relauncher.Side;

public class MessageRegister {
	public static void init() {
        NetworkHandler.register(MessageSyncAllTalismanUses.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFireImmunity.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFlyingCapability.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFury.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncGrudge.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncMaxSpellUses.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncPotionCharge.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncPower.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncSpellUses.class, Side.CLIENT);

        NetworkHandler.register(MessageSyncGrimoireSlot.class, Side.SERVER);
        NetworkHandler.register(MessageSyncSpellBowSlot.class, Side.SERVER);
    }
}
