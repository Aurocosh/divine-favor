package aurocosh.divinefavor.common.item.tools

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.constants.ConstMainTabOrder
import aurocosh.divinefavor.common.item.base.ModItem

class ItemBanishingWand : ModItem("banishing_wand", "banishing_wand", ConstMainTabOrder.TOOLS) {
    init {
        setMaxStackSize(1)
        creativeTab = DivineFavor.TAB_MAIN
    }
}