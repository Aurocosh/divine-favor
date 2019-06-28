package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.util.EnumFacing

val EnumFacing.directionPos get() = this.directionVec.toBlockPos()
