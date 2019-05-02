package aurocosh.divinefavor.common.entity.ai

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.base.MinionData
import net.minecraft.entity.EntityCreature
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.ai.EntityAITarget

class EntityAIOwnerOrderedToAttack<T>(minion: T, private val minionData: MinionData<*>) : EntityAITarget(minion, false) where T : EntityCreature, T : IMinion {
    private var orderedTarget: EntityLivingBase? = null

    init {
        mutexBits = 1
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    override fun shouldExecute(): Boolean {
        val owner = minionData.owner ?: return false
        val attackTarget = minionData.attackTarget ?: return false
        return if (attackTarget === taskOwner) false else isSuitableTarget(attackTarget, false)
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    override fun startExecuting() {
        val attackTarget = minionData.attackTarget
        taskOwner.attackTarget = attackTarget
        orderedTarget = attackTarget
        super.startExecuting()
    }

    override fun shouldContinueExecuting(): Boolean {
        return if (minionData.attackTarget !== orderedTarget) false else super.shouldContinueExecuting()
    }
}
