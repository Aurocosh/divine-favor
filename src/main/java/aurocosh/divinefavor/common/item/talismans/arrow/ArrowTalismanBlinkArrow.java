package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;
import aurocosh.divinefavor.common.util.UtilEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;

import java.awt.*;

public class ArrowTalismanBlinkArrow extends ItemArrowTalisman {
    public ArrowTalismanBlinkArrow() {
        super("blink_arrow", 20, Color.green.getRGB(), 0, true, false, ArrowType.SPELL_ARROW);
    }

    @Override
    protected void performActionServer(EntityLivingBase target, EntityLivingBase shooter, EntityArrow arrow) {
        UtilEntity.teleport(shooter, arrow.getPosition());
    }
}
