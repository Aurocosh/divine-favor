package aurocosh.divinefavor.common.network.message.sever.petrification

import aurocosh.divinefavor.common.config.common.ConfigArrow
import aurocosh.divinefavor.common.damage_source.ModDamageSources
import aurocosh.autonetworklib.network.base.WrappedServerMessage
import net.minecraft.entity.player.EntityPlayerMP

class MessagePetrificationDamage : WrappedServerMessage() {
    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        serverPlayer.attackEntityFrom(ModDamageSources.petrificationDamage, ConfigArrow.petrification.damage)
    }
}
