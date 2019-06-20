package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack

class ToolTalismanObsidianCarving(name: String, spirit: ModSpirit, favorCost: Int, block: Block, predicate: (Block) -> Boolean) : ToolTalismanCarving(name, spirit, favorCost, block, predicate) {

    override fun isDestroySpeedCustom(stack: ItemStack, state: IBlockState) = true

    override fun getCustomDestroySpeed(stack: ItemStack, state: IBlockState): Float {
        if (state.block === Blocks.OBSIDIAN)
            return 800f
        return super.getCustomDestroySpeed(stack, state)
    }
}
