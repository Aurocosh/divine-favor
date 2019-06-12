package aurocosh.divinefavor.common.core.creative_tabs

import aurocosh.divinefavor.DivineFavor
import aurocosh.divinefavor.common.item.talismans.common.ModArrowTalismans
import aurocosh.divinefavor.common.registry.ModRegistries
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class DivineFavorCreativeTabArrowTalismans : DivineFavorCreativeTab("arrow_talismans", { ModArrowTalismans.fill_lungs }) {
    private lateinit var list: NonNullList<ItemStack>
}