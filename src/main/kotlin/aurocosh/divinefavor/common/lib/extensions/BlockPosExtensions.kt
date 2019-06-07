package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.util.math.BlockPos

fun BlockPos.inverse(): BlockPos {
    return BlockPos(-this.x, -this.y, -this.z)
}