package aurocosh.divinefavor.common.item.talisman_container;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;
import java.util.function.Predicate;

public interface ITalismanContainer {
    int getSelectedSlotIndex();
    void setSelectedSlotIndex(int index);

    void switchToNext();
    void switchToPrevious();

    int getSlotCount();

    ItemStack getSelectedStack();
    ItemStack getStackInSlot(int slot);
    List<ItemStack> getPreviousStacks();
    List<ItemStack> getNextStacks();
    List<ItemStack> getAllStacks();
    List<Integer> getSlotIndexes(Predicate<ItemStack> predicate);

    ItemStackHandler getStackHandler();

    int getState();
}
