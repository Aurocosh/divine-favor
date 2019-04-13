package aurocosh.divinefavor.common.item.talismans.spell;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import aurocosh.divinefavor.common.entity.minions.base.MinionData;
import aurocosh.divinefavor.common.entity.minions.base.MinionMode;
import aurocosh.divinefavor.common.item.talismans.spell.base.ItemSpellTalisman;
import aurocosh.divinefavor.common.item.talismans.spell.base.SpellOptions;
import aurocosh.divinefavor.common.item.talismans.spell.base.TalismanContext;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class SpellTalismanFollow extends ItemSpellTalisman {
    private static final double RADIUS = 30;

    public SpellTalismanFollow(String name, ModSpirit spirit, int favorCost, EnumSet<SpellOptions> options) {
        super(name, spirit, favorCost, options);
    }

    @Override
    protected void performActionServer(TalismanContext context) {
        Vec3d origin = context.player.getPositionVector();
        double radiusSq = RADIUS * RADIUS;
        List<EntityLivingBase> entities = UtilEntity.getNearbyEntities(EntityLivingBase.class, context.world, origin, RADIUS, (EntityLivingBase livingBase) -> (livingBase instanceof IMinion)
                && (livingBase != context.player) && UtilEntity.isInRadius(origin, livingBase, radiusSq));
        for (Entity entity : entities) {
            IMinion minion = (IMinion) entity;
            MinionData minionData = minion.getMinionData();
            minionData.setAttackTarget(null);
            minionData.setMode(MinionMode.Follow);
        }
    }
}
