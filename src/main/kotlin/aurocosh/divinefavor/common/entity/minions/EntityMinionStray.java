package aurocosh.divinefavor.common.entity.minions;

import aurocosh.divinefavor.common.config.common.ConfigMinion;
import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.base.MinionData;
import aurocosh.divinefavor.common.entity.minions.base.MinionMode;
import aurocosh.divinefavor.common.entity.minions.behaviour.MinionBehaviourSkeleton;
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionBanishing;
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionFeeding;
import aurocosh.divinefavor.common.entity.minions.minion_interaction.MinionWaitSwitch;
import aurocosh.divinefavor.common.entity.minions.minion_interaction.base.MinionInteractionHandler;
import com.google.common.base.Optional;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityStray;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.UUID;

public class EntityMinionStray extends EntityStray implements IMinion {
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(EntityMinionStray.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> MODE = EntityDataManager.createKey(EntityMinionStray.class, DataSerializers.VARINT);
    private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(EntityMinionStray.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    private final MinionData<EntityMinionStray> minionData;
    private final MinionBehaviourSkeleton<EntityMinionStray> behaviour;
    private final MinionInteractionHandler<EntityMinionStray> interactionHandler;

    public EntityMinionStray(World world) {
        super(world);
        minionData = new MinionData<>(this, dataManager, BEGGING, MODE, OWNER_UNIQUE_ID);
        minionData.setMode(MinionMode.Normal);

        behaviour = new MinionBehaviourSkeleton<>(this);
        behaviour.apply(this, tasks, targetTasks);

        interactionHandler = new MinionInteractionHandler<>();
        interactionHandler.addOwnerInteraction(new MinionWaitSwitch<>());
        interactionHandler.addOwnerInteraction(new MinionFeeding<>(1, Items.CHICKEN, Items.PORKCHOP, Items.BEEF));
        interactionHandler.addOwnerInteraction(new MinionBanishing<>());

        setCombatTask();
    }

    @Override
    protected void initEntityAI() {
        // unused because it is called before all data initialized by child classes
    }

    @Override
    public void setCombatTask() {
        if(behaviour != null)
            behaviour.applyCombatBehaviour(this, tasks);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(ConfigMinion.stray.armor);
        getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(ConfigMinion.stray.armorToughness);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(ConfigMinion.stray.attackDamage);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(ConfigMinion.stray.followRange);
        getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(ConfigMinion.stray.knockbackResistance);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(ConfigMinion.stray.maxHealth);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(ConfigMinion.stray.movementSpeed);
        getEntityAttribute(SWIM_SPEED).setBaseValue(ConfigMinion.stray.swimSpeed);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(MODE, MinionMode.Normal.getValue());
        dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
        dataManager.register(BEGGING, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        minionData.writeEntityToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        minionData.readEntityFromNBT(compound);
    }

    @Override
    public boolean canBeLeashedTo(EntityPlayer player) {
        return minionData.isOwner(player);
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand) {
        return interactionHandler.processInteract(this, player, hand);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    @Override
    public MinionData getMinionData() {
        return minionData;
    }
}
