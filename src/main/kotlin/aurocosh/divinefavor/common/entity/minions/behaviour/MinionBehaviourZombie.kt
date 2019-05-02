package aurocosh.divinefavor.common.entity.minions.behaviour

import aurocosh.divinefavor.common.entity.ai.EntityAIFollowOwner
import aurocosh.divinefavor.common.entity.ai.EntityAIMinionWait
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerHurtByTarget
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerOrderedToAttack
import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.behaviour.base.MinionBehaviour
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.*
import net.minecraft.entity.monster.EntityZombie
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items

class MinionBehaviourZombie<T> : MinionBehaviour<T>() where T : IMinion, T : EntityZombie {
    override fun apply(minion: T, tasks: EntityAITasks, targetTasks: EntityAITasks) {
        val minionData = minion.minionData

        tasks.addTask(0, EntityAISwimming(minion))
        tasks.addTask(1, EntityAIFollowOwner(minion, 2.0, 5.0f, 2.0f, true, { minionData.isFollowing }))
        tasks.addTask(2, EntityAIMinionWait(minion, { minionData.isFollowing || minionData.isWaiting }))
        tasks.addTask(3, EntityAIZombieAttack(minion, 1.0, false))
        tasks.addTask(4, EntityAITempt(minion, 1.1, Items.CHICKEN, false))
        tasks.addTask(6, EntityAIMoveTowardsRestriction(minion, 1.0))
        tasks.addTask(6, EntityAIFollowOwner(minion, 2.0, 5.0f, 2.0f, false, { true }))
        tasks.addTask(8, EntityAIWander(minion, 1.0))
        tasks.addTask(9, EntityAIWatchClosest(minion, EntityPlayer::class.java, 8.0f))
        tasks.addTask(9, aurocosh.divinefavor.common.entity.ai.EntityAIBeg(minion, 8.0f, Items.CHICKEN))

        targetTasks.addTask(1, EntityAIOwnerHurtByTarget(minion))
        targetTasks.addTask(2, EntityAIOwnerOrderedToAttack(minion, minionData))
        targetTasks.addTask(3, EntityAINearestAttackableTarget(minion, EntityLiving::class.java, 0, true, true) { minionData.shouldAttack(it) })
        targetTasks.addTask(4, EntityAIHurtByTarget(minion, false))
    }
}
