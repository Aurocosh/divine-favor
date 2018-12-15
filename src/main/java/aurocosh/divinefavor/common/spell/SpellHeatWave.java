package aurocosh.divinefavor.common.spell;

import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.spell.base.ModSpell;
import aurocosh.divinefavor.common.spell.base.SpellContext;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;

import java.util.List;

public class SpellHeatWave extends ModSpell {
    private final double RADIUS = 10;
    private final float EXTRA_DAMAGE = 1;
    private final int ENEMY_BURN_TIME_SECONDS = 3;
    private final int CHANCE_TO_SET_ENEMY_ON_FIRE = 80;
    private final int CHANCE_TO_SET_GROUND_ON_FIRE = 20;

    public SpellHeatWave() {
        super("heat_wave");
    }

    @Override
    protected void performActionServer(SpellContext context) {
        Vector3i origin = Vector3i.convert(context.player.getPosition());
        AxisAlignedBB axis = new AxisAlignedBB(origin.x - RADIUS, origin.y - RADIUS, origin.z - RADIUS, origin.x + RADIUS, origin.y + RADIUS, origin.z + RADIUS);
        List<Entity> list = context.world.getEntitiesWithinAABB(Entity.class, axis, (Entity e) -> (e instanceof EntityLivingBase) && e != context.player && isInRadius(origin, e));

        for (Entity entity : list) {
            entity.attackEntityFrom(DamageSource.ON_FIRE, EXTRA_DAMAGE);

            if(UtilRandom.rollDice(CHANCE_TO_SET_ENEMY_ON_FIRE))
                entity.setFire(ENEMY_BURN_TIME_SECONDS);
            if(UtilRandom.rollDice(CHANCE_TO_SET_GROUND_ON_FIRE))
                UtilBlock.ignite(context.world,entity.getPosition());
        }
    }

    private boolean isInRadius(Vector3i origin, Entity entity) {
        Vector3i entityVec = Vector3i.convert(entity.getPosition());
        return origin.isDistanceLessThen(entityVec, RADIUS);
    }
}
