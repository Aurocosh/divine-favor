package aurocosh.divinefavor.common.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntitySpellArrowCurse extends EntitySpellArrow {
    public EntitySpellArrowCurse(World worldIn) {
        super(worldIn);
    }

    public EntitySpellArrowCurse(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    public EntitySpellArrowCurse(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
    }
}