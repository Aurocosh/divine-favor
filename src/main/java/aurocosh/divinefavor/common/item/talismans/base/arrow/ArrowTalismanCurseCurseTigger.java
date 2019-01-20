package aurocosh.divinefavor.common.item.talismans.base.arrow;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.effect.ModEffectTrigger;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionTrigger;
import aurocosh.divinefavor.common.util.UtilCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;

public class ArrowTalismanCurseCurseTigger extends ItemArrowTalisman {
    private final ModPotionTrigger potion;
    private final int duration;

    public ArrowTalismanCurseCurseTigger(String name, int startingSpellUses, int color, ModPotionTrigger potion, int duration) {
        super(name, startingSpellUses, color, 0, false, true, ArrowType.CURSED_ARROW);
        this.potion = potion;
        this.duration = duration;
    }

    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        UtilCurses.applyCurse(target, shooter, new ModEffectTrigger(potion, duration));
    }
}