package aurocosh.divinefavor.common.muliblock.instance

import aurocosh.divinefavor.common.lib.extensions.S
import aurocosh.divinefavor.common.lib.math.CuboidBoundingBox
import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.muliblock.MultiBlockConfiguration
import aurocosh.divinefavor.common.muliblock.validators.AirStateValidator
import net.minecraft.util.math.BlockPos
import java.util.*

open class MultiBlockInstance(val multiBlock: ModMultiBlock, val configuration: MultiBlockConfiguration, controllerPosition: BlockPos) {
    val boundingBox: CuboidBoundingBox
    val multiBlockOrigin: BlockPos
    val positionsOfSolids: Set<BlockPos>
    val positionsOfAir: Set<BlockPos>

    init {

        val bounds = configuration.boundingBoxRelative
        this.boundingBox = bounds.add(controllerPosition)

        multiBlockOrigin = controllerPosition.subtract(configuration.controllerRelPosition)

        val air = HashSet<BlockPos>()
        val solids = HashSet<BlockPos>()

        for (part in configuration.parts) {
            val posList = part.positions.S.map(multiBlockOrigin::add)
            if (part.validator is AirStateValidator)
                air.addAll(posList)
            else
                solids.addAll(posList)
        }

        this.positionsOfAir = Collections.unmodifiableSet(air)
        this.positionsOfSolids = Collections.unmodifiableSet(solids)
    }

    fun isSolidPart(position: BlockPos): Boolean {
        return boundingBox.isCoordinateInside(position) && positionsOfSolids.contains(position)
    }

    fun isSupposedToBeEmpty(position: BlockPos): Boolean {
        return boundingBox.isCoordinateInside(position) && positionsOfAir.contains(position)
    }
}
