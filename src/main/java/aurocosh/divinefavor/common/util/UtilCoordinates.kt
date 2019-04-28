package aurocosh.divinefavor.common.util

import aurocosh.divinefavor.common.constants.BlockPosConstants
import aurocosh.divinefavor.common.lib.extensions.selectRandom
import net.minecraft.init.Blocks
import net.minecraft.util.EnumFacing
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Vec3d
import net.minecraft.world.World
import java.util.*

object UtilCoordinates {
    fun getRandomNeighbours(start: BlockPos, neighbourCount: Int, minNextNode: Int, maxNextNode: Int, cycleLimit: Int, predicate: (BlockPos) -> Boolean): List<BlockPos> {
        var neighbourCounter = neighbourCount
        var cycleCounter = cycleLimit
        val selectedNodes = ArrayList<BlockPos>()
        val nodesToVisit = ArrayDeque<BlockPos>()
        val plannedNodes = HashSet<BlockPos>()
        val visitedNodes = HashSet<BlockPos>()
        nodesToVisit.add(start)

        while (!nodesToVisit.isEmpty() && cycleCounter-- > 0 && neighbourCounter-- > 0) {
            val nextNode = nodesToVisit.remove()
            plannedNodes.remove(nextNode)
            visitedNodes.add(nextNode)
            selectedNodes.add(nextNode)

            if (!predicate.invoke(nextNode))
                continue

            val neighboursToAdd = UtilRandom.nextInt(minNextNode, maxNextNode)
            val randomNewNodes = BlockPosConstants.DIRECT_NEIGHBOURS
                    .map(nextNode::add)
                    .filter { node -> !visitedNodes.contains(node) && !plannedNodes.contains(node) }
                    .selectRandom(neighboursToAdd)
            nodesToVisit.addAll(randomNewNodes)
            plannedNodes.addAll(randomNewNodes)
        }
        return selectedNodes
    }

    fun getSphereOutline(center: BlockPos, radiusInternal: Int, radiusExternal: Int): List<BlockPos> {
        val cubeCoordinates = getBlocksInSphere(center, radiusExternal)
        val radiusSq = radiusInternal * radiusInternal
        return UtilList.select(cubeCoordinates) { pos -> center.distanceSq(pos) >= radiusSq }
    }

    fun getBlocksInSphere(center: BlockPos, radius: Int): List<BlockPos> {
        val cubeCoordinates = getBlocksInRadius(center, radius, radius, radius)
        val radiusSq = radius * radius
        return UtilList.select(cubeCoordinates) { pos -> center.distanceSq(pos) <= radiusSq }
    }

    fun getBlocksInRadius(center: BlockPos, radiusX: Int, radiusY: Int, radiusZ: Int): List<BlockPos> {
        val coordinates = ArrayList<BlockPos>()
        for (x in -radiusX..radiusX)
            for (y in -radiusY..radiusY)
                for (z in -radiusZ..radiusZ)
                    coordinates.add(center.add(x, y, z))
        return coordinates
    }

    fun getRandomNeighbourSafe(center: BlockPos, xRadius: Int, yRadius: Int, zRadius: Int): BlockPos {
        val destination = getRandomNeighbour(center, xRadius, yRadius, zRadius)
        return BlockPos(destination.x, Math.max(5, destination.y), destination.z)
    }

    fun getRandomNeighbour(center: BlockPos, xRadius: Int, yRadius: Int, zRadius: Int): BlockPos {
        val xShift = UtilRandom.nextInt(-xRadius, xRadius)
        val yShift = UtilRandom.nextInt(-yRadius, yRadius)
        val zShift = UtilRandom.nextInt(-zRadius, zRadius)
        return center.add(xShift, yShift, zShift)
    }

    fun getRandomBlockInRange(center: BlockPos, radius: Int, limit: Int, predicate: (BlockPos) -> Boolean): BlockPos? {
        val blockPos: BlockPos = getRandomNeighbour(center, radius, radius, radius)
        if (predicate.invoke(blockPos))
            return blockPos

        return findPosition(center, limit, predicate, { it.down() }) ?: findPosition(center, limit, predicate, { it.up() })
    }

    fun findPlaceToStand(start: BlockPos, world: World, limit: Int): BlockPos? {
        val pos = findPlaceToStandBelow(start.up(), world, limit, true)
        return pos ?: findPlaceToTeleportAbove(start.down(), world, limit)
    }

    fun findPlaceToTeleport(start: BlockPos, world: World, facing: EnumFacing, limit: Int, needPlaceToStand: Boolean): BlockPos? {
        var limitCounter = limit
        var pos = start
        while (limitCounter-- > 0) {
            pos = pos.offset(facing)
            if (!world.isAirBlock(pos) || !world.isAirBlock(pos.up()))
                continue
            if (!needPlaceToStand)
                return pos
            val state = world.getBlockState(pos.down())
            if (state.block === Blocks.BEDROCK)
                return null
            if (state.isSideSolid(world, pos.down(), EnumFacing.UP))
                return pos
        }
        return null
    }

    fun findPlaceToTeleportAbove(start: BlockPos, world: World, limit: Int): BlockPos? {
        var limitCounter = limit
        var pos = start
        var previousSecondIsAir = false
        var previousOneIsAir = false
        while (limitCounter-- > 0) {
            pos = pos.up()
            if (previousOneIsAir && previousSecondIsAir)
                return pos.down()

            val state = world.getBlockState(pos)
            if (state.block === Blocks.BEDROCK)
                return null

            previousSecondIsAir = previousOneIsAir
            previousOneIsAir = world.isAirBlock(pos)
        }
        return null
    }

    fun findPlaceToStandBelow(start: BlockPos, world: World, limit: Int, needPlaceToStand: Boolean): BlockPos? {
        var limitCounter = limit
        var pos = start
        var previousSecondIsAir = false
        var previousOneIsAir = false
        while (limitCounter-- > 0) {
            pos = pos.down()
            val state = world.getBlockState(pos)
            if (previousOneIsAir && previousSecondIsAir && (!needPlaceToStand || state.isSideSolid(world, pos, EnumFacing.UP)))
                return pos.up()
            if (state.block === Blocks.BEDROCK)
                return null

            previousSecondIsAir = previousOneIsAir
            previousOneIsAir = world.isAirBlock(pos)
        }
        return null
    }

    fun findPosition(start: BlockPos, limit: Int, predicate: (BlockPos) -> Boolean, nextPosFunction: (BlockPos) -> BlockPos): BlockPos? {
        var limitCounter = limit
        var pos = start
        while (limitCounter-- > 0) {
            if (predicate.invoke(pos))
                return pos
            pos = nextPosFunction.invoke(pos)
        }
        return null
    }

    fun floodFill(start: List<BlockPos>, expansionDirs: List<BlockPos>, predicate: (BlockPos) -> Boolean, limit: Int): List<BlockPos> {
        val expansionFront = ArrayDeque(start)
        val explored = HashSet(start)
        val result = ArrayList<BlockPos>()

        while (expansionFront.size > 0 && result.size < limit) {
            val nextPos = expansionFront.remove()
            if (!predicate.invoke(nextPos))
                continue
            result.add(nextPos)
            for (expansionDir in expansionDirs) {
                val neighbour = nextPos.add(expansionDir)
                if (!explored.contains(neighbour)) {
                    expansionFront.add(neighbour)
                    explored.add(neighbour)
                }
            }
        }
        return result
    }

    fun getBoundingBox(origin: BlockPos, radius: Double): AxisAlignedBB {
        return AxisAlignedBB(origin.x - radius, origin.y - radius, origin.z - radius, origin.x + radius, origin.y + radius, origin.z + radius)
    }

    fun getBoundingBox(origin: Vec3d, radius: Double): AxisAlignedBB {
        return AxisAlignedBB(origin.x - radius, origin.y - radius, origin.z - radius, origin.x + radius, origin.y + radius, origin.z + radius)
    }
}
