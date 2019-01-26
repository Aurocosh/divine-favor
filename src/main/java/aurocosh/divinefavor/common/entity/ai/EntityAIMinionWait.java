package aurocosh.divinefavor.common.entity.ai;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.base.MinionMode;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

public class EntityAIMinionWait<T extends EntityLiving & IMinion> extends EntityAIBase {
    private final T minion;

    /** If the EntityTameable is sitting. */
    public EntityAIMinionWait(T minion) {
        this.minion = minion;
        setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (!minion.getMinionData().hasOwner())
            return false;
        else if (minion.isInWater())
            return false;
        else if (!minion.onGround)
            return false;
        else {
            MinionMode mode = minion.getMinionData().getMode();
            return mode == MinionMode.Wait;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        minion.getNavigator().clearPath();
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
    }
}
