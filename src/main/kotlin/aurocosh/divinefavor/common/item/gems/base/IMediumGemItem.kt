package aurocosh.divinefavor.common.item.gems.base

import aurocosh.divinefavor.common.block.medium.MediumStone
import aurocosh.divinefavor.common.lib.iterators.IItemHandlerSlot
import aurocosh.divinefavor.common.muliblock.ModMultiBlock
import aurocosh.divinefavor.common.spirit.base.ModSpirit
import net.minecraftforge.items.ItemStackHandler

interface IMediumGemItem {
    val spirit: ModSpirit
    val stoneType: MediumStone
    val multiBlock: ModMultiBlock
    val acceptsOfferings : Boolean

    fun exchangeItems(slotStacks: List<IItemHandlerSlot>, leftHandler: ItemStackHandler, rightHandler: ItemStackHandler)
}