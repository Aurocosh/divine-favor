package aurocosh.divinefavor.common.entity.minions;

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
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.UUID;

public class MinionSkeleton extends EntitySkeleton implements IMinion {
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(MinionSkeleton.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> MODE = EntityDataManager.createKey(MinionSkeleton.class, DataSerializers.VARINT);
    private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(MinionSkeleton.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    private final MinionData<MinionSkeleton> minionData;
    private final MinionBehaviourSkeleton<MinionSkeleton> behaviour;
    private final MinionInteractionHandler<MinionSkeleton> interactionHandler;

    public MinionSkeleton(World worldIn) {
        super(worldIn);
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

//        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
//        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
//        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);

        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
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
