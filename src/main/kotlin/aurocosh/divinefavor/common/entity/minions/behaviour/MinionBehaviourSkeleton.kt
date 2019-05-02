package aurocosh.divinefavor.common.entity.minions.behaviour

import aurocosh.divinefavor.common.entity.ai.EntityAIFollowOwner
import aurocosh.divinefavor.common.entity.ai.EntityAIMinionWait
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerHurtByTarget
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerOrderedToAttack
import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.behaviour.base.MinionBehaviour
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.ai.*
import net.minecraft.entity.monster.AbstractSkeleton
import net.minecraft.entity.passive.EntityWolf
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.world.EnumDifficulty
import net.minecraftforge.fml.relauncher.ReflectionHelper

class MinionBehaviourSkeleton<T>(minion: T) : MinionBehaviour<T>() where T : IMinion, T : AbstractSkeleton {

    private val aiArrowAttack: EntityAIAttackRangedBow<AbstractSkeleton>
    private val aiAttackOnCollide: EntityAIAttackMelee

    init {
        this.aiArrowAttack = ReflectionHelper.getPrivateValue(AbstractSkeleton::class.java, minion, AI_ARROW_ATTACK_INDEX)
        this.aiAttackOnCollide = ReflectionHelper.getPrivateValue(AbstractSkeleton::class.java, minion, AI_ATTACK_ON_COLLIDE)
    }

    override fun apply(minion: T, tasks: EntityAITasks, targetTasks: EntityAITasks) {
        val minionData = minion.minionData

        tasks.addTask(0, EntityAISwimming(minion))
        tasks.addTask(1, EntityAIRestrictSun(minion))
        tasks.addTask(2, EntityAIFleeSun(minion, 1.0))
        tasks.addTask(2, EntityAIAvoidEntity(minion, EntityWolf::class.java, 6.0f, 1.0, 1.2))

        tasks.addTask(3, EntityAIFollowOwner(minion, 2.0, 5.0f, 2.0f, true, { minionData.isFollowing }))
        tasks.addTask(4, EntityAIMinionWait(minion, { minionData.isFollowing || minionData.isWaiting }))

        // attack task here

        tasks.addTask(6, EntityAIWander(minion, 1.0))
        tasks.addTask(6, EntityAIFollowOwner(minion, 2.0, 5.0f, 2.0f, false, { true }))
        tasks.addTask(7, EntityAIWatchClosest(minion, EntityPlayer::class.java, 8.0f))
        tasks.addTask(7, EntityAILookIdle(minion))
        tasks.addTask(8, aurocosh.divinefavor.common.entity.ai.EntityAIBeg(minion, 8.0f, Items.CHICKEN))

        targetTasks.addTask(1, EntityAIOwnerHurtByTarget(minion))
        targetTasks.addTask(2, EntityAIOwnerOrderedToAttack(minion, minionData))
        targetTasks.addTask(3, EntityAINearestAttackableTarget(minion, EntityLiving::class.java, 0, true, true) { minionData.shouldAttack(it) })
        targetTasks.addTask(4, EntityAIHurtByTarget(minion, false))
    }

    fun applyCombatBehaviour(minion: T, tasks: EntityAITasks) {
        tasks.removeTask(aiAttackOnCollide)
        tasks.removeTask(aiArrowAttack)
        val itemStack = minion.heldItemMainhand

        if (itemStack.item === Items.BOW) {
            val attackCooldown = if (minion.world.difficulty != EnumDifficulty.HARD) 40 else 20
            aiArrowAttack.setAttackCooldown(attackCooldown)
            tasks.addTask(AI_ATTACK_PRIORITY, aiArrowAttack)
        } else
            tasks.addTask(AI_ATTACK_PRIORITY, aiAttackOnCollide)
    }

    companion object {
        private val AI_ARROW_ATTACK_INDEX = 1
        private val AI_ATTACK_ON_COLLIDE = 2

        private val AI_ATTACK_PRIORITY = 5
    }
}
