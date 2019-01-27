package aurocosh.divinefavor.common.entity.ai;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;

import javax.annotation.Nonnull;
import java.util.function.BooleanSupplier;

public class EntityAIMinionWait<T extends EntityLiving & IMinion> extends EntityAIBase {
    private final T minion;
    private final BooleanSupplier shouldWait;

    /** If the EntityTameable is sitting. */
    public EntityAIMinionWait(T minion, @Nonnull final BooleanSupplier shouldWait) {
        this.minion = minion;
        this.shouldWait = shouldWait;
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
        return shouldWait.getAsBoolean();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        minion.getNavigator().clearPath();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return shouldWait.getAsBoolean();
    }
}
