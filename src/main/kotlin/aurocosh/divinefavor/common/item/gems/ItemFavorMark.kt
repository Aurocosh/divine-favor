package aurocosh.divinefavor.common.item.gems

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.extensions.divinePlayerData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavorInfinite
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.client.resources.I18n
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.EnumActionResult
import net.minecraft.util.EnumHand
import net.minecraft.world.World
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class ItemFavorMark(val spirit: ModSpirit, orderIndex: Int) : ModItem("favor_mark_" + spirit.name, "favor_marks/" + spirit.name, orderIndex) {

    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun onItemRightClick(world: World?, player: EntityPlayer, hand: EnumHand): ActionResult<ItemStack> {
        val stack = player.getHeldItem(hand)
        val result = if (addFavor(player, stack)) EnumActionResult.SUCCESS else EnumActionResult.PASS
        return ActionResult(result, stack)
    }

    private fun addFavor(player: EntityPlayer, stack: ItemStack): Boolean {
        if (player.world.isRemote)
            return false
        if (stack.item !is ItemFavorMark)
            return false
        val spiritData = player.divinePlayerData.spiritData
        spiritData.toggleFavorInfinite(spirit.id)
        MessageSyncFavorInfinite(spirit, spiritData).sendTo(player)
        stack.shrink(1)
        return false
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack?, world: World?, tooltip: MutableList<String>?, flag: ITooltipFlag?) {
        super.addInformation(stack!!, world, tooltip!!, flag!!)
        val favorMark = stack.item as ItemFavorMark
        val name = I18n.format(favorMark.spirit.nameTranslationKey)

        val message = I18n.format("tooltip.divinefavor:favor_mark.spirit", name)
        tooltip.add(message)
    }
}