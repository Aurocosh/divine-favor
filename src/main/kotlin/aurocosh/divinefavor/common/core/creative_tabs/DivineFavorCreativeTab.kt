package aurocosh.divinefavor.common.core.creative_tabs

import aurocosh.divinefavor.common.core.ResourceNamer
import aurocosh.divinefavor.common.item.base.ModItem
import aurocosh.divinefavor.common.registry.ModRegistries
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

open class DivineFavorCreativeTab(name: String, val itemGetter: () -> ModItem) : CreativeTabs(ResourceNamer.getFullName("tab", name).toString()) {
    private lateinit var list: NonNullList<ItemStack>

    override fun createIcon(): ItemStack {
//        return ItemStack(ModItems.grimoire)
        return ItemStack(itemGetter.invoke())
    }

    override fun hasSearchBar(): Boolean {
        return false
    }

    @SideOnly(Side.CLIENT)
    override fun displayAllRelevantItems(stacks: NonNullList<ItemStack>) {
        list = stacks

        ModRegistries.blocks.values.forEach(this::addBlock)
        ModRegistries.items.values.forEach(this::addItem)
        ModRegistries.arrows.values.forEach(this::addItem)
        stacks.sortWith(ModItemStackComparator())
    }

    private fun addItem(item: Item) {
        item.getSubItems(this, list)
    }

    private fun addBlock(block: Block) {
        addItem(Item.getItemFromBlock(block))
    }
}
//DivineFavor.MOD_ID
//setNoTitle();
//setBackgroundImageName(ConstResources.GUI_CREATIVE);