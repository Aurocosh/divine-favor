package aurocosh.divinefavor.common.core;

import aurocosh.divinefavor.common.block.base.ModBlocks;
import aurocosh.divinefavor.common.item.base.ModItems;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class DivineFavorCreativeTab extends CreativeTabs {
    public static DivineFavorCreativeTab INSTANCE = new DivineFavorCreativeTab();
    NonNullList list;

    public DivineFavorCreativeTab() {
        super("Divine Favor");
        //LibMisc.MOD_ID

        //setNoTitle();
        //setBackgroundImageName(LibResources.GUI_CREATIVE);
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(ModBlocks.blockFastFurnace);
    }

    @Override
    public ItemStack getTabIconItem() {
        return getIconItemStack();
    }

    @Override
    public boolean hasSearchBar() {
        return true;
    }

    @Override
    public void displayAllRelevantItems(NonNullList<ItemStack> p_78018_1_) {
        list = p_78018_1_;

        addBlock(ModBlocks.blockFastFurnace);
        addBlock(ModBlocks.blockIronMedium);

        addItem(ModItems.arrow_throw_talisman);
        addItem(ModItems.bonemeal_talisman);
        addItem(ModItems.ignition_talisman);
        addItem(ModItems.snowball_throw_talisman);
    }

    private void addItem(Item item) {
        item.getSubItems(this, list);
    }

    private void addBlock(Block block) {
        addItem(Item.getItemFromBlock(block));
    }

}