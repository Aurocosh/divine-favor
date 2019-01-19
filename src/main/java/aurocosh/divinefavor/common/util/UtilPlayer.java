package aurocosh.divinefavor.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

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

    public static EnumHand getHand(UtilList.Predicate<EnumHand> predicate) {
        if (predicate.select(EnumHand.MAIN_HAND))
            return EnumHand.MAIN_HAND;
        if (predicate.select(EnumHand.OFF_HAND))
            return EnumHand.OFF_HAND;
        return null;
    }

    public static ItemStack findStackInInventory(EntityPlayer player, UtilList.Predicate<ItemStack> predicate) {
        ItemStack stack = player.getHeldItemMainhand();
        if (predicate.select(stack))
            return stack;
        stack = player.getHeldItemOffhand();
        if (predicate.select(stack))
            return stack;
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            stack = player.inventory.getStackInSlot(i);
            if (predicate.select(stack))
                return stack;
        }
        return ItemStack.EMPTY;
    }

    public static void damageStack(EntityPlayer player, ItemStack stack) {
        stack.shrink(1);
        if (stack.isEmpty())
            player.inventory.deleteStack(stack);
    }
}
