package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.item.tool_talismans.base.PickDestroySpeedType
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.Block
import net.minecraft.block.state.IBlockState
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack

class ToolTalismanObsidianCarving(name: String, spirit: ModSpirit, favorCost: Int, block: Block, predicate: (Block) -> Boolean) : ToolTalismanCarving(name, spirit, favorCost, block, predicate) {

    override fun getDestroySpeedType(stack: ItemStack, state: IBlockState): PickDestroySpeedType {
        if (state.block === Blocks.OBSIDIAN)
            return PickDestroySpeedType.GET_FROM_TALISMAN
        return PickDestroySpeedType.STANDARD
    }

    override fun getCustomDestroySpeed(talismanStack: ItemStack, state: IBlockState): Float {
        return 800f
    }
}
