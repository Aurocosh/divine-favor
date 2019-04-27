package aurocosh.divinefavor.common.item.talismans.spell

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