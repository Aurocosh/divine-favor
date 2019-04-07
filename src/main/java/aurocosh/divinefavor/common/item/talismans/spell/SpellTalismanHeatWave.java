package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.lib.math.Vector3i;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesHeatWave;
import aurocosh.divinefavor.common.util.UtilBlock;
import aurocosh.divinefavor.common.util.UtilRandom;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class SpellTalismanHeatWave extends ItemSpellTalisman {
    public SpellTalismanHeatWave(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        double radius = ConfigSpells.heatWave.radius;
        World world = context.world;
        EntityPlayer player = context.player;
        Vector3i origin = Vector3i.convert(player.getPosition());
        AxisAlignedBB axis = new AxisAlignedBB(origin.x - radius, origin.y - radius, origin.z - radius, origin.x + radius, origin.y + radius, origin.z + radius);
        List<Entity> list = world.getEntitiesWithinAABB(Entity.class, axis, (Entity e) -> (e instanceof EntityLivingBase) && e != player && isInRadius(origin, e));

        for (Entity entity : list) {
            entity.attackEntityFrom(DamageSource.ON_FIRE, ConfigSpells.heatWave.damage);

            if (UtilRandom.rollDice(ConfigSpells.heatWave.chanceToSetEnemyOnFire))
                entity.setFire(ConfigSpells.heatWave.enemyBurnTime);
            if (UtilRandom.rollDice(ConfigSpells.heatWave.chanceToSetGroundOnFire))
                UtilBlock.ignite(world, entity.getPosition());
        }

        Vec3d positionEyes = player.getPositionEyes(0);
        new MessageParticlesHeatWave(positionEyes).sendToAllAround(world, player.getPosition(), ConfigGeneral.particleRadius);

    }

    private boolean isInRadius(Vector3i origin, Entity entity) {
        Vector3i entityVec = Vector3i.convert(entity.getPosition());
        return origin.isDistanceLessThen(entityVec, ConfigSpells.heatWave.radius);
    }
}
