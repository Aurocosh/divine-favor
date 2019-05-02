package aurocosh.divinefavor.common.entity.minions.behaviour.base

import aurocosh.divinefavor.common.entity.minions.base.IMinion
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.EntityAITasks

abstract class MinionBehaviour<T> where T : IMinion, T : EntityLiving {
    abstract fun apply(minion: T, tasks: EntityAITasks, targetTasks: EntityAITasks)
}
