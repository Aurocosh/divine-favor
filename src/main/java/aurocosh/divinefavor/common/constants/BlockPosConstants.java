package aurocosh.divinefavor.common.constants;

import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BlockPosConstants {
    public static final BlockPos ZERO = new BlockPos(0, 0, 0);
    public static final BlockPos MIN = new BlockPos(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    public static final BlockPos MAX = new BlockPos(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

    public static final BlockPos UP = new BlockPos(0, 1, 0);
    public static final BlockPos DOWN = new BlockPos(0, -1, 0);
    public static final BlockPos NORTH = new BlockPos(0, 0, -1);
    public static final BlockPos SOUTH = new BlockPos(0, 0, 1);
    public static final BlockPos WEST = new BlockPos(-1, 0, 0);
    public static final BlockPos EAST = new BlockPos(1, 0, 0);


    public static final List<BlockPos> DIRECT_NEIGHBOURS = Collections.unmodifiableList(Arrays.asList(
            UP,
            DOWN,
            WEST,
            EAST,
            NORTH,
            SOUTH
    ));

    public static final List<BlockPos> HORIZONTAL_DIAGONAL = Collections.unmodifiableList(Arrays.asList(
            WEST.add(NORTH),
            EAST.add(NORTH),
            WEST.add(SOUTH),
            EAST.add(SOUTH)
    ));
    public static final List<BlockPos> HORIZONTAL_DIRECT = Collections.unmodifiableList(Arrays.asList(
            WEST,
            EAST,
            NORTH,
            SOUTH
    ));

    public static final List<BlockPos> VERTICAL_NORTH_SOUTH_DIRECT = Collections.unmodifiableList(Arrays.asList(
            UP,
            DOWN,
            NORTH,
            SOUTH
    ));
    public static final List<BlockPos> VERTICAL_NORTH_SOUTH_DIAGONAL = Collections.unmodifiableList(Arrays.asList(
            UP.add(NORTH),
            DOWN.add(NORTH),
            UP.add(SOUTH),
            DOWN.add(SOUTH)
    ));

    public static final List<BlockPos> VERTICAL_WEST_EAST_DIRECT = Collections.unmodifiableList(Arrays.asList(
            UP,
            DOWN,
            WEST,
            EAST
    ));
    public static final List<BlockPos> VERTICAL_WEST_EAST_DIAGONAL = Collections.unmodifiableList(Arrays.asList(
            UP.add(EAST),
            DOWN.add(EAST),
            UP.add(WEST),
            DOWN.add(WEST)
    ));

    public static final List<BlockPos> HORIZONTAL_ALL = Collections.unmodifiableList(UtilList.unite(
            HORIZONTAL_DIRECT,
            HORIZONTAL_DIAGONAL));
    public static final List<BlockPos> DIRECT_AND_DIAGONAL = Collections.unmodifiableList(UtilList.unite(
            HORIZONTAL_DIRECT,
            HORIZONTAL_DIAGONAL,
            VERTICAL_NORTH_SOUTH_DIRECT,
            VERTICAL_NORTH_SOUTH_DIAGONAL,
            VERTICAL_WEST_EAST_DIRECT,
            VERTICAL_WEST_EAST_DIAGONAL));
}
