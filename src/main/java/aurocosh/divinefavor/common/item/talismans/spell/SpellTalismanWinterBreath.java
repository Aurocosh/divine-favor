package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.damage_source.ModDamageSources;
import aurocosh.divinefavor.common.favor.ModFavor;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class SpellTalismanWinterBreath extends ItemSpellTalisman {
    private final double RADIUS = 10;
    private final float CONE_TOLERANCE = 0.8f;
    private final float EXTRA_DAMAGE = 1;
    private final float KNOCKBACK_FORCE = 2;

    public SpellTalismanWinterBreath(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        Vec3d origin = context.player.getPositionVector();
        Vec3d lookVec = context.player.getLookVec();
        double radiusSq = RADIUS * RADIUS;
        List<EntityLivingBase> entities = UtilEntity.getEntitiesInSquareRadius(EntityLivingBase.class, context.world, origin, RADIUS, (EntityLivingBase livingBase) -> (livingBase != null) && (livingBase != context.player) && UtilEntity.isInRadius(origin, livingBase, radiusSq) && UtilEntity.isInCone(lookVec, origin, livingBase, CONE_TOLERANCE));
        for (Entity entity : entities) {
            entity.attackEntityFrom(ModDamageSources.frostDamage, EXTRA_DAMAGE);
            UtilEntity.addVelocity(entity, lookVec, KNOCKBACK_FORCE);
        }
    }
}
