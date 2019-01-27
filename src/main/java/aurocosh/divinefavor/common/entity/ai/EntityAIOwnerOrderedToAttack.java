package aurocosh.divinefavor.common.entity.ai;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.base.MinionData;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAITarget;

public class EntityAIOwnerOrderedToAttack<T extends EntityCreature & IMinion> extends EntityAITarget {
    private T minion;
    private MinionData minionData;
    private EntityLivingBase orderedTarget;

    public EntityAIOwnerOrderedToAttack(T minion) {
        super(minion, false);
        this.minion = minion;
        setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        minionData = minion.getMinionData();
        EntityLivingBase owner = minionData.getOwner();
        if (owner == null)
            return false;
        EntityLivingBase attackTarget = minionData.getAttackTarget();
        if(attackTarget == null)
            return false;
        if(attackTarget == minion)
            return false;
        return isSuitableTarget(attackTarget, false);
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        EntityLivingBase attackTarget = minionData.getAttackTarget();
        taskOwner.setAttackTarget(attackTarget);
        orderedTarget = attackTarget;
        super.startExecuting();
    }

    @Override
    public boolean shouldContinueExecuting() {
        if(minionData.getAttackTarget() != orderedTarget)
            return false;
        return super.shouldContinueExecuting();
    }
}