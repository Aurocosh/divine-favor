package aurocosh.divinefavor.common.network.message.sever.petrification

import aurocosh.autonetworklib.network.base.WrappedServerMessage
import aurocosh.divinefavor.common.potions.common.ModCurses
import net.minecraft.entity.player.EntityPlayerMP

class MessagePetrificationCure : WrappedServerMessage() {
    override fun handleSafe(serverPlayer: EntityPlayerMP) {
        serverPlayer.removePotionEffect(ModCurses.petrification)
    }
}
