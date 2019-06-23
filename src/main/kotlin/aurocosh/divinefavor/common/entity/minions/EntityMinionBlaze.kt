package aurocosh.divinefavor.common.entity.minions

import aurocosh.divinefavor.common.config.common.ConfigMinion
import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.base.MinionData
import aurocosh.divinefavor.common.entity.minions.base.MinionMode
import aurocosh.divinefavor.common.entity.minions.behaviour.MinionBehaviourBlaze
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionBanishing
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionFeeding
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionWaitSwitch
import aurocosh.divinefavor.common.entity.minions.minion_interaction.base.MinionInteractionHandler
import com.google.common.base.Optional
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.monster.EntityBlaze
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.util.EnumHand
import net.minecraft.world.World

class EntityMinionBlaze(world: World) : EntityBlaze(world), IMinion {

    override val minionData: MinionData<EntityMinionBlaze>
    private val interactionHandler: MinionInteractionHandler<EntityMinionBlaze>

    init {
        minionData = MinionData(this, dataManager, BEGGING, MODE, OWNER_UNIQUE_ID)
        minionData.mode = MinionMode.Normal

        val behaviour = MinionBehaviourBlaze<EntityMinionBlaze>()
        behaviour.apply(this, tasks, targetTasks)

        interactionHandler = MinionInteractionHandler()
        interactionHandler.addOwnerInteraction(MinionWaitSwitch())
        interactionHandler.addOwnerInteraction(MinionFeeding(1f, Items.COAL))
        interactionHandler.addOwnerInteraction(MinionBanishing())
    }

    override fun initEntityAI() {
        // unused because it is called before all data initialized by child classes
    }

    override fun applyEntityAttributes() {
        super.applyEntityAttributes()

        getEntityAttribute(SharedMonsterAttributes.ARMOR).baseValue = ConfigMinion.blaze.armor
        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).baseValue = ConfigMinion.blaze.armorToughness
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).baseValue = ConfigMinion.blaze.attackDamage
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).baseValue = ConfigMinion.blaze.followRange
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).baseValue = ConfigMinion.blaze.knockbackResistance
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue = ConfigMinion.blaze.maxHealth
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = ConfigMinion.blaze.movementSpeed
        getEntityAttribute(EntityLivingBase.SWIM_SPEED).baseValue = ConfigMinion.blaze.swimSpeed.toDouble()
    }

    override fun entityInit() {
        super.entityInit()
        dataManager.register(MODE, MinionMode.Normal.value)
        dataManager.register(OWNER_UNIQUE_ID, Optional.absent())
        dataManager.register(BEGGING, false)
    }

    override fun writeEntityToNBT(compound: NBTTagCompound) {
        super.writeEntityToNBT(compound)
        minionData.writeEntityToNBT(compound)
    }

    override fun readEntityFromNBT(compound: NBTTagCompound) {
        super.readEntityFromNBT(compound)
        minionData.readEntityFromNBT(compound)
    }

    override fun canBeLeashedTo(player: EntityPlayer): Boolean {
        return minionData.isOwner(player)
    }

    override fun processInteract(player: EntityPlayer, hand: EnumHand): Boolean {
        return interactionHandler.processInteract(this, player, hand)
    }

    override fun getMaxSpawnedInChunk(): Int {
        return 8
    }

    companion object {
        private val BEGGING = EntityDataManager.createKey(EntityMinionBlaze::class.java, DataSerializers.BOOLEAN)
        private val MODE = EntityDataManager.createKey(EntityMinionBlaze::class.java, DataSerializers.VARINT)
        private val OWNER_UNIQUE_ID = EntityDataManager.createKey(EntityMinionBlaze::class.java, DataSerializers.OPTIONAL_UNIQUE_ID)
    }
}
