package aurocosh.divinefavor.common.network.common;

import aurocosh.divinefavor.common.network.message.client.*;
import aurocosh.divinefavor.common.network.message.client.contracts.MessageSyncContracts;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncAllFavors;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncFavor;
import aurocosh.divinefavor.common.network.message.client.spell_uses.MessageSyncFavorValue;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncGrimoireSlot;
import aurocosh.divinefavor.common.network.message.sever.MessageSyncSpellBowSlot;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationCure;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationDamage;
import aurocosh.divinefavor.common.network.message.sever.petrification.MessagePetrificationReset;
import net.minecraftforge.fml.relauncher.Side;

public class MessageRegister {
	public static void init() {
        NetworkHandler.register(MessagePetrificationReset.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncAllFavors.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncContracts.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFavor.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFavorValue.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFireImmunity.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFlyingCapability.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncFury.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncGrudge.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncPotionCharge.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncPower.class, Side.CLIENT);
        NetworkHandler.register(MessageSyncWindLeash.class, Side.CLIENT);

        NetworkHandler.register(MessagePetrificationCure.class, Side.SERVER);
        NetworkHandler.register(MessagePetrificationDamage.class, Side.SERVER);
        NetworkHandler.register(MessageSyncGrimoireSlot.class, Side.SERVER);
        NetworkHandler.register(MessageSyncSpellBowSlot.class, Side.SERVER);
    }
}
