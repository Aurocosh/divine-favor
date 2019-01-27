package aurocosh.divinefavor.common.entity.minions.behaviour;

import aurocosh.divinefavor.common.entity.ai.EntityAIFollowOwner;
import aurocosh.divinefavor.common.entity.ai.EntityAIMinionWait;
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerHurtByTarget;
import aurocosh.divinefavor.common.entity.ai.EntityAIOwnerOrderedToAttack;
import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.base.MinionData;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;

public class MinionBehaviourZombie {
    public static <T extends EntityZombie & IMinion> void apply(EntityAITasks tasks, EntityAITasks targetTasks, T minion) {
        MinionData minionData = minion.getMinionData();

        tasks.addTask(0, new EntityAISwimming(minion));
        tasks.addTask(1, new EntityAIFollowOwner<>(minion, 2.0D, 5.0F, 2.0F, true, minionData::isFollowing));
        tasks.addTask(2, new EntityAIMinionWait<>(minion, () -> minionData.isFollowing() || minionData.isWaiting()));
        tasks.addTask(3, new EntityAIZombieAttack(minion, 1.0D, false));
        tasks.addTask(4, new EntityAITempt(minion, 1.1D, Items.CHICKEN, false));
        tasks.addTask(6, new EntityAIMoveTowardsRestriction(minion, 1.0D));
        tasks.addTask(6, new EntityAIFollowOwner<>(minion, 2.0D, 5.0F, 2.0F, false, () -> true));
        tasks.addTask(8, new EntityAIWander(minion, 1.0D));
        tasks.addTask(9, new EntityAIWatchClosest(minion, EntityPlayer.class, 8.0F));
        tasks.addTask(9, new aurocosh.divinefavor.common.entity.ai.EntityAIBeg<>(minion, 8.0F, Items.CHICKEN));

        targetTasks.addTask(1, new EntityAIOwnerHurtByTarget<>(minion));
        targetTasks.addTask(2, new EntityAIOwnerOrderedToAttack<>(minion, minionData));
        targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(minion, EntityLiving.class, 0, true, true, minionData::shouldAttack));
        targetTasks.addTask(4, new EntityAIHurtByTarget(minion, false));
    }
}
