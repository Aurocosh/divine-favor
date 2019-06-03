package aurocosh.divinefavor.common.network.message.client.syncing

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.base.DivineClientMessage
import net.minecraft.util.ResourceLocation
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncGrudge : DivineClientMessage {
    lateinit var mobTypeId: String

    constructor() {}

    constructor(mobTypeId: ResourceLocation) {
        this.mobTypeId = mobTypeId.toString()
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        player.divinePlayerData.grudgeData.mobTypeId = ResourceLocation(mobTypeId)
    }
}
