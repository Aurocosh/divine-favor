package aurocosh.divinefavor.common.util;

import net.minecraft.util.EnumFacing;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class UtilFacing {
    public static final List<EnumFacing> horizontal = Collections.unmodifiableList(
        Arrays.asList(
            EnumFacing.NORTH,
            EnumFacing.EAST,
            EnumFacing.SOUTH,
            EnumFacing.WEST
        )
    );

    public static final List<EnumFacing> vertical = Collections.unmodifiableList(
            Arrays.asList(
                    EnumFacing.UP,
                    EnumFacing.DOWN
            )
    );
}
