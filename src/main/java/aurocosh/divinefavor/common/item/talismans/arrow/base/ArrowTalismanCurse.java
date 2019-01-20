package aurocosh.divinefavor.common.item.talismans.arrow.base;

import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.util.UtilCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;

public class ArrowTalismanCurse extends ItemArrowTalisman {
    private final ModPotion potion;
    private final int duration;

    public ArrowTalismanCurse(String name, int startingSpellUses, int color, ModPotion potion, int duration) {
        super(name, startingSpellUses, color, 0, false, true, ArrowType.CURSED_ARROW);
        this.potion = potion;
        this.duration = duration;
    }

    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        UtilCurses.applyCurse(target, shooter, new ModEffect(potion, duration));
    }
}