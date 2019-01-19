package aurocosh.divinefavor.common.item.spell_bow.capability;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public interface ISpellBowHandler {
    int getSelectedSlotIndex();
    void setSelectedSlotIndex(int index);

    void switchToNext();
    void switchToPrevious();

    ItemStack getSelectedStack();
    List<ItemStack> getPreviousStacks();
    List<ItemStack> getNextStacks();

    ItemStackHandler getStackHandler();

    int getState();
}
