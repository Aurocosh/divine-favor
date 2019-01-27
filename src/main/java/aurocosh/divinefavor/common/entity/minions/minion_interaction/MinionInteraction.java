package aurocosh.divinefavor.common.entity.minions.minion_interaction;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public abstract class MinionInteraction<T extends EntityLiving & IMinion> {
    public abstract boolean process(T minion, EntityPlayer player, EnumHand hand);
}
