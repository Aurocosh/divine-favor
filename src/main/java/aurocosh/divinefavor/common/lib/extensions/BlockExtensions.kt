package aurocosh.divinefavor.common.lib.extensions

import net.minecraft.block.Block
import net.minecraft.block.BlockDynamicLiquid
import net.minecraft.block.BlockStaticLiquid
import net.minecraft.init.Blocks

fun Block.isWater(): Boolean {
    return this === Blocks.WATER || this === Blocks.FLOWING_WATER
}

fun Block.isIce(): Boolean {
    return this === Blocks.ICE || this === Blocks.PACKED_ICE || this === Blocks.FROSTED_ICE
}

fun Block.isLava(): Boolean {
    return this === Blocks.LAVA || this === Blocks.FLOWING_LAVA
}

fun Block.isLiquid(): Boolean {
    return this is BlockStaticLiquid || this is BlockDynamicLiquid
}