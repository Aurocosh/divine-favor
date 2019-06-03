package aurocosh.divinefavor.common.network.message.sever.petrification

import aurocosh.divinefavor.common.network.message.base.DivineServerMessage
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.player.EntityPlayerMP

class MessagePetrificationCure : DivineServerMessage() {
    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        serverPlayer.removePotionEffect(ModCurses.petrification)
    }
}
