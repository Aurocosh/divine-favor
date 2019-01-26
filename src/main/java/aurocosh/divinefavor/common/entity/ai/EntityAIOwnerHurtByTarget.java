package aurocosh.divinefavor.common.entity.ai;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIOwnerHurtByTarget<T extends EntityCreature & IMinion> extends EntityAITarget {
    private T minion;
    private EntityLivingBase attacker;
    private int timestamp;

    public EntityAIOwnerHurtByTarget(T minion) {
        super(minion, false);
        this.minion = minion;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        EntityLivingBase owner = minion.getMinionData().getOwner();
        if (owner == null)
            return false;
        attacker = owner.getAttackingEntity();
        int i = owner.getRevengeTimer();
        return i != timestamp && isSuitableTarget(attacker, false);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        taskOwner.setAttackTarget(attacker);
        EntityLivingBase owner = minion.getMinionData().getOwner();
        if (owner != null)
            timestamp = owner.getRevengeTimer();
        super.startExecuting();
    }
}
