package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.lib.GlobalBlockPos
import net.minecraft.nbt.CompressedStreamTools
import net.minecraft.nbt.NBTTagCompound
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

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

    fun splitArray(arrayToSplit: ByteArray, chunkSize: Int): List<ByteArray> {
        if (chunkSize <= 0)
            return emptyList()

        // first we have to check if the array can be split in multiple
        // arrays of equal 'chunk' size
        val rest = arrayToSplit.size % chunkSize  // if rest>0 then our last array will have less elements than the others
        // then we check in how many arrays we can split our input array
        val chunks = arrayToSplit.size / chunkSize + if (rest > 0) 1 else 0 // we may have to add an additional array for the 'rest'
        // now we know how many arrays we need and create our result array
        val arrays = ArrayList<ByteArray>(chunks)
        // we create our resulting arrays by copying the corresponding
        // part from the input array. If we have a rest (rest>0), then
        // the last array will have less elements than the others. This
        // needs to be handled separately, so we iterate 1 times less.
        for (i in 0 until if (rest > 0) chunks - 1 else chunks) {
            // this copies 'chunk' times 'chunkSize' elements into a new array
            val chunk = Arrays.copyOfRange(arrayToSplit, i * chunkSize, i * chunkSize + chunkSize)
            arrays.add(chunk)
        }
        if (rest > 0) { // only when we have a rest
            // we copy the remaining elements into the last chunk
            val chunk = Arrays.copyOfRange(arrayToSplit, (chunks - 1) * chunkSize, (chunks - 1) * chunkSize + rest)
            arrays.add(chunk)
        }
        return arrays // that's it
    }

    fun joinByteArrays(arrays: List<ByteArray>): ByteArray {
        if (arrays.isEmpty())
            return ByteArray(0)
        val firstArray = arrays.first()
        if (arrays.size == 1)
            return firstArray.copyOf()

        val finalSize = arrays.sumBy(ByteArray::size)
        val otherArrays = arrays.subList(1, arrays.size - 1)

        val finalArray = firstArray.copyOf(finalSize)
        var nextIndexStart = firstArray.size
        for (array in otherArrays) {
            System.arraycopy(array, 0, finalArray, nextIndexStart, array.size)
            nextIndexStart += array.size
        }
        return finalArray
    }
}
