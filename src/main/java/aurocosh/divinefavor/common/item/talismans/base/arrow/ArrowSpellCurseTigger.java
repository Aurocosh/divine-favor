package aurocosh.divinefavor.common.item.talismans.base.arrow;

import aurocosh.divinefavor.common.potions.base.effect.ModEffectTrigger;
import aurocosh.divinefavor.common.potions.base.potion.ModPotionTrigger;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;

public class ArrowSpellCurseTigger extends ArrowSpell {
    private final ModPotionTrigger potion;
    private final int duration;

    public ArrowSpellCurseTigger(ModPotionTrigger potion, int duration) {
        super(true);
        this.potion = potion;
        this.duration = duration;
    }

    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        target.addPotionEffect(new ModEffectTrigger(potion, duration).setIsCurse());
    }
}