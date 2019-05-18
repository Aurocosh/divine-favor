package aurocosh.divinefavor.common.custom_data.world.data.altars

import aurocosh.divinefavor.common.lib.interfaces.INbtSerializer
import aurocosh.divinefavor.common.registry.mappers.ModMappers
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlockPos
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.math.BlockPos

import java.util.*

// Handles the actual read/write of the nbt.
class AltarsDataSerializer : INbtSerializer<AltarsData> {

    override fun serialize(nbt: NBTTagCompound, instance: AltarsData) {
        val altarNbt = NBTTagCompound()

        val positions = instance.allPositions
        for ((spiritId, value) in positions) {
            val altarLocations = ArrayList(value)
            val spirit = ModMappers.spirits[spiritId]
            val posArray = UtilBlockPos.serialize(altarLocations)
            val spiritName = spirit.registryName!!.toString()
            altarNbt.setIntArray(spiritName, posArray)
        }
        nbt.setTag(TAG_ALTAR_POSITIONS, altarNbt)
    }

    override fun deserialize(nbt: NBTTagCompound, instance: AltarsData) {
        if (!nbt.hasKey(TAG_ALTAR_POSITIONS))
            return
        val altarNbt = nbt.getCompoundTag(TAG_ALTAR_POSITIONS)
        val spirits = ModMappers.spirits.getValues()
        val positions = HashMap<Int, MutableSet<BlockPos>>()
        for (spirit in spirits) {
            val spiritName = spirit.registryName!!.toString()
            if (!altarNbt.hasKey(spiritName))
                return
            val posArray = altarNbt.getIntArray(spiritName)
            val altarLocations = UtilBlockPos.deserialize(posArray)
            positions[spirit.id] = HashSet(altarLocations)
        }
        instance.allPositions = positions
    }

    companion object {
        private val TAG_ALTAR_POSITIONS = "AltarPositions"
    }
}
