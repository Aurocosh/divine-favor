package aurocosh.divinefavor.common.network.message.client.spirit_data

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.autonetworklib.network.base.WrappedClientMessage
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncFavorInfinite : WrappedClientMessage {
    var spiritId: Int = 0
    var infinite: Boolean = false

    constructor() {}

    constructor(spirit: ModSpirit, spiritData: SpiritData) {
        spiritId = spirit.id
        infinite = spiritData.isFavorInfinite(spiritId)
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        player.divinePlayerData.spiritData.setFavorInfinite(spiritId, infinite)
    }
}
