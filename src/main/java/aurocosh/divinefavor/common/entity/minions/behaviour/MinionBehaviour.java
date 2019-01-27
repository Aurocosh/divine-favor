package aurocosh.divinefavor.common.entity.minions.behaviour;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;

public abstract class MinionBehaviour <T extends EntityLiving & IMinion> {
    public abstract void init(EntityAITasks tasks, EntityAITasks targetTasks, T minion);
}
