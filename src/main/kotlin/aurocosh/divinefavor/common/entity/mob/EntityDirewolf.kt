package aurocosh.divinefavor.common.entity.mob

import net.minecraft.entity.EntityLiving
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.ai.*
import net.minecraft.entity.passive.EntityWolf
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

class EntityDirewolf(worldIn: World) : EntityWolf(worldIn) {

    init {
        setSize(width * BODY_SCALE, height * BODY_SCALE)
    }

    override fun initEntityAI() {
        // unused because it is called before all data initialized by child classes
        aiSit = EntityAISit(this)
        tasks.addTask(1, EntityAISwimming(this))
        tasks.addTask(4, EntityAILeapAtTarget(this, 0.4f))
        tasks.addTask(5, EntityAIAttackMelee(this, 1.0, true))
        tasks.addTask(8, EntityAIWanderAvoidWater(this, 1.0))
        tasks.addTask(10, EntityAIWatchClosest(this, EntityPlayer::class.java, 8.0f))
        tasks.addTask(10, EntityAILookIdle(this))

        targetTasks.addTask(3, EntityAIHurtByTarget(this, true))
        targetTasks.addTask(4, EntityAINearestAttackableTarget(this, EntityPlayer::class.java, false))
        targetTasks.addTask(5, EntityAINearestAttackableTarget(this, EntityLiving::class.java, 0, false, false) { entityLiving -> entityLiving !is EntityDirewolf })
    }

    override fun applyEntityAttributes() {
        super.applyEntityAttributes()
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = 0.30000001192092896
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue = 20.0
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).baseValue = 64.0
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).baseValue = 4.0
    }

    companion object {
        val BODY_SCALE = 1.5f
    }
}
