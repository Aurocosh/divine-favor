package aurocosh.divinefavor.common.item.gems

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.block.medium.MediumStone
import aurocosh.divinefavor.common.constants.ConstGemTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.item.gems.base.IMediumGemItem
import aurocosh.divinefavor.common.lib.extensions.cap
import aurocosh.divinefavor.common.lib.iterators.IItemHandlerSlot
import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.receipes.common.ModMediumRecipes
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack
import net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY
import net.minecraftforge.items.ItemStackHandler

class ItemCallingStone(name: String, override val spirit: ModSpirit, override val multiBlock: ModMultiBlock, override val stoneType: MediumStone) : ModItem("calling_stone_$name", "calling_stones/$name", ConstGemTabOrder.CALLING_STONE), IMediumGemItem {
    override val acceptsOfferings get() = true

    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_GEMS
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }

    override fun exchangeItems(slotStacks: List<IItemHandlerSlot>, leftHandler: ItemStackHandler, rightHandler: ItemStackHandler) {
        val pouchHandlers = slotStacks
                .filter { it.stack.item === ModItems.ritual_pouch }
                .map { it.stack.cap(ITEM_HANDLER_CAPABILITY) }

        pouchHandlers.forEach { ModMediumRecipes.exchangeRecipe(this, it, slotIndexesPouch) }

        ModMediumRecipes.exchangeRecipe(this, leftHandler, slotIndexesAltar)
        ModMediumRecipes.exchangeRecipe(this, rightHandler, slotIndexesAltar)
    }

    companion object {
        private val slotIndexesPouch = listOf(3, 1, 5, 0, 2, 4, 6)
        private val slotIndexesAltar = listOf(4, 1, 3, 5, 7, 0, 2, 6, 8)
    }
}