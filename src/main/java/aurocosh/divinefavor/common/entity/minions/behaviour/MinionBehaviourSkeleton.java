package aurocosh.divinefavor.common.entity.minions.behaviour;

import aurocosh.divinefavor.common.entity.ai.EntityAIFollowOwner;
import aurocosh.divinefavor.common.entity.ai.EntityAIMinionWait;
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerHurtByTarget;
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerOrderedToAttack;
import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.base.MinionData;
import aurocosh.divinefavor.common.entity.minions.behaviour.base.MinionBehaviour;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.AbstractSkeleton;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

public class MinionBehaviourSkeleton<T extends EntitySkeleton & IMinion> extends MinionBehaviour<T> {
    private static final int AI_ARROW_ATTACK_INDEX = 1;
    private static final int AI_ATTACK_ON_COLLIDE = 2;

    private static final int AI_ATTACK_PRIORITY = 5;

    private final EntityAIAttackRangedBow<AbstractSkeleton> aiArrowAttack;
    private final EntityAIAttackMelee aiAttackOnCollide;

    public MinionBehaviourSkeleton(T minion) {
        this.aiArrowAttack = ReflectionHelper.getPrivateValue(AbstractSkeleton.class, minion, AI_ARROW_ATTACK_INDEX);
        this.aiAttackOnCollide = ReflectionHelper.getPrivateValue(AbstractSkeleton.class, minion, AI_ATTACK_ON_COLLIDE);
    }

    @Override
    public void apply(T minion, EntityAITasks tasks, EntityAITasks targetTasks)  {
        MinionData minionData = minion.getMinionData();

        tasks.addTask(0, new EntityAISwimming(minion));
        tasks.addTask(1, new EntityAIRestrictSun(minion));
        tasks.addTask(2, new EntityAIFleeSun(minion, 1.0D));
        tasks.addTask(2, new EntityAIAvoidEntity<>(minion, EntityWolf.class, 6.0F, 1.0D, 1.2D));

        tasks.addTask(3, new EntityAIFollowOwner<>(minion, 2.0D, 5.0F, 2.0F, true, minionData::isFollowing));
        tasks.addTask(4, new EntityAIMinionWait<>(minion, () -> minionData.isFollowing() || minionData.isWaiting()));

        // attack task here

        tasks.addTask(6, new EntityAIWander(minion, 1.0D));
        tasks.addTask(6, new EntityAIFollowOwner<>(minion, 2.0D, 5.0F, 2.0F, false, () -> true));
        tasks.addTask(7, new EntityAIWatchClosest(minion, EntityPlayer.class, 8.0F));
        tasks.addTask(7, new EntityAILookIdle(minion));
        tasks.addTask(8, new aurocosh.divinefavor.common.entity.ai.EntityAIBeg<>(minion, 8.0F, Items.CHICKEN));

        targetTasks.addTask(1, new EntityAIOwnerHurtByTarget<>(minion));
        targetTasks.addTask(2, new EntityAIOwnerOrderedToAttack<>(minion, minionData));
        targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(minion, EntityLiving.class, 0, true, true, minionData::shouldAttack));
        targetTasks.addTask(4, new EntityAIHurtByTarget(minion, false));
    }

    public void applyCombatBehaviour(T minion, EntityAITasks tasks) {
        tasks.removeTask(aiAttackOnCollide);
        tasks.removeTask(aiArrowAttack);
        ItemStack itemStack = minion.getHeldItemMainhand();

        if (itemStack.getItem() == Items.BOW) {
            int attackCooldown = minion.world.getDifficulty() != EnumDifficulty.HARD ? 40 : 20;
            aiArrowAttack.setAttackCooldown(attackCooldown);
            tasks.addTask(AI_ATTACK_PRIORITY, aiArrowAttack);
        }
        else
            tasks.addTask(AI_ATTACK_PRIORITY, aiAttackOnCollide);
    }
}
