package aurocosh.divinefavor.common.entity.minions.minion_interaction;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.base.MinionData;
import aurocosh.divinefavor.common.entity.minions.base.MinionMode;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class MinionWaitSwitch<T extends EntityLiving & IMinion> extends MinionInteraction<T> {
    public boolean process(T minion, EntityPlayer player, EnumHand hand) {
        if(hand != EnumHand.MAIN_HAND)
            return false;
        if (!player.getHeldItemMainhand().isEmpty())
            return false;
        MinionData minionData = minion.getMinionData();
        if (!minionData.isOwner(player))
            return false;
        minionData.setMode(minionData.getMode() != MinionMode.Normal ? MinionMode.Normal : MinionMode.Wait);
        minion.setAttackTarget(null);
        return true;
    }
}
