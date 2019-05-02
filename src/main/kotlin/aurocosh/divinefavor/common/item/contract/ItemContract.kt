package aurocosh.divinefavor.common.item.contract

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem
import net.minecraft.item.EnumRarity
import net.minecraft.item.ItemStack

open class ItemContract(name: String, texturePath: String) : ModItem("contract_$name", "contracts/$texturePath", ConstMainTabOrder.CONTRACTS) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }

    override fun getRarity(stack: ItemStack): EnumRarity {
        return EnumRarity.RARE
    }
}
