package aurocosh.divinefavor.common.entity.minions.behaviour

import aurocosh.divinefavor.common.entity.ai.EntityAIFollowOwner
import aurocosh.divinefavor.common.entity.ai.EntityAIMinionWait
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerHurtByTarget
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerOrderedToAttack
import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.behaviour.base.MinionBehaviour
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.*
import net.minecraft.entity.monster.EntityCreeper
import net.minecraft.entity.passive.EntityOcelot
import net.minecraft.entity.player.EntityPlayer

class MinionBehaviourCreeper<T> : MinionBehaviour<T>() where T : IMinion, T : EntityCreeper {
    override fun apply(minion: T, tasks: EntityAITasks, targetTasks: EntityAITasks) {
        val minionData = minion.minionData

        tasks.addTask(0, EntityAISwimming(minion))
        tasks.addTask(1, EntityAICreeperSwell(minion))
        tasks.addTask(2, EntityAIAvoidEntity(minion, EntityOcelot::class.java, 6.0f, 1.0, 1.2))
        tasks.addTask(3, EntityAIFollowOwner(minion, 2.0, 5.0f, 2.0f, true, { minionData.isFollowing }))
        tasks.addTask(4, EntityAIMinionWait(minion, { minionData.isFollowing || minionData.isWaiting }))
        tasks.addTask(5, EntityAIAttackMelee(minion, 1.0, false))
        tasks.addTask(6, EntityAIFollowOwner(minion, 2.0, 5.0f, 2.0f, false, { true }))
        tasks.addTask(7, EntityAIWanderAvoidWater(minion, 0.8))
        tasks.addTask(8, EntityAIWatchClosest(minion, EntityPlayer::class.java, 8.0f))
        tasks.addTask(8, EntityAILookIdle(minion))

        targetTasks.addTask(1, EntityAIOwnerHurtByTarget(minion))
        targetTasks.addTask(2, EntityAIOwnerOrderedToAttack(minion, minionData))
        targetTasks.addTask(3, EntityAINearestAttackableTarget(minion, EntityLiving::class.java, 0, true, true) { minionData.shouldAttack(it) })
        targetTasks.addTask(4, EntityAIHurtByTarget(minion, false))
    }
}
