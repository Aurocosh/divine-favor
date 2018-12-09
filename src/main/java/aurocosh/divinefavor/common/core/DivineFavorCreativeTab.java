package aurocosh.divinefavor.common.core;

import aurocosh.divinefavor.common.block.base.ModBlock;
import aurocosh.divinefavor.common.block.common.ModBlocks;
import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.item.base.ModItem;
import aurocosh.divinefavor.common.item.common.ModItems;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Collection;

public class DivineFavorCreativeTab extends CreativeTabs {
    public static final DivineFavorCreativeTab INSTANCE = new DivineFavorCreativeTab();
    private NonNullList list;

    public DivineFavorCreativeTab() {
        super(ConstMisc.MOD_ID);
        //ConstMisc.MOD_ID

        //setNoTitle();
        //setBackgroundImageName(ConstResources.GUI_CREATIVE);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModBlocks.blockFastFurnace);
    }

//    @Override
//    public ItemStack getTabIconItem() {
//        return getIconItemStack();
//    }

    @Override
    public boolean hasSearchBar() {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void displayAllRelevantItems(NonNullList<ItemStack> stacks) {
        list = stacks;

        Collection<ModBlock> blocks = ModBlocks.getBlocks();
        blocks.forEach(this::addBlock);

        Collection<ModItem> items = ModItems.getItems();
        items.forEach(this::addItem);
    }

    private void addItem(Item item) {
        item.getSubItems(this, list);
    }

    private void addBlock(Block block) {
        addItem(Item.getItemFromBlock(block));
    }
}