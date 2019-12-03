package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.block.material.Material
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks

fun IBlockState.isWater(): Boolean {
    return this.material == Material.WATER
}

fun IBlockState.isIce(): Boolean {
    return this.material == Material.ICE
}

fun IBlockState.isLava(): Boolean {
    return this.material == Material.LAVA
}

fun IBlockState.isLiquid(): Boolean {
    return this.material == Material.WATER || this.material == Material.LAVA
}