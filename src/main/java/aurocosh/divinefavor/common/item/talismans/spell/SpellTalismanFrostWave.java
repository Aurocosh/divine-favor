package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.config.common.ConfigGeneral;
import aurocosh.divinefavor.common.config.common.ConfigSpells;
import aurocosh.divinefavor.common.damage_source.ModDamageSources;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.network.message.client.particles.MessageParticlesWave;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilCoordinates;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.List;

public class SpellTalismanFrostWave extends ItemSpellTalisman {
    private static final double RADIUS_SQ = ConfigSpells.frostWave.radius * ConfigSpells.frostWave.radius;

    public SpellTalismanFrostWave(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        World world = context.world;
        EntityPlayer player = context.player;
        Vec3d origin = player.getPositionVector();

        AxisAlignedBB alignedBB = UtilCoordinates.INSTANCE.getBoundingBox(origin, ConfigSpells.frostWave.radius);
        List<EntityLivingBase> list = world.getEntitiesWithinAABB(EntityLivingBase.class, alignedBB, e -> e != player && e != null && origin.squareDistanceTo(e.getPositionVector()) <= RADIUS_SQ);

        for (EntityLivingBase entity : list) {
            entity.attackEntityFrom(ModDamageSources.frostDamage, ConfigSpells.frostWave.damage);
            Vec3d direction = origin.subtract(entity.getPositionVector()).normalize();
            entity.knockBack(context.player, ConfigSpells.frostWave.knockbackPower, direction.x, direction.z);
        }

        Vec3d positionEyes = player.getPositionEyes(0);
        new MessageParticlesWave(EnumParticleTypes.SNOW_SHOVEL, positionEyes).sendToAllAround(world, player.getPosition(), ConfigGeneral.particleRadius);
    }
}
