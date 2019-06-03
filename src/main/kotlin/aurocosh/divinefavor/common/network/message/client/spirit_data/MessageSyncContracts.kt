package aurocosh.divinefavor.common.network.message.client.spirit_data

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.autonetworklib.network.base.WrappedClientMessage
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncContracts : WrappedClientMessage {
    var tag: NBTTagCompound = NBTTagCompound()

    constructor() {}

    constructor(data: SpiritData) {
        tag.setTag(TAG_CONTRACTS, data.serializeContracts())
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        player.divinePlayerData.spiritData.deserializeContracts(tag.getCompoundTag(TAG_CONTRACTS))
    }

    companion object {
        private const val TAG_CONTRACTS = "Contracts"
    }
}