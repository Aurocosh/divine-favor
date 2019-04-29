package aurocosh.divinefavor.common.constants

import aurocosh.divinefavor.common.util.UtilList
import net.minecraft.util.math.BlockPos
import java.util.*

object BlockPosConstants {
    val ZERO = BlockPos(0, 0, 0)
    val MIN = BlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE)
    val MAX = BlockPos(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE)

    val UP = BlockPos(0, 1, 0)
    val DOWN = BlockPos(0, -1, 0)
    val NORTH = BlockPos(0, 0, -1)
    val SOUTH = BlockPos(0, 0, 1)
    val WEST = BlockPos(-1, 0, 0)
    val EAST = BlockPos(1, 0, 0)


    val DIRECT_NEIGHBOURS: List<BlockPos> = Collections.unmodifiableList(Arrays.asList(
            UP,
            DOWN,
            WEST,
            EAST,
            NORTH,
            SOUTH
    ))

    val HORIZONTAL_DIAGONAL: List<BlockPos> = Collections.unmodifiableList(Arrays.asList(
            WEST.add(NORTH),
            EAST.add(NORTH),
            WEST.add(SOUTH),
            EAST.add(SOUTH)
    ))
    val HORIZONTAL_DIRECT: List<BlockPos> = Collections.unmodifiableList(Arrays.asList(
            WEST,
            EAST,
            NORTH,
            SOUTH
    ))

    val VERTICAL_NORTH_SOUTH_DIRECT: List<BlockPos> = Collections.unmodifiableList(Arrays.asList(
            UP,
            DOWN,
            NORTH,
            SOUTH
    ))
    val VERTICAL_NORTH_SOUTH_DIAGONAL: List<BlockPos> = Collections.unmodifiableList(Arrays.asList(
            UP.add(NORTH),
            DOWN.add(NORTH),
            UP.add(SOUTH),
            DOWN.add(SOUTH)
    ))

    val VERTICAL_WEST_EAST_DIRECT: List<BlockPos> = Collections.unmodifiableList(Arrays.asList(
            UP,
            DOWN,
            WEST,
            EAST
    ))
    val VERTICAL_WEST_EAST_DIAGONAL: List<BlockPos> = Collections.unmodifiableList(Arrays.asList(
            UP.add(EAST),
            DOWN.add(EAST),
            UP.add(WEST),
            DOWN.add(WEST)
    ))

    val HORIZONTAL_ALL: List<BlockPos> = Collections.unmodifiableList(UtilList.unite(
            HORIZONTAL_DIRECT,
            HORIZONTAL_DIAGONAL))
    val DIRECT_AND_DIAGONAL: List<BlockPos> = Collections.unmodifiableList(UtilList.unite(
            HORIZONTAL_DIRECT,
            HORIZONTAL_DIAGONAL,
            VERTICAL_NORTH_SOUTH_DIRECT,
            VERTICAL_NORTH_SOUTH_DIAGONAL,
            VERTICAL_WEST_EAST_DIRECT,
            VERTICAL_WEST_EAST_DIAGONAL))
}
