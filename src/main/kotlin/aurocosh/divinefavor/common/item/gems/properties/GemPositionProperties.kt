package aurocosh.divinefavor.common.item.gems.properties

import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyBlockPos
import aurocosh.divinefavor.common.stack_properties.properties.StackPropertyInt
import net.minecraft.util.math.BlockPos

object GemPositionProperties {
    val position = StackPropertyBlockPos("Position", BlockPos.ORIGIN)
    val dimension = StackPropertyInt("Dimension", 0)
}
