package aurocosh.divinefavor.common.item.tool_talismans

import aurocosh.divinefavor.common.item.spell_talismans.context.*
import aurocosh.divinefavor.common.item.tool_talismans.base.ItemToolTalisman
import aurocosh.divinefavor.common.lib.extensions.get
import aurocosh.divinefavor.common.lib.extensions.removeEnchantment
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import aurocosh.divinefavor.common.util.UtilBlock
import net.minecraft.block.state.IBlockState
import net.minecraft.enchantment.EnchantmentHelper
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Blocks
import net.minecraft.init.Enchantments
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class ToolTalismanSilkyTool(name: String, spirit: ModSpirit, favorCost: Int) : ItemToolTalisman(name, spirit, favorCost) {
    override fun validate(context: TalismanContext): Boolean {
        return true
    }

    override fun shouldBreakBlock(context: TalismanContext): Boolean {
        return false
    }

    override fun canHarvest(stack: ItemStack, state: IBlockState, toolCanHarvest: Boolean): Boolean {
        return true
    }

    override fun performActionServer(context: TalismanContext) {
        val (world, player, pos, stack) = context.get(worldField, playerField, posField, stackField)
        val state = world.getBlockState(pos);

        val hasSilkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantments.SILK_TOUCH, stack) > 0
        if (!hasSilkTouch)
            stack.addEnchantment(Enchantments.SILK_TOUCH, 1)

        harvestBlock(world, player, pos, state, world.getTileEntity(pos), stack)

        if (!hasSilkTouch)
            stack.removeEnchantment(Enchantments.SILK_TOUCH)
    }

    private fun harvestBlock(world: World, player: EntityPlayer, pos: BlockPos, state: IBlockState, tileEntity: TileEntity?, stack: ItemStack) {
        if (player.capabilities.isCreativeMode) {
            UtilBlock.replaceBlock(player, world, pos, Blocks.AIR.defaultState)
        } else {
            val stackCopy = if (stack.isEmpty) ItemStack.EMPTY else stack.copy()
            val flag = state.block.canHarvestBlock(world, pos, player)

            if (!stack.isEmpty) {
                stack.onBlockDestroyed(world, state, pos, player)
                if (stack.isEmpty) net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, stackCopy, EnumHand.MAIN_HAND)
            }

            val removed = UtilBlock.replaceBlock(player, world, pos, Blocks.AIR.defaultState)
            if (removed && flag) {
                state.block.harvestBlock(world, player, pos, state, tileEntity, stackCopy)
            }
        }
    }
}
