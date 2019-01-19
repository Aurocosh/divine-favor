package aurocosh.divinefavor.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

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

    public static EnumHand getHand(UtilList.Predicate<EnumHand> predicate){
        if(predicate.select(EnumHand.MAIN_HAND))
            return EnumHand.MAIN_HAND;
        if(predicate.select(EnumHand.OFF_HAND))
            return EnumHand.OFF_HAND;
        return null;
    }
}
