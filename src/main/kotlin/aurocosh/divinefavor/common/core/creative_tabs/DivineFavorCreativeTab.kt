package aurocosh.divinefavor.common.core.creative_tabs

import aurocosh.divinefavor.common.item.common.ModItems
import aurocosh.divinefavor.common.registry.ModRegistries
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.NonNullList
import net.minecraftforge.fml.relauncher.Side
import net.minecraftforge.fml.relauncher.SideOnly

class DivineFavorCreativeTab : CreativeTabs("divinefavor") {
    private lateinit var list: NonNullList<ItemStack>

    override fun createIcon(): ItemStack {
        return ItemStack(ModItems.grimoire)
    }

    //    @Override
    //    public ItemStack getTabIconItem() {
    //        return getIconItemStack();
    //    }

    override fun hasSearchBar(): Boolean {
        return false
    }

    @SideOnly(Side.CLIENT)
    override fun displayAllRelevantItems(stacks: NonNullList<ItemStack>) {
        list = stacks

        ModRegistries.blocks.values.forEach { this.addBlock(it) }
        ModRegistries.items.values.forEach { this.addItem(it) }
        stacks.sortWith(ModItemStackComparator())
    }

    private fun addItem(item: Item) {
        item.getSubItems(this, list)
    }

    private fun addBlock(block: Block) {
        addItem(Item.getItemFromBlock(block))
    }
}
//ConstMisc.MOD_ID
//setNoTitle();
//setBackgroundImageName(ConstResources.GUI_CREATIVE);