package aurocosh.divinefavor.common.network.message.sever.petrification

import aurocosh.divinefavor.common.config.common.ConfigCurses
import aurocosh.divinefavor.common.damage_source.ModDamageSources
import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import net.minecraft.entity.player.EntityPlayerMP

class MessagePetrificationDamage : DivineServerMessage() {
    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        serverPlayer.attackEntityFrom(ModDamageSources.petrificationDamage, ConfigCurses.petrification.damage)
    }
}
