package aurocosh.divinefavor.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class UtilPlayer {
    public static ItemStack getStackFromHands(EntityPlayer player, UtilList.Predicate<ItemStack> predicate){
        ItemStack stack = player.getHeldItemMainhand();
        if(predicate.select(stack))
            return stack;
        stack = player.getHeldItemOffhand();
        if(predicate.select(stack))
            return stack;
        return ItemStack.EMPTY;
    }
}
