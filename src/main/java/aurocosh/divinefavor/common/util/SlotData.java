package aurocosh.divinefavor.common.util;

import net.minecraft.item.ItemStack;

public class SlotData {
    public final int slotIndex;
    public final ItemStack stack;

    public SlotData(int slotIndex, ItemStack stack) {
        this.slotIndex = slotIndex;
        this.stack = stack;
    }

    public boolean isValid(){
        return slotIndex > 0 || slotIndex < InventoryIndexes.Offhand.getValue();
    }
}
