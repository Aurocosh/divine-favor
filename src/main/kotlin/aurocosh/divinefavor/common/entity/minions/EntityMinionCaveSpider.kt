package aurocosh.divinefavor.common.entity.minions

import aurocosh.divinefavor.common.config.common.ConfigMinion
import aurocosh.divinefavor.common.entity.minions.base.IMinion
import aurocosh.divinefavor.common.entity.minions.base.MinionData
import aurocosh.divinefavor.common.entity.minions.base.MinionMode
import aurocosh.divinefavor.common.entity.minions.behaviour.MinionBehaviourSpider
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionBanishing
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionFeeding
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionWaitSwitch
import aurocosh.divinefavor.common.entity.minions.minion_interaction.base.MinionInteractionHandler
import com.google.common.base.Optional
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.SharedMonsterAttributes
import net.minecraft.entity.monster.EntityCaveSpider
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.init.Items
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.datasync.DataSerializers
import net.minecraft.network.datasync.EntityDataManager
import net.minecraft.util.EnumHand
import net.minecraft.world.World

class EntityMinionCaveSpider(world: World) : EntityCaveSpider(world), IMinion {

    override val minionData: MinionData<EntityMinionCaveSpider>
    private val interactionHandler: MinionInteractionHandler<EntityMinionCaveSpider>

    init {
        minionData = MinionData(this, dataManager, BEGGING, MODE, OWNER_UNIQUE_ID)
        minionData.mode = MinionMode.Normal

        val behaviour = MinionBehaviourSpider<EntityMinionCaveSpider>()
        behaviour.apply(this, tasks, targetTasks)

        interactionHandler = MinionInteractionHandler()
        interactionHandler.addOwnerInteraction(MinionWaitSwitch())
        interactionHandler.addOwnerInteraction(MinionFeeding(1f, Items.CHICKEN, Items.PORKCHOP, Items.BEEF))
        interactionHandler.addOwnerInteraction(MinionBanishing())
    }

    override fun initEntityAI() {
        // unused because it is called before all data initialized by child classes
    }

    override fun applyEntityAttributes() {
        super.applyEntityAttributes()

        getEntityAttribute(SharedMonsterAttributes.ARMOR).baseValue = ConfigMinion.caveSpider.armor
        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).baseValue = ConfigMinion.caveSpider.armorToughness
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).baseValue = ConfigMinion.caveSpider.attackDamage
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).baseValue = ConfigMinion.caveSpider.followRange
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).baseValue = ConfigMinion.caveSpider.knockbackResistance
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).baseValue = ConfigMinion.caveSpider.maxHealth
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).baseValue = ConfigMinion.caveSpider.movementSpeed
        getEntityAttribute(EntityLivingBase.SWIM_SPEED).baseValue = ConfigMinion.caveSpider.swimSpeed.toDouble()
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
        private val BEGGING = EntityDataManager.createKey(EntityMinionCaveSpider::class.java, DataSerializers.BOOLEAN)
        private val MODE = EntityDataManager.createKey(EntityMinionCaveSpider::class.java, DataSerializers.VARINT)
        private val OWNER_UNIQUE_ID = EntityDataManager.createKey(EntityMinionCaveSpider::class.java, DataSerializers.OPTIONAL_UNIQUE_ID)
    }
}
