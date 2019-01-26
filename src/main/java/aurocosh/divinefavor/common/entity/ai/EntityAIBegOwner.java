package aurocosh.divinefavor.common.entity.ai;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

public class EntityAIBegOwner<T extends EntityLiving & IMinion> extends EntityAIBeg<T> {
    public EntityAIBegOwner(T minion, float minDistance, Item... wantedItems) {
        super(minion, minDistance, wantedItems);
    }

    @Override
    protected boolean wantSomethingFromPlayer(EntityPlayer player) {
        return minion.getMinionData().isOwner(player) && super.wantSomethingFromPlayer(player);
    }
}
