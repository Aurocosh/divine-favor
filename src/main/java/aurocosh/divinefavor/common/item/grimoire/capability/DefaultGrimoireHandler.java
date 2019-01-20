package aurocosh.divinefavor.common.item.grimoire.capability;

import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.util.UtilMath;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// The default implementation of the capability. Holds all the logic.
public class DefaultGrimoireHandler implements IGrimoireHandler {

    private final int SLOT_COUNT = 27;
    private final int MAX_INDEX = SLOT_COUNT - 1;
    private final int STACKS_DISPLAYED = 3;

    private int state;
    private int selectedSlotIndex;
    private final ItemStackHandler inventory;

    public DefaultGrimoireHandler() {
        state = 0;
        selectedSlotIndex = 0;
        inventory = new ItemStackHandler(SLOT_COUNT) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof ItemSpellTalisman;
            }

            @Override
            protected void onContentsChanged(int slot) {
                super.onContentsChanged(slot);
                state++;
            }
        };
    }

    @Override
    public int getSelectedSlotIndex() {
        return selectedSlotIndex;
    }

    @Override
    public void setSelectedSlotIndex(int index) {
        selectedSlotIndex = UtilMath.clamp(index, 0, MAX_INDEX);
        state++;
    }

    @Override
    public ItemStack getSelectedStack() {
        return inventory.getStackInSlot(selectedSlotIndex);
    }

    @Override
    public void switchToNext() {
        setSelectedSlotIndex(getNonEmptyIndex(selectedSlotIndex, this::nextIndex));
    }

    @Override
    public void switchToPrevious() {
        setSelectedSlotIndex(getNonEmptyIndex(selectedSlotIndex, this::previousIndex));
    }

    @Override
    public ItemStackHandler getStackHandler() {
        return inventory;
    }

    @Override
    public int getState() {
        return state;
    }

    @Override
    public List<ItemStack> getPreviousStacks() {
        return getStacks(STACKS_DISPLAYED, this::previousIndex);
    }

    public List<ItemStack> getNextStacks() {
        return getStacks(STACKS_DISPLAYED, this::nextIndex);
    }

    public List<ItemStack> getStacks(int count, Indexer indexer) {
        List<ItemStack> stacks = new ArrayList<>(count);
        int index = selectedSlotIndex;
        do {
            index = getNonEmptyIndex(index, indexer);
            if (index != selectedSlotIndex)
                stacks.add(inventory.getStackInSlot(index));
        } while (count-- > 0 && index != selectedSlotIndex);
        return Collections.unmodifiableList(stacks);
    }

    private int getNonEmptyIndex(int index, Indexer indexer) {
        int newIndex = index;
        do {
            newIndex = indexer.next(newIndex);
            ItemStack stack = inventory.getStackInSlot(newIndex);
            if (!stack.isEmpty())
                return newIndex;
        } while (newIndex != index);
        return index;
    }

    private int nextIndex(int index) {
        index++;
        if (index > MAX_INDEX)
            index = 0;
        return index;
    }

    private int previousIndex(int index) {
        index--;
        if (index < 0)
            index = MAX_INDEX;
        return index;
    }

    @FunctionalInterface
    public interface Indexer {
        int next(int index);
    }
}
