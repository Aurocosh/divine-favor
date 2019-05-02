package aurocosh.divinefavor.common.item.calling_stones

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack

class ItemCallingStone(name: String, val spirit: ModSpirit, val multiBlock: ModMultiBlock) : ModItem("calling_stone_$name", "calling_stones/$name", ConstGemTabOrder.CALLING_STONE) {

    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }
}