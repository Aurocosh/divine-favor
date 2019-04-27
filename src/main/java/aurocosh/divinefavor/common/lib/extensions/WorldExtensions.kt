package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

fun World.getBlock(pos: BlockPos): Block {
    return this.getBlockState(pos).block
}

fun World.getMaterial(pos: BlockPos): Material {
    return this.getBlockState(pos).material
}

fun World.isWood(pos: BlockPos): Boolean {
    return this.getBlockState(pos).block.isWood(this, pos)
}

fun World.isLeaves(pos: BlockPos): Boolean {
    val blockState = this.getBlockState(pos)
    val block = blockState.block
    return block.isLeaves(blockState, this, pos)
}
