package aurocosh.divinefavor.common.core.creative_tabs

import aurocosh.divinefavor.common.item.common.ModArrowTalismans
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList

class DivineFavorCreativeTabArrowTalismans : DivineFavorCreativeTab("arrow_talismans", { ModArrowTalismans.fill_lungs }) {
    private lateinit var list: NonNullList<ItemStack>
}