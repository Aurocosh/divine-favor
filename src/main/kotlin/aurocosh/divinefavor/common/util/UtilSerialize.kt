package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.lib.GlobalBlockPos
import net.minecraft.nbt.CompressedStreamTools
import net.minecraft.nbt.NBTTagCompound
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*

object UtilSerialize {
    fun serializeGlobalBlockPosArray(posArrayList: List<GlobalBlockPos>): IntArray {
        val array = IntArray(posArrayList.size * 4)
        var i = 0
        for ((pos, dimensionId) in posArrayList) {
            array[i++] = pos.x
            array[i++] = pos.y
            array[i++] = pos.z
            array[i++] = dimensionId
        }

        return array
    }

    fun deserializeGlobalBlockPosArray(array: IntArray): ArrayList<GlobalBlockPos> {
        val posCount = array.size / 4
        val posArrayList = ArrayList<GlobalBlockPos>()

        var i = 0
        var j = 0
        while (i < posCount) {
            val x = array[j++]
            val y = array[j++]
            val z = array[j++]
            val dimension = array[j++]
            posArrayList.add(GlobalBlockPos(x, y, z, dimension))
            i++
        }
        return posArrayList
    }

    fun stringToUUID(string: String): UUID? {
        try {
            return UUID.fromString(string)
        } catch (var2: IllegalArgumentException) {
            return null
        }

    }

    @Throws(IOException::class)
    fun getPasteStream(compound: NBTTagCompound): ByteArrayOutputStream? {
        val outputStream = ByteArrayOutputStream()
        CompressedStreamTools.writeCompressed(compound, outputStream)
        return if (outputStream.size() < Short.MAX_VALUE - 200) outputStream else null
    }
}
