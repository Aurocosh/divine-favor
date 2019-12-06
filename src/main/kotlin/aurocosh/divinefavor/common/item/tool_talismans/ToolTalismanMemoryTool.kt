package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.item.spell_talismans.context.CastContext
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.getBlock
import aurocosh.divinefavor.common.lib.extensions.set
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.block.Block
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.player.PlayerEvent

class ToolTalismanMemoryTool(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    private val selectedBlockId = propertyHandler.registerIntProperty("selected_block_id", -1, -1, Int.MAX_VALUE, false)
    private val speedBonus = propertyHandler.registerFloatProperty("speed_bonus", 0f, 0f, ConfigTool.memoryTool.maxSpeed, false)

    override fun getMiningSpeed(stack: ItemStack, event: PlayerEvent.BreakSpeed) {
        event.newSpeed += stack.get(speedBonus)
    }

    override fun performActionServer(context: CastContext) {
        val block = context.world.getBlock(context.pos)
        val blockId = Block.getIdFromBlock(block)
        val oldBlockId = context.stack.get(selectedBlockId)
        if (blockId == oldBlockId) {
            val bonus = context.stack.get(speedBonus)
            context.stack.set(speedBonus, bonus + ConfigTool.memoryTool.speedIncrease, true)
        } else {
            context.stack.set(selectedBlockId, blockId, true)
            context.stack.set(speedBonus, 0f, true)
        }
    }
}
