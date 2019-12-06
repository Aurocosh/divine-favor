package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.config.common.ConfigTool
import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.state.IBlockState
import net.minecraft.item.ItemStack
import net.minecraftforge.event.entity.player.PlayerEvent

class ToolTalismanVoidTool(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun isCustomToolClasses(stack: ItemStack) = true
    override fun getCustomToolClasses(stack: ItemStack) = emptySet<String>()
    override fun canHarvest(stack: ItemStack, state: IBlockState, toolCanHarvest: Boolean) = false
    override fun shouldBreakBlock(context: CastContext) = false

    override fun performActionServer(context: CastContext) {
        val (player, world, stack, pos) = context.get(playerField, worldField, containerStackField, posField)
        UtilBlock.removeBlock(player, world, stack, pos, false, false, true)
        stack.damageItem(1, player)
    }

    override fun getMiningSpeed(stack: ItemStack, event: PlayerEvent.BreakSpeed) {
        event.newSpeed += ConfigTool.voidTool.miningSpeed
    }
}
