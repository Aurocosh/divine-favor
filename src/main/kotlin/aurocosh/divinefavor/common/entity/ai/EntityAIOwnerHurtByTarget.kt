package aurocosh.divinefavor.common.entity.ai

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import net.minecraft.entity.EntityCreature
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.ai.EntityAITarget

class EntityAIOwnerHurtByTarget<T>(private val minion: T) : EntityAITarget(minion, false) where T : EntityCreature, T : IMinion {
    private var attacker: EntityLivingBase? = null
    private var timestamp: Int = 0

    init {
        mutexBits = 1
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    override fun shouldExecute(): Boolean {
        val owner = minion.minionData.owner ?: return false
        attacker = owner.attackingEntity
        val i = owner.revengeTimer
        return i != timestamp && isSuitableTarget(attacker, false)
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    override fun startExecuting() {
        taskOwner.attackTarget = attacker
        val owner = minion.minionData.owner
        if (owner != null)
            timestamp = owner.revengeTimer
        super.startExecuting()
    }
}
