package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.block.Block
import net.minecraft.block.BlockDynamicLiquid
import net.minecraft.block.BlockStaticLiquid
import net.minecraft.init.Blocks
import net.minecraft.util.math.BlockPos

fun BlockPos.inverse(): BlockPos {
    return BlockPos(-this.x, -this.y, -this.z)
}