package aurocosh.divinefavor.common.network.message.sever.petrification

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.custom_data.living.LivingData
import aurocosh.divinefavor.common.network.base.WrappedClientMessage
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessagePetrificationReset : WrappedClientMessage() {
    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        val petrification = LivingData[player].petrificationData
        petrification.resetCureTimer()
    }
}
