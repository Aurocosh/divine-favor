package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.arrow.base.ArrowType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.GravityType;
import aurocosh.divinefavor.common.item.talismans.arrow.base.ItemArrowTalisman;

import java.awt.*;

public class ArrowTalismanAntiGravityArrow extends ItemArrowTalisman {
    public ArrowTalismanAntiGravityArrow() {
        super("anti_gravity_arrow", 15, Color.green.getRGB(), 2, false, false, ArrowType.WOODEN_ARROW);
        gravityType = GravityType.ANTIGRAVITY;
    }
}
