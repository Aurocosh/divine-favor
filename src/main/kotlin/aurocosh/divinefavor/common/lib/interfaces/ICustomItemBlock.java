package aurocosh.divinefavor.common.lib.interfaces;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public interface ICustomItemBlock {
    /**
     * Returns a custom item for this block.
     *
     * @return
     */
    @Nullable
    default ItemBlock getItemBlock() {
        return getDefaultItemBlock((Block) this);
    }

    /**
     * Returns the default item for the specified block
     * @param block
     * @return
     */
    static ItemBlock getDefaultItemBlock(Block block) {
        if (Item.getItemFromBlock(block) != Items.AIR)
            return (ItemBlock) Item.getItemFromBlock(block);
        else
            return new ItemBlock(block);
    }

    /**
     * Returns which item this block should be rendered as
     *
     * @return
     */
    @SideOnly(Side.CLIENT)
    default ItemStack getRenderedItem() {
        return ItemStack.EMPTY;
    }
}
