package aurocosh.divinefavor.common.item.talismans.base.arrow;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;

public class ArrowSpellCurse extends ArrowSpell {
    private final ModPotion potion;
    private final int duration;

    public ArrowSpellCurse(ModPotion potion, int duration) {
        super(true);
        this.potion = potion;
        this.duration = duration;
    }

    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        target.addPotionEffect(new ModEffect(potion, duration).setIsCurse());
    }
}