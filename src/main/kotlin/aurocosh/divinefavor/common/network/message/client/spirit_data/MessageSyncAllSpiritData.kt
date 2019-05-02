package aurocosh.divinefavor.common.network.message.client.spirit_data

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.custom_data.player.PlayerData
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData
import aurocosh.divinefavor.common.network.base.WrappedClientMessage
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncAllSpiritData : WrappedClientMessage {
    lateinit var tag: NBTTagCompound

    constructor() {}

    constructor(data: SpiritData) {
        tag = getNbtTagCompound(data)
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        val data = PlayerData[player]!!.spiritData
        setDataFromNBT(data, tag)
    }

    companion object {
        private const val TAG_CONTRACTS = "Contracts"
        private const val TAG_FAVOR_VALUES = "FavorValues"

        fun getNbtTagCompound(instance: SpiritData): NBTTagCompound {
            val tag = NBTTagCompound()
            tag.setTag(TAG_CONTRACTS, instance.serializeContracts())
            tag.setIntArray(TAG_FAVOR_VALUES, instance.getFavorValues())
            return tag
        }

        fun setDataFromNBT(instance: SpiritData, nbt: NBTTagCompound) {
            instance.deserializeContracts(nbt.getCompoundTag(TAG_CONTRACTS))
            instance.setFavorValues(nbt.getIntArray(TAG_FAVOR_VALUES))
        }
    }
}