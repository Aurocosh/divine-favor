package aurocosh.divinefavor.common.item.talismans.arrow;

import aurocosh.divinefavor.common.item.talismans.base.arrow.ArrowType;
import aurocosh.divinefavor.common.item.talismans.base.arrow.GravityType;
import aurocosh.divinefavor.common.item.talismans.base.arrow.ItemArrowTalisman;

import java.awt.*;

public class ArrowTalismanZeroGArrow extends ItemArrowTalisman {
    public ArrowTalismanZeroGArrow() {
        super("zero_g_arrow", 15, Color.green.getRGB(), 2, false, false, ArrowType.WOODEN_ARROW);
        gravityType = GravityType.NO_GRAVITY;
    }
}
