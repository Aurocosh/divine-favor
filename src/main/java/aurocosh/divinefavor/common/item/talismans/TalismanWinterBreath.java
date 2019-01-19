package aurocosh.divinefavor.common.item.talismans;

import aurocosh.divinefavor.common.damage_source.ModDamageSources;
import aurocosh.divinefavor.common.item.talismans.base.ItemTalisman;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.item.talismans.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.List;

public class TalismanWinterBreath extends ItemTalisman {
    private final double RADIUS = 10;
    private final float CONE_TOLERANCE = 0.8f;
    private final float EXTRA_DAMAGE = 1;
    private final float KNOCKBACK_FORCE = 2;
    private static final int USES = 10;

    public TalismanWinterBreath() {
        super("winter_breath", USES, true, true);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        Vector3i origin = Vector3i.convert(context.player.getPosition());
        Vec3d originVec3d = context.player.getPositionVector();
        Vec3d lookVector = context.player.getLookVec();
        AxisAlignedBB axis = new AxisAlignedBB(origin.x - RADIUS, origin.y - RADIUS, origin.z - RADIUS, origin.x + RADIUS, origin.y + RADIUS, origin.z + RADIUS);
        List<Entity> list = context.world.getEntitiesWithinAABB(Entity.class, axis, (Entity e) -> (e instanceof EntityLivingBase) && e != context.player && isInRadius(origin, e) && isInCone(lookVector, originVec3d, e));

        for (Entity entity : list) {
            entity.attackEntityFrom(ModDamageSources.frostDamage, EXTRA_DAMAGE);
            UtilEntity.addVelocity(entity, lookVector, KNOCKBACK_FORCE);
        }
    }

    private boolean isInRadius(Vector3i origin, Entity entity) {
        Vector3i entityVec = Vector3i.convert(entity.getPosition());
        return origin.isDistanceLessThen(entityVec, RADIUS);
    }

    private boolean isInCone(Vec3d playerLookVec, Vec3d origin, Entity entity) {
        Vec3d vec = entity.getPositionVector().subtract(origin).normalize();
        return vec.dotProduct(playerLookVec) >= CONE_TOLERANCE;
    }
}
