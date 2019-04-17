package aurocosh.divinefavor.common.entity.projectile;

import aurocosh.divinefavor.common.config.common.ConfigArrow;
import aurocosh.divinefavor.common.util.UtilEntity;
import aurocosh.divinefavor.common.util.UtilList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class EntityStasisArrow extends EntitySpellArrow {
    private static final int RADIUS_SQ = ConfigArrow.stasisArrow.radius * ConfigArrow.stasisArrow.radius;

    public EntityStasisArrow(World worldIn) {
        super(worldIn);
        init();
    }

    public EntityStasisArrow(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        init();
    }

    public EntityStasisArrow(World worldIn, EntityLivingBase shooter) {
        super(worldIn, shooter);
        init();
    }

    private void init() {
        setDespawnDelay(ConfigArrow.stasisArrow.despawnDelay);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if(!inGround)
            return;

        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(getPosition()).grow(ConfigArrow.stasisArrow.radius));
        List<Entity> affectedMobs = UtilList.select(entities, element -> element != this && !(element instanceof EntityPlayer) && (element instanceof EntityLivingBase || element instanceof EntityArrow) && element.getDistanceSq(this) <= RADIUS_SQ);
        for (Entity affectedMob : affectedMobs)
            UtilEntity.setVelocity(affectedMob, UtilEntity.getMotionVector(affectedMob), 0.01f);
    }
}