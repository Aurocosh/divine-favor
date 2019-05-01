package aurocosh.divinefavor.common.custom_data.player.data.curse.crawling_mist

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import aurocosh.divinefavor.common.util.UtilNbt
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos

// Handles the actual read/write of the nbt.
class CrawlingMistDataSerializer : INbtSerializer<CrawlingMistData> {

    override fun serialize(nbt: NBTTagCompound, instance: CrawlingMistData) {
        UtilNbt.setBlockPos(nbt, TAG_MIST_ORIGIN, instance.mistOrigin)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: CrawlingMistData) {
        instance.mistOrigin = UtilNbt.getBlockPos(nbt, TAG_MIST_ORIGIN, BlockPos.ORIGIN)
    }

    companion object {
        private const val TAG_MIST_ORIGIN = "MistOrigin"
    }
}
