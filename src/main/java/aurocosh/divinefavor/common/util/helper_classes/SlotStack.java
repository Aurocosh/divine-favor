package aurocosh.divinefavor.common.util.helper_classes;

import net.minecraft.item.ItemStack;

public class SlotStack {
    private int index;
    private ItemStack stack;

    public int getIndex() {
        return index;
    }

    public ItemStack getStack() {
        return stack;
    }

    public SlotStack(int index, ItemStack stack) {
        this.index = index;
        this.stack = stack;
    }
}
