package aurocosh.divinefavor.common.util;

import aurocosh.divinefavor.common.misc.SlotStack;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

import java.util.ArrayList;
import java.util.List;

public class UtilHandler {
    public static List<SlotStack> getNotEmptyStacksWithSlotIndexes(IItemHandler itemHandler){
        List<SlotStack> stackList = new ArrayList<>();
        int stackCount = itemHandler.getSlots();

        for (int i = 0; i < stackCount; i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if(stack != ItemStack.EMPTY)
                stackList.add(new SlotStack(i,stack));
        }
        return stackList;
    }

    public static List<ItemStack> getNotEmptyStacks(IItemHandler itemHandler){
        int stackCount = itemHandler.getSlots();
        List<ItemStack> stackList = new ArrayList<>(stackCount);

        for (int i = 0; i < stackCount; i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if(stack != ItemStack.EMPTY)
                stackList.add(stack);
        }
        return stackList;
    }

    public static List<ItemStack> getAllStacks(IItemHandler itemHandler){
        int stackCount = itemHandler.getSlots();
        List<ItemStack> stackList = new ArrayList<>(stackCount);

        for (int i = 0; i < stackCount; i++)
            stackList.add(itemHandler.getStackInSlot(i));
        return stackList;
    }
}
