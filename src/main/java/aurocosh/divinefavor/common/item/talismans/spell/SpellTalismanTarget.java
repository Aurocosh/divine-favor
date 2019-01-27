package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
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

public class SpellTalismanTarget extends ItemSpellTalisman {
    private static final double RADIUS = 30;

    public SpellTalismanTarget(String name, ModFavor favor, int favorCost, EnumSet<SpellOptions> options) {
        super(name, favor, favorCost, options);
    }

    @Override
    protected boolean validate(TalismanContext context) {
        return context.target != null;
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        Vec3d origin = context.player.getPositionVector();
        double radiusSq = RADIUS * RADIUS;
        List<EntityLivingBase> entities = UtilEntity.getEntitiesInSquareRadius(EntityLivingBase.class, context.world, origin, RADIUS, (EntityLivingBase livingBase) -> (livingBase instanceof IMinion)
                && (livingBase != context.player) && UtilEntity.isInRadius(origin, livingBase, radiusSq));
        for (Entity entity : entities) {
            IMinion minion = (IMinion) entity;
            minion.getMinionData().setAttackTarget(context.target);
        }
    }
}
