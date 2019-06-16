package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.util.math.BlockPos

fun BlockPos.inverse(): BlockPos {
    return BlockPos(-this.x, -this.y, -this.z)
}

fun BlockPos.scale(value: Int): BlockPos {
    return BlockPos(this.x * value, this.y * value, this.z * value)
}