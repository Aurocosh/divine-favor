package aurocosh.divinefavor.common.entity.ai

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.EntityAIBase
import java.util.function.BooleanSupplier

class EntityAIMinionWait<T>
/** If the EntityTameable is sitting.  */
(private val minion: T, private val shouldWait: BooleanSupplier) : EntityAIBase() where T : EntityLiving, T : IMinion {

    init {
        mutexBits = 5
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    override fun shouldExecute(): Boolean {
        if (!minion.minionData.hasOwner())
            return false
        else if (minion.isInWater)
            return false
        else if (!minion.onGround)
            return false
        return shouldWait.asBoolean
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    override fun startExecuting() {
        minion.navigator.clearPath()
    }

    override fun shouldContinueExecuting(): Boolean {
        return shouldWait.asBoolean
    }
}
