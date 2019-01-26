package aurocosh.divinefavor.common.item.talismans.spell.base;

import aurocosh.divinefavor.common.entity.minions.base.IMinion;
import net.minecraft.entity.EntityLiving;

public class SpellTalismanSummonMinion<T extends EntityLiving & IMinion> extends SpellTalismanSummonEntity<T> {

    public SpellTalismanSummonMinion(String name, int uses, boolean castOnUse, boolean castOnRightClick, Class<? extends T> clazz) {
        super(name, uses, castOnUse, castOnRightClick, clazz);
    }

    @Override
    protected void postProcessEntity(T entityLiving, TalismanContext context) {
        entityLiving.getMinionData().setOwner(context.player);
    }
}
