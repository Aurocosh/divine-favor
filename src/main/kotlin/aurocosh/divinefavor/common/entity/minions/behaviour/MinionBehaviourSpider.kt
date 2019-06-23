package aurocosh.divinefavor.common.entity.minions.behaviour

import aurocosh.divinefavor.common.entity.ai.*
import aurocosh.divinefavor.common.entity.ai.EntityAIBeg
import aurocosh.divinefavor.common.entity.ai.EntityAIFollowOwner
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerHurtByTarget
import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.behaviour.base.MinionBehaviour
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.ai.*
import net.minecraft.entity.monster.EntitySpider
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items

class MinionBehaviourSpider<T> : MinionBehaviour<T>() where T : IMinion, T : EntitySpider {
    override fun apply(minion: T, tasks: EntityAITasks, targetTasks: EntityAITasks) {
        val minionData = minion.minionData

        tasks.addTask(0, EntityAISwimming(minion))
        tasks.addTask(1, EntityAIFollowOwner(minion, 1.0, 5.0f, 2.0f, true, { minionData.isFollowing }))
        tasks.addTask(2, EntityAIMinionWait(minion, { minionData.isFollowing || minionData.isWaiting }))
        tasks.addTask(3, EntityAILeapAtTarget(minion, 0.4f))
        tasks.addTask(4, AISpiderAttack(minion))
        tasks.addTask(5, EntityAITempt(minion, 1.1, Items.CHICKEN, false))
        tasks.addTask(6, EntityAIFollowOwner(minion, 1.0, 5.0f, 2.0f, false, { true }))
        tasks.addTask(5, EntityAIWanderAvoidWater(minion, 0.8))
        tasks.addTask(9, EntityAIWatchClosest(minion, EntityPlayer::class.java, 8.0f))
        tasks.addTask(9, EntityAIBeg(minion, 8.0f, Items.CHICKEN))

        targetTasks.addTask(1, EntityAIOwnerHurtByTarget(minion))
        targetTasks.addTask(2, EntityAIOwnerOrderedToAttack(minion, minionData))
        targetTasks.addTask(3, EntityAINearestAttackableTarget(minion, EntityLiving::class.java, 0, true, true) { minionData.shouldAttack(it) })
        targetTasks.addTask(4, EntityAIHurtByTarget(minion, false))
    }

    internal class AISpiderAttack(spider: EntitySpider) : EntityAIAttackMelee(spider, 1.0, true) {
        override fun shouldContinueExecuting(): Boolean {
            val brightness = this.attacker.brightness
            if (brightness >= 0.5f && this.attacker.rng.nextInt(100) == 0) {
                this.attacker.attackTarget = null
                return false
            } else {
                return super.shouldContinueExecuting()
            }
        }

        override fun getAttackReachSqr(attackTarget: EntityLivingBase): Double {
            return (4.0f + attackTarget.width).toDouble()
        }
    }
}
