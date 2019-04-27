package aurocosh.divinefavor.common.lib.wrapper

import aurocosh.divinefavor.common.item.talismans.spell.getBlock
import net.minecraft.block.Block
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class BlockPredicate : PredicateWrapper<BlockPos, Block> {
    constructor(world: World, predicate: (Block) -> Boolean): super({ world.getBlock(it)}, predicate) {}

    constructor(world: World, block: Block) : super({ world.getBlock(it)}, { value -> value === block }) {}
}
