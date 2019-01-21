package aurocosh.divinefavor.common.network.message.sever.petrification;

import aurocosh.divinefavor.common.damage_source.ModDamageSources;
import aurocosh.divinefavor.common.network.base.NetworkWrappedServerMessage;
import aurocosh.divinefavor.common.potions.curses.PotionPetrification;
import net.minecraft.entity.player.EntityPlayerMP;

public class MessagePetrificationDamage extends NetworkWrappedServerMessage {
	public MessagePetrificationDamage() {}

    @Override
    protected void handleSafe(EntityPlayerMP serverPlayer) {
        serverPlayer.attackEntityFrom(ModDamageSources.petrificationDamage, PotionPetrification.DAMAGE);
    }
}
