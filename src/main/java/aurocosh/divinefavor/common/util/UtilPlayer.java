package aurocosh.divinefavor.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class UtilPlayer {
    public static ItemStack findHeldStackHands(EntityPlayer player, UtilList.Predicate<ItemStack> predicate) {
        ItemStack stack = player.getHeldItemMainhand();
        if (predicate.select(stack))
            return stack;
        stack = player.getHeldItemOffhand();
        if (predicate.select(stack))
            return stack;
        return ItemStack.EMPTY;
    }

    public static ItemStack getItemInHand(EntityPlayer player, UtilList.Predicate<Item> predicate) {
        ItemStack stack = player.getHeldItemMainhand();
        if (!stack.isEmpty() && predicate.select(stack.getItem()))
            return stack;
        stack = player.getHeldItemOffhand();
        if (!stack.isEmpty() && predicate.select(stack.getItem()))
            return stack;
        return ItemStack.EMPTY;
    }

    public static EnumHand getHandWithItem(EntityPlayer player, UtilList.Predicate<Item> predicate) {
        ItemStack stack = player.getHeldItemMainhand();
        if (!stack.isEmpty() && predicate.select(stack.getItem()))
            return EnumHand.MAIN_HAND;
        stack = player.getHeldItemOffhand();
        if (!stack.isEmpty() && predicate.select(stack.getItem()))
            return EnumHand.OFF_HAND;
        return null;
    }

    public static EnumHand getHand(UtilList.Predicate<EnumHand> predicate) {
        if (predicate.select(EnumHand.MAIN_HAND))
            return EnumHand.MAIN_HAND;
        if (predicate.select(EnumHand.OFF_HAND))
            return EnumHand.OFF_HAND;
        return null;
    }

    public static SlotData findStackInMainInventory(EntityPlayer player, UtilList.Predicate<ItemStack> predicate) {
        for (int i = 9; i < player.inventory.getSizeInventory(); i++) {
            ItemStack stack = player.inventory.getStackInSlot(i);
            if (predicate.select(stack))
                return new SlotData(i, stack);
        }
        return new SlotData(-1, ItemStack.EMPTY);
    }

    public static SlotData findStackInInventory(EntityPlayer player, UtilList.Predicate<ItemStack> predicate) {
        ItemStack stack = player.getHeldItemMainhand();
        if (predicate.select(stack))
            return new SlotData(player.inventory.currentItem, stack);
        stack = player.getHeldItemOffhand();
        if (predicate.select(stack))
            return new SlotData(InventoryIndexes.Offhand.getValue(), stack);
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            stack = player.inventory.getStackInSlot(i);
            if (predicate.select(stack))
                return new SlotData(i, stack);
        }
        return new SlotData(-1, ItemStack.EMPTY);
    }

    public static void swapStacks(EntityPlayer player, int firstSlot, int secondSlot) {
        ItemStack firstStack = player.inventory.getStackInSlot(firstSlot);
        ItemStack secondStack = player.inventory.getStackInSlot(secondSlot);
        player.inventory.setInventorySlotContents(firstSlot, secondStack);
        player.inventory.setInventorySlotContents(secondSlot, firstStack);
    }

    public static void damageStack(EntityPlayer player, ItemStack stack) {
        stack.shrink(1);
        if (stack.isEmpty())
            player.inventory.deleteStack(stack);
    }

    public static EnumHand getOtherHand(EnumHand hand) {
        return hand == EnumHand.MAIN_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
    }

    public static int getHandIndex(EntityPlayer player, EnumHand hand) {
        return hand == EnumHand.MAIN_HAND ? player.inventory.currentItem : InventoryIndexes.Offhand.getValue();
    }
}
