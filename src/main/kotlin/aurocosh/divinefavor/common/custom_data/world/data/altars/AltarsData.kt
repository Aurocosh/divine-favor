package aurocosh.divinefavor.common.custom_data.world.data.altars

import aurocosh.divinefavor.common.registry.mappers.ModMappers
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.util.math.BlockPos

import java.util.*

// The default implementation of the capability. Holds all the logic.
class AltarsData {
    private val positions: MutableMap<Int, MutableSet<BlockPos>>
    private val positionsInverse: MutableMap<BlockPos, Int>

    var allPositions: Map<Int, MutableSet<BlockPos>>
        get() = HashMap(positions)
        set(positions) {
            this.positions.clear()
            this.positions.putAll(positions)
        }

    init {
        positions = HashMap()
        positionsInverse = HashMap()
    }

    fun addAltarLocation(spirit: ModSpirit, pos: BlockPos) {
        if (positionsInverse.containsKey(pos))
            removeAltarLocation(spirit, pos)
        val altars = getAltars(spirit.id)
        positionsInverse[pos] = spirit.id
        altars.add(pos)
    }

    fun removeAltarLocation(pos: BlockPos) {
        val spiritId = positionsInverse[pos] ?: return
        val spirit = ModMappers.spirits[spiritId]
        removeAltarLocation(spirit, pos)
    }

    fun removeAltarLocation(spirit: ModSpirit, pos: BlockPos) {
        val altars = getAltars(spirit.id)
        positionsInverse.remove(pos)
        altars.remove(pos)
    }

    private fun getAltars(spiritId: Int): MutableSet<BlockPos> {
        return positions.computeIfAbsent(spiritId) { integer -> HashSet() }
    }

    fun getAltarLocations(spirit: ModSpirit): Set<BlockPos> {
        return Collections.unmodifiableSet(getAltars(spirit.id))
    }
}
