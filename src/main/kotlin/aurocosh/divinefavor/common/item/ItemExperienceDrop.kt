package aurocosh.divinefavor.common.item

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.config.common.ConfigSpell
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.util.UtilExperience
import aurocosh.divinefavor.common.util.UtilItemStack.actionResult
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumFacing
import net.minecraft.util.EnumHand
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

open class ItemExperienceDrop : ModItem(name, name, ConstGemTabOrder.OTHER_GEMS) {

    init {
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun onItemUse(player: EntityPlayer, world: World, pos: BlockPos, hand: EnumHand, facing: EnumFacing, hitX: Float, hitY: Float, hitZ: Float): EnumActionResult {

        return EnumActionResult.PASS
    }

    override fun onItemRightClick(world: World?, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        return actionResult(addExperience(player, stack), stack)
    }

    private fun addExperience(player: EntityPlayer, stack: ItemStack): Boolean {
        if (player.world.isRemote)
            return false

        val count =  if(player.isSneaking) stack.count else 1
        UtilExperience.addPlayerXP(player, ConfigSpell.crystallizeExperience.experiencePerCast * count)
        stack.shrink(count)
        return false
    }

    companion object {
        private const val name = "experience_drop"
    }
}

