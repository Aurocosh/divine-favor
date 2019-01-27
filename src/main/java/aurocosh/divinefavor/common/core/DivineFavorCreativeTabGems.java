package aurocosh.divinefavor.common.core;

import aurocosh.divinefavor.common.constants.ConstMisc;
import aurocosh.divinefavor.common.item.calling_stones.ItemCallingStone;
import aurocosh.divinefavor.common.item.calling_stones.ModCallingStones;
import aurocosh.divinefavor.common.registry.ModRegistries;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Comparator;

public class DivineFavorCreativeTabGems extends CreativeTabs {
    public static final DivineFavorCreativeTabGems INSTANCE = new DivineFavorCreativeTabGems();
    private NonNullList list;

    private static Comparator<ItemStack> tabComp = (first, second) -> {
        Item firstItem = first.getItem();
        Item secondItem = second.getItem();

        boolean isCallingStone1 = firstItem instanceof ItemCallingStone;
        boolean isCallingStone2 = secondItem instanceof ItemCallingStone;

        if(isCallingStone1 && !isCallingStone2)
            return -1;
        if(!isCallingStone1 && isCallingStone2)
            return 1;
        if(isCallingStone1 && isCallingStone2)
            return firstItem.getRegistryName().toString().compareTo(secondItem.getRegistryName().toString());

//            if(firstItem instanceof ItemWishingStone && secondItem instanceof ItemWishingStone){
//                ItemWishingStone itemW1 = (ItemWishingStone) firstItem;
//                ItemWishingStone itemW2 = (ItemWishingStone) secondItem;
//                int cmp = itemW1.getSpirit().getName().compareToIgnoreCase(itemW2.getSpirit().getName());
//                if(cmp != 0)
//                    return cmp;
//            }
        return firstItem.getRegistryName().toString().compareTo(secondItem.getRegistryName().toString());
    };
    public DivineFavorCreativeTabGems() {
        super(ConstMisc.MOD_ID + "_gems");
        //ConstMisc.MOD_ID

        //setNoTitle();
        //setBackgroundImageName(ConstResources.GUI_CREATIVE);
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(ModCallingStones.calling_stone_allfire);
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

        ModRegistries.blocks.getValues().forEach(this::addBlock);
        ModRegistries.items.getValues().forEach(this::addItem);
        stacks.sort(tabComp);
    }

    private void addItem(Item item) {
        item.getSubItems(this, list);
    }

    private void addBlock(Block block) {
        addItem(Item.getItemFromBlock(block));
    }
}