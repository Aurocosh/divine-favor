package aurocosh.divinefavor.common.item.talisman_container.spell_bow.capability;

import aurocosh.divinefavor.common.item.talisman_container.spell_bow.ItemSpellBow;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.util.UtilMath;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

// The default implementation of the capability. Holds all the logic.
public class DefaultSpellBowHandler implements ISpellBowHandler {
    private final int MAX_INDEX = ItemSpellBow.SIZE - 1;
    private final int STACKS_DISPLAYED = 3;

    private int state;
    private int selectedSlotIndex;
    private final ItemStackHandler inventory;

    public DefaultSpellBowHandler() {
        state = 0;
        selectedSlotIndex = 0;
        inventory = new ItemStackHandler(ItemSpellBow.SIZE) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return stack.getItem() instanceof ItemArrowTalisman;
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
    public ItemStack getStackInSlot(int slot) {
        return inventory.getStackInSlot(slot);
    }

    @Override
    public List<Integer> getSlotIndexes(Predicate<ItemStack> predicate) {
        List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < inventory.getSlots(); i++) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (predicate.test(stack))
                indexes.add(i);
        }
        return indexes;
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
    public int getSlotCount() {
        return inventory.getSlots();
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

    @Override
    public List<ItemStack> getAllStacks() {
        List<ItemStack> stacks = new ArrayList<>();
        for (int i = 0; i < inventory.getSlots(); i++)
            stacks.add(inventory.getStackInSlot(i));
        return stacks;
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
