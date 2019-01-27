package aurocosh.divinefavor.common.entity.minions.behaviour.base;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAITasks;

public abstract class MinionBehaviour <T extends EntityLiving & IMinion> {
    public abstract void apply(T minion, EntityAITasks tasks, EntityAITasks targetTasks);
}
