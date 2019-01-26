package aurocosh.divinefavor.common.entity.minions;

import aurocosh.divinefavor.common.entity.ai.EntityAIBeg;
import aurocosh.divinefavor.common.entity.ai.EntityAIFollowOwner;
import aurocosh.divinefavor.common.entity.ai.EntityAIMinionWait;
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerHurtByTarget;
import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.base.MinionData;
import aurocosh.divinefavor.common.entity.minions.base.MinionMode;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import java.util.UUID;

public class MinionZombie extends EntityZombie implements IMinion {
    private static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(MinionZombie.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> MODE = EntityDataManager.createKey(MinionZombie.class, DataSerializers.VARINT);
    private static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(MinionZombie.class, DataSerializers.OPTIONAL_UNIQUE_ID);

    private final MinionData minionData;

    private final Predicate<Entity> notOwnerPredicate;

    public MinionZombie(World worldIn) {
        super(worldIn);
        minionData = new MinionData(this, dataManager, BEGGING, MODE, OWNER_UNIQUE_ID);
        minionData.setMode(MinionMode.Normal);

        notOwnerPredicate = entity -> {
            Entity owner = minionData.getOwner();

            if (entity == owner)
                return false;
            if (entity instanceof IMinion) {
                IMinion minion = (IMinion) entity;
                if (minion.getMinionData().getOwner() == owner)
                    return false;
            }
            if (entity instanceof EntityLivingBase)
                return ((EntityLivingBase) entity).attackable();
            return false;
        };
    }

    @Override
    protected void initEntityAI() {
        tasks.addTask(0, new EntityAISwimming(this));
        tasks.addTask(1, new EntityAIMinionWait<>(this));
        tasks.addTask(2, new EntityAIZombieAttack(this, 1.0D, false));
        tasks.addTask(3, new EntityAITempt(this, 1.1D, Items.CHICKEN, false));
        tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 1.0D));
        tasks.addTask(5, new EntityAIFollowOwner<>(this, 2.0D, 5.0F, 2.0F, false));
        tasks.addTask(7, new EntityAIWander(this, 1.0D));
        tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        tasks.addTask(8, new EntityAIBeg<>(this, 8.0F, Items.CHICKEN));

        targetTasks.addTask(1, new EntityAIOwnerHurtByTarget<>(this));
        targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 0, true, true, notOwnerPredicate));
        targetTasks.addTask(4, new EntityAIHurtByTarget(this, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();

        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);

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
        if (player.world.isRemote)
            return false;

        if(toggleMode(player, hand))
            return true;
        if (feed(player, hand))
            return true;
        return false;
    }

    private boolean toggleMode(EntityPlayer player, EnumHand hand) {
        if(hand != EnumHand.MAIN_HAND)
            return false;
        if (!player.getHeldItemMainhand().isEmpty())
            return false;
        if (!minionData.isOwner(player))
            return false;

        MinionMode mode = minionData.getMode() != MinionMode.Wait ? MinionMode.Wait : MinionMode.Normal;
        minionData.setMode(mode);
        isJumping = false;
        navigator.clearPath();
        setAttackTarget(null);
        return true;
    }

    private boolean feed(EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.getItem() != Items.CHICKEN)
            return false;
        if (!(getHealth() < 30.0F))
            return false;

        if (!player.capabilities.isCreativeMode)
            stack.shrink(1);
        heal(20.0F);
        return true;
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 8;
    }

    @Override
    public MinionData getMinionData() {
        return minionData;
    }

    @Override
    protected boolean shouldBurnInDay() {
        return false;
    }
}
