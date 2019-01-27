package aurocosh.divinefavor.common.entity.minions.base;

import net.minecraft.entity.EntityLivingBase;

public interface IMinion {
    void attack(EntityLivingBase livingBase);
    MinionData getMinionData();
}
