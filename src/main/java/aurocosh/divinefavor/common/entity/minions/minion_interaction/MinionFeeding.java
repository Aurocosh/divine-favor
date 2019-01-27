package aurocosh.divinefavor.common.entity.minions.minion_interaction;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MinionFeeding<T extends EntityLiving & IMinion> extends MinionInteraction<T> {
    private final float healMultiplier;
    private final List<ItemFood> foodList;

    public MinionFeeding(float healMultiplier, Item... foodList) {
        this.healMultiplier = healMultiplier;
        this.foodList = new ArrayList<>(foodList.length);
        for (Item item : foodList)
            if (item instanceof ItemFood)
                this.foodList.add((ItemFood) item);
    }

    public boolean process(T minion, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.isEmpty())
            return false;
        Item item = stack.getItem();
        if (!(item instanceof ItemFood))
            return false;
        ItemFood itemFood = (ItemFood) item;
        if (!foodList.contains(itemFood))
            return false;

        if (!player.capabilities.isCreativeMode)
            stack.shrink(1);
        minion.heal(itemFood.getHealAmount(stack) * healMultiplier);
        return true;
    }
}
