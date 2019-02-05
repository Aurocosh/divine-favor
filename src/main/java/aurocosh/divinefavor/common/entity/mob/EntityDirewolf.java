package aurocosh.divinefavor.common.entity.mob;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class EntityDirewolf extends EntityWolf {
    public static final float BODY_SCALE = 1.5f;

    public EntityDirewolf(World worldIn) {
        super(worldIn);
        setSize(width * BODY_SCALE, height * BODY_SCALE);
    }

    @Override
    protected void initEntityAI() {
        // unused because it is called before all data initialized by child classes
        aiSit = new EntityAISit(this);
        tasks.addTask(1, new EntityAISwimming(this));
        tasks.addTask(4, new EntityAILeapAtTarget(this, 0.4f));
        tasks.addTask(5, new EntityAIAttackMelee(this, 1.0d, true));
        tasks.addTask(8, new EntityAIWanderAvoidWater(this, 1.0d));
        tasks.addTask(10, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        tasks.addTask(10, new EntityAILookIdle(this));

        targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
        targetTasks.addTask(5, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 0, false, false, entityLiving -> !(entityLiving instanceof EntityDirewolf)));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896d);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0d);
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0d);
        getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0d);
    }
}
