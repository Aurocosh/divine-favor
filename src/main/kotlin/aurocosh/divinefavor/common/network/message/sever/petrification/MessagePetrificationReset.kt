package aurocosh.divinefavor.common.network.message.sever.petrification

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divineLivingData
import aurocosh.divinefavor.common.network.message.base.DivineClientMessage
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessagePetrificationReset : DivineClientMessage() {
    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        val petrification = player.divineLivingData.petrificationData
        petrification.resetCureTimer()
    }
}
