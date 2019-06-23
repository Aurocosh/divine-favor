package aurocosh.divinefavor.common.entity.minions.behaviour

import aurocosh.divinefavor.common.entity.ai.*
import aurocosh.divinefavor.common.entity.ai.EntityAIBeg
import aurocosh.divinefavor.common.entity.ai.EntityAIFollowOwner
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerHurtByTarget
import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.behaviour.base.MinionBehaviour
import net.minecraft.entity.EntityLiving
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.*
import net.minecraft.entity.monster.EntityBlaze
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.entity.projectile.EntitySmallFireball
import net.minecraft.init.Items
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.MathHelper

class MinionBehaviourBlaze<T> : MinionBehaviour<T>() where T : IMinion, T : EntityBlaze {
    override fun apply(minion: T, tasks: EntityAITasks, targetTasks: EntityAITasks) {
        val minionData = minion.minionData

        tasks.addTask(1, EntityAIFollowOwner(minion, 1.0, 5.0f, 2.0f, true, { minionData.isFollowing }))
        tasks.addTask(2, EntityAIMinionWait(minion, { minionData.isFollowing || minionData.isWaiting }))
        tasks.addTask(3, AIFireballAttack(minion))
        tasks.addTask(4, EntityAIMoveTowardsRestriction(minion, 1.0))
        tasks.addTask(5, EntityAITempt(minion, 1.1, Items.COAL, false))
        tasks.addTask(6, EntityAIFollowOwner(minion, 1.0, 5.0f, 2.0f, false, { true }))
        tasks.addTask(7, EntityAIWanderAvoidWater(minion, 0.8))
        tasks.addTask(9, EntityAIWatchClosest(minion, EntityPlayer::class.java, 8.0f))
        tasks.addTask(9, EntityAIBeg(minion, 8.0f, Items.COAL))
        tasks.addTask(10, EntityAILookIdle(minion))

        targetTasks.addTask(1, EntityAIOwnerHurtByTarget(minion))
        targetTasks.addTask(2, EntityAIOwnerOrderedToAttack(minion, minionData))
        targetTasks.addTask(3, EntityAINearestAttackableTarget(minion, EntityLiving::class.java, 0, true, true) { minionData.shouldAttack(it) })
        targetTasks.addTask(4, EntityAIHurtByTarget(minion, false))
    }

    internal class AIFireballAttack(private val blaze: EntityBlaze) : EntityAIBase() {
        private var attackStep: Int = 0
        private var attackTime: Int = 0

        private val followDistance: Double
            get() {
                val attributeInstance = blaze.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE)
                return attributeInstance.attributeValue
            }

        init {
            mutexBits = 3
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        override fun shouldExecute(): Boolean {
            val livingBase = blaze.attackTarget
            return livingBase != null && livingBase.isEntityAlive
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        override fun startExecuting() {
            attackStep = 0
        }

        /**
         * Reset the task's internal state. Called when this task is interrupted by another one
         */
        override fun resetTask() {
            blaze.setOnFire(false)
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        override fun updateTask() {
            --attackTime
            val entitylivingbase = blaze.attackTarget
            val d0 = blaze.getDistanceSq(entitylivingbase!!)

            if (d0 < 4.0) {
                if (attackTime <= 0) {
                    attackTime = 20
                    blaze.attackEntityAsMob(entitylivingbase)
                }

                blaze.moveHelper.setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0)
            } else if (d0 < followDistance * followDistance) {
                val d1 = entitylivingbase.posX - blaze.posX
                val d2 = entitylivingbase.entityBoundingBox.minY + (entitylivingbase.height / 2.0f).toDouble() - (blaze.posY + (blaze.height / 2.0f).toDouble())
                val d3 = entitylivingbase.posZ - blaze.posZ

                if (attackTime <= 0) {
                    ++attackStep

                    if (attackStep == 1) {
                        attackTime = 60
                        blaze.setOnFire(true)
                    } else if (attackStep <= 4) {
                        attackTime = 6
                    } else {
                        attackTime = 100
                        attackStep = 0
                        blaze.setOnFire(false)
                    }

                    if (attackStep > 1) {
                        val f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5f
                        blaze.world.playEvent(null as EntityPlayer?, 1018, BlockPos(blaze.posX.toInt(), blaze.posY.toInt(), blaze.posZ.toInt()), 0)

                        for (i in 0..0) {
                            val entitysmallfireball = EntitySmallFireball(blaze.world, blaze, d1 + blaze.rng.nextGaussian() * f.toDouble(), d2, d3 + blaze.rng.nextGaussian() * f.toDouble())
                            entitysmallfireball.posY = blaze.posY + (blaze.height / 2.0f).toDouble() + 0.5
                            blaze.world.spawnEntity(entitysmallfireball)
                        }
                    }
                }

                blaze.lookHelper.setLookPositionWithEntity(entitylivingbase, 10.0f, 10.0f)
            } else {
                blaze.navigator.clearPath()
                blaze.moveHelper.setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0)
            }

            super.updateTask()
        }
    }
}
