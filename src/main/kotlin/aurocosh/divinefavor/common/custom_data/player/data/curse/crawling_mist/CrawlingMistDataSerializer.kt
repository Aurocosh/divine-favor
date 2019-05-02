package aurocosh.divinefavor.common.custom_data.player.data.curse.crawling_mist

import aurocosh.divinefavor.common.lib.extensions.fallback
import aurocosh.divinefavor.common.lib.extensions.getBlockPos
import aurocosh.divinefavor.common.lib.extensions.setBlockPos
import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos

// Handles the actual read/write of the nbt.
class CrawlingMistDataSerializer : INbtSerializer<CrawlingMistData> {

    override fun serialize(nbt: NBTTagCompound, instance: CrawlingMistData) {
        nbt.setBlockPos(TAG_MIST_ORIGIN, instance.mistOrigin)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: CrawlingMistData) {
        instance.mistOrigin = nbt.fallback(TAG_MIST_ORIGIN, NBTTagCompound::getBlockPos, BlockPos.ORIGIN)
    }

    companion object {
        private const val TAG_MIST_ORIGIN = "MistOrigin"
    }
}
