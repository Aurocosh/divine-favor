package aurocosh.divinefavor.common.network.message.client.spirit_data

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.base.WrappedClientMessage
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncFavor : WrappedClientMessage {
    var spiritId: Int = 0
    var favor: Int = 0

    constructor() {}

    constructor(spirit: ModSpirit, spiritData: SpiritData) {
        spiritId = spirit.id
        favor = spiritData.getFavor(spiritId)
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        player.divinePlayerData.spiritData.setFavor(spiritId, favor)
    }
}
