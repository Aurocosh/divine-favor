package aurocosh.divinefavor.common.entity.minions.minion_interaction;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.item.ItemBanishingWand;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class MinionBanishing<T extends EntityLiving & IMinion> extends MinionInteraction<T> {
    public boolean process(T minion, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.isEmpty())
            return false;
        Item item = stack.getItem();
        if (item instanceof ItemBanishingWand) {
            minion.setDead();
            return true;
        }
        return false;
    }
}
