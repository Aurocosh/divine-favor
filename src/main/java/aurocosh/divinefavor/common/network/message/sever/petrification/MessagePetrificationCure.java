package aurocosh.divinefavor.common.network.message.sever.petrification;

import aurocosh.divinefavor.common.network.base.NetworkWrappedServerMessage;
import aurocosh.divinefavor.common.potions.common.ModCurses;
import net.minecraft.entity.player.EntityPlayerMP;

public class MessagePetrificationCure extends NetworkWrappedServerMessage {
	public MessagePetrificationCure() {}

    @Override
    protected void handleSafe(EntityPlayerMP serverPlayer) {
        serverPlayer.removePotionEffect(ModCurses.petrification);
    }
}
