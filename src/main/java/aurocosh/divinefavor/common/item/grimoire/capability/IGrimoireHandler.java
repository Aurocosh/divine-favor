package aurocosh.divinefavor.common.item.grimoire.capability;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public interface IGrimoireHandler {
    int getSelectedSlotIndex();
    void setSelectedSlotIndex(int index);

    void switchToNext();
    void switchToPrevious();

    ItemStack getSelectedStack();
    List<ItemStack> getPreviousStacks();
    List<ItemStack> getNextStacks();
    List<ItemStack> getAllStacks();

    ItemStackHandler getStackHandler();

    int getState();
}
