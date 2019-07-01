package aurocosh.divinefavor.common.network.message.client.spirit_data

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.custom_data.player.data.favor.SpiritData
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.base.DivineClientMessage
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class MessageSyncAllSpiritData : DivineClientMessage {
    var contracts: NBTTagCompound = NBTTagCompound()
    var favorValues: IntArray = IntArray(0)
    var favorInfinite: BooleanArray = BooleanArray(0)

    constructor()

    constructor(data: SpiritData) {
        contracts = data.serializeContracts()
        favorValues = data.getFavorValues()
        favorInfinite = data.getFavorInfinite()
    }

    @SideOnly(Side.CLIENT)
    override fun handleSafe() {
        val player = DivineFavor.proxy.clientPlayer
        val data = player.divinePlayerData.spiritData

        data.deserializeContracts(contracts)
        data.setFavorValues(favorValues)
        data.setFavorInfinite(favorInfinite)
    }
}