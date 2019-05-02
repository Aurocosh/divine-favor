package aurocosh.divinefavor.common.item.wishing_stones

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.lib.extensions.divineCustomData
import aurocosh.divinefavor.common.network.message.client.spirit_data.MessageSyncFavor
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

class ItemWishingStone(val spirit: ModSpirit, private val favorCount: Int, typeName: String, orderIndex: Int) : ModItem("wishing_stone_" + typeName + "_" + spirit.name, "wishing_stones/" + typeName + "/" + spirit.name, orderIndex) {

    init {
        setMaxStackSize(64)
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
        if (stack.item !is ItemWishingStone)
            return false
        val spiritData = player.divineCustomData.spiritData
        spiritData.addFavor(spirit.id, favorCount)
        MessageSyncFavor(spirit, spiritData).sendTo(player)
        stack.shrink(1)
        return false
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    @SideOnly(Side.CLIENT)
    override fun addInformation(stack: ItemStack?, world: World?, tooltip: MutableList<String>?, flag: ITooltipFlag?) {
        super.addInformation(stack!!, world, tooltip!!, flag!!)
        val wishingStone = stack.item as ItemWishingStone
        val favorCount = wishingStone.favorCount
        val spirit = wishingStone.spirit
        val name = I18n.format(spirit.nameTranslationKey)

        val message = I18n.format("tooltip.divinefavor:wishingStone.favor_count", favorCount, name)
        tooltip.add(message)
    }
}