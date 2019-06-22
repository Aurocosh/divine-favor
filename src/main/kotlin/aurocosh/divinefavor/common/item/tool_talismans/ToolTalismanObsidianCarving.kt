package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.Block
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.player.PlayerEvent

class ToolTalismanObsidianCarving(name: String, spirit: ModSpirit, favorCost: Int, block: Block, predicate: (Block) -> Boolean) : ToolTalismanCarving(name, spirit, favorCost, block, predicate) {

    override fun getMiningSpeed(stack: ItemStack, event: PlayerEvent.BreakSpeed) {
        if (event.state.block === Blocks.OBSIDIAN)
            event.newSpeed += 800f
    }
}
