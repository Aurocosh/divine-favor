package aurocosh.divinefavor.common.item.talismans.arrow.base;

import aurocosh.divinefavor.common.entity.projectile.EntitySpellArrow;
import aurocosh.divinefavor.common.potions.base.effect.ModEffect;
import aurocosh.divinefavor.common.potions.base.potion.ModPotion;
import aurocosh.divinefavor.common.spirit.base.ModSpirit;
import aurocosh.divinefavor.common.util.UtilCurses;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class ArrowTalismanCurse extends ItemArrowTalisman {
    private final ModPotion potion;
    private final int duration;

    public ArrowTalismanCurse(String name, ModSpirit spirit, int favorCost, int color, ModPotion potion, int duration) {
        super(name, spirit, favorCost, color, 0, ArrowOptions.REQUIRES_TARGET, ArrowType.CURSED_ARROW);
        this.potion = potion;
        this.duration = duration;
    }

    protected boolean performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntitySpellArrow spellArrow, BlockPos blockPos, EnumFacing sideHit) {
        UtilCurses.applyCurse(target, shooter, new ModEffect(potion, duration));
        return true;
    }
}