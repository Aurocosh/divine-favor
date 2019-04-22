package aurocosh.divinefavor.common.network.message.sever.petrification;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.damage_source.ModDamageSources;
import aurocosh.divinefavor.common.network.base.WrappedServerMessage;
import net.minecraft.entity.player.EntityPlayerMP;

public class MessagePetrificationDamage extends WrappedServerMessage {
    public MessagePetrificationDamage() {
    }

    @Override
    protected void handleSafe(EntityPlayerMP serverPlayer) {
        serverPlayer.attackEntityFrom(ModDamageSources.petrificationDamage, ConfigArrow.petrification.damage);
    }
}
