package aurocosh.divinefavor.common.constants

import net.minecraft.util.EnumFacing
import java.util.*

object FacingConstants {
    val horizontal: List<EnumFacing> = Collections.unmodifiableList(
            Arrays.asList(
                    EnumFacing.NORTH,
                    EnumFacing.EAST,
                    EnumFacing.SOUTH,
                    EnumFacing.WEST
            )
    )

    val vertical: List<EnumFacing> = Collections.unmodifiableList(
            Arrays.asList(
                    EnumFacing.UP,
                    EnumFacing.DOWN
            )
    )
}
